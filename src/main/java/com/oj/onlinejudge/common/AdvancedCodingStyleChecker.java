package com.oj.onlinejudge.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.classifiers.trees.J48;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/22 上午3:47
 */


public class AdvancedCodingStyleChecker {
    private static final Logger log = LoggerFactory.getLogger(AdvancedCodingStyleChecker.class);

    // Model and dataset paths (can be configured via system properties)
    private static final String MODEL_FILE_PATH = System.getProperty("model.path", "model/j48_model.model");
    private static final String DATASET_FILE_PATH = System.getProperty("dataset.path", "src/main/resources/dataset.arff");

    // Keywords for feature extraction
    private static final List<String> KEYWORDS = Arrays.asList(
            "int", "void", "char", "float", "double", "if", "else",
            "for", "while", "return", "using", "namespace", "std",
            "cout", "endl", "string", "vector"
    );

    // Regular expressions for pattern matching
    private static final Pattern VARIABLE_PATTERN = Pattern.compile(
            "(int|char|float|double|bool|string|vector|map)\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*(=.*)?;"
    );
    private static final Pattern FUNCTION_PATTERN = Pattern.compile(
            "(void|int|bool|string|double|float)\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*\\([^)]*\\)\\s*\\{?"
    );
    private static final Pattern COMMENT_PATTERN = Pattern.compile("(//|/\\*|\\*)");
    private static final Pattern TODO_FIXME_PATTERN = Pattern.compile("(TODO|FIXME)");

    // Model instance (lazy-loaded)
    private static volatile J48 model;

    // Static block to load the model
    static {
        try {
            loadModel();
        } catch (Exception e) {
            log.error("Failed to load model: {}", e.getMessage());
        }
    }

    public static synchronized J48 trainAndSaveModel(String datasetPath) throws Exception {
        log.info("Training model with dataset: {}", datasetPath);

        DataSource source = new DataSource(datasetPath);
        Instances data = source.getDataSet();
        if (data == null) {
            throw new IOException("Failed to load dataset from path: " + datasetPath);
        }
        data.setClassIndex(data.numAttributes() - 1);

        J48 tree = new J48();
        tree.buildClassifier(data);

        File modelDir = new File(MODEL_FILE_PATH).getParentFile();
        if (!modelDir.exists() && !modelDir.mkdirs()) {
            throw new IOException("Failed to create model directory: " + modelDir.getAbsolutePath());
        }
        SerializationHelper.write(MODEL_FILE_PATH, tree);
        model = tree;

        log.info("Model trained and saved successfully at: {}", MODEL_FILE_PATH);
        return tree;
    }

    public static J48 loadModel() throws Exception {
        if (model == null) {
            synchronized (AdvancedCodingStyleChecker.class) {
                if (model == null) {
                    log.info("Loading model from: {}", MODEL_FILE_PATH);
                    model = (J48) SerializationHelper.read(MODEL_FILE_PATH);
                    log.info("Model loaded successfully.");
                }
            }
        }
        return model;
    }

    public static boolean hasPoorCodingStyle(String code) throws Exception {
        double[] features = extractFeatures(code);
        return isPoorStyleBasedOnFeatures(features);
    }

    public static double[] extractFeatures(String code) {
        String[] lines = code.split("\\n");

        // Feature extraction
        double maxLineLength = Arrays.stream(lines)
                .mapToInt(String::length)
                .max()
                .orElse(0);

        double avgIndentation = Arrays.stream(lines)
                .mapToInt(line -> line.length() - line.stripLeading().length())
                .average()
                .orElse(0);

        long variableCount = Arrays.stream(lines)
                .filter(line -> VARIABLE_PATTERN.matcher(line).find())
                .count();

        long functionCount = Arrays.stream(lines)
                .filter(line -> FUNCTION_PATTERN.matcher(line).find())
                .count();

        long commentCount = Arrays.stream(lines)
                .filter(line -> COMMENT_PATTERN.matcher(line).find())
                .count();

        long keywordCount = Arrays.stream(lines)
                .mapToInt(line -> (int) KEYWORDS.stream().filter(line::contains).count())
                .sum();

        long stringCount = Arrays.stream(lines)
                .filter(line -> line.contains("\""))
                .count();

        boolean containsTODO = TODO_FIXME_PATTERN.matcher(code).find();

        // Calculate complexity and Levenshtein distance
        double complexityFactor = calculateCodeComplexity(lines);
        int levenshteinDistance = LevenshteinDistance.getDefaultInstance().apply(code, " ".repeat(code.length()));

        // Feature vector
        double[] features = new double[]{
                maxLineLength * (maxLineLength > 80 ? 1.5 : 1.0),  // 行长度
                avgIndentation,                                    // 缩进
                variableCount,                                     // 变量数
                functionCount,                                     // 函数数
                commentCount * (commentCount < 2 ? 1.5 : 1.0),     // 注释数
                stringCount,                                       // 字符串数
                keywordCount,                                      // 关键词数
                levenshteinDistance,                                // 编辑距离
                complexityFactor,                                  // 代码复杂度
                containsTODO ? 1.0 : 0.0                           // TODO标记
        };

        logFeatureDetails(features);
        return features;
    }

    private static double calculateCodeComplexity(String[] lines) {
        int nestingDepth = 0;
        int maxNestingDepth = 0;
        int complexityScore = 0;

        for (String line : lines) {
            if (line.contains("{")) nestingDepth++;
            if (line.contains("}")) nestingDepth--;
            maxNestingDepth = Math.max(maxNestingDepth, nestingDepth);

            // Increment complexity score for conditions and loops
            if (line.contains("if") || line.contains("for") ||
                    line.contains("while") || line.contains("switch")) {
                complexityScore++;
            }
        }

        return (maxNestingDepth * 0.5) + (complexityScore * 0.3);
    }

    private static void logFeatureDetails(double[] features) {
        String[] featureNames = {
                "最大行长度",       // Max Line Length
                "平均缩进",         // Average Indentation
                "变量数量",         // Variable Count
                "函数数量",         // Function Count
                "注释数量",         // Comment Count
                "字符串数量",       // String Count
                "关键词数量",       // Keyword Count
                "编辑距离", // Levenshtein Distance
                "代码复杂度",       // Code Complexity
                "TODO/FIXME 标记"  // TODO/FIXME Marker
        };

        log.info("代码特征详细信息:");
        for (int i = 0; i < features.length; i++) {
            // 格式化特征值到两位小数
            log.info("{}. {}: {}", i + 1, featureNames[i], String.format("%.2f", features[i]));
        }
    }
    private static boolean isPoorStyleBasedOnFeatures(double[] features) throws Exception {
        if (model == null) {
            throw new IllegalStateException("Model is not initialized. Train or load the model first.");
        }

        Instances instances = createInstances();
        Instance instance = new DenseInstance(1.0, features);
        instance.setDataset(instances);

        double predictedClass = model.classifyInstance(instance);
        return predictedClass == 1.0; // 1.0 indicates poor style
    }

    private static Instances createInstances() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("maxLineLength"));
        attributes.add(new Attribute("avgIndentation"));
        attributes.add(new Attribute("variableCount"));
        attributes.add(new Attribute("functionCount"));
        attributes.add(new Attribute("commentCount"));
        attributes.add(new Attribute("stringCount"));
        attributes.add(new Attribute("keywordCount"));
        attributes.add(new Attribute("levensteinDistance"));
        attributes.add(new Attribute("containsTODO"));
        attributes.add(new Attribute("class", Arrays.asList("good", "poor")));

        Instances instances = new Instances("CodeStyle", attributes, 0);
        instances.setClassIndex(attributes.size() - 1);
        return instances;
    }

    public static class LevenshteinDistance {
        public static LevenshteinDistance getDefaultInstance() {
            return new LevenshteinDistance();
        }

        public int apply(String s1, String s2) {
            int[][] dp = new int[s1.length() + 1][s2.length() + 1];

            for (int i = 0; i <= s1.length(); i++) dp[i][0] = i;
            for (int j = 0; j <= s2.length(); j++) dp[0][j] = j;

            for (int i = 1; i <= s1.length(); i++) {
                for (int j = 1; j <= s2.length(); j++) {
                    dp[i][j] = (s1.charAt(i - 1) == s2.charAt(j - 1))
                            ? dp[i - 1][j- 1]
                            : 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                }
            }
            return dp[s1.length()][s2.length()];
        }
    }

    public static void main(String[] args) {
        try {
            // Train the model if it hasn't been initialized
            if (model == null) {
                log.info("Model is not initialized. Training a new model...");
                trainAndSaveModel(DATASET_FILE_PATH);
            }

            // Sample code snippet to analyze
            String code = """
                public class Test {
                    public static void main(String[] args) {
                        System.out.println("Hello, World!");
                    }
                }
            """;

            boolean isPoor = hasPoorCodingStyle(code);
            log.info("Is the code style poor? {}", isPoor);

        } catch (Exception e) {
            log.error("Error during execution: {}", e.getMessage(), e);
        }
    }
}
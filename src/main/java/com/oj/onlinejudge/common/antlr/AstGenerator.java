package com.oj.onlinejudge.common.antlr;

import com.oj.onlinejudge.common.antlr.com.oj.onlinejudge.common.antlr.CPP14Lexer;
import com.oj.onlinejudge.common.antlr.com.oj.onlinejudge.common.antlr.CPP14Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/25 下午7:00
 */
public class AstGenerator {

    public static void main(String[] args) throws Exception {
        String sourceCode1 = "#include <iostream>\nusing namespace std;\n\nint main()\n{\n   //刷\n   cout << \"Hello World   wedw\";\n   return 0;\n}";
        String sourceCode2 = "#include <iostream>\nusing namespace std;\n\nint main()\n{\n   //刷\n   cout << \"Hello World\";\n   return 0;\n}";

        // 解析并提取指纹
        String fingerprint1 = extractFingerprintFromCode(sourceCode1);
        String fingerprint2 = extractFingerprintFromCode(sourceCode2);

        // 计算相似度
        double cosineSimilarity = CosineSimilarity.cosineSimilarity(fingerprint1, fingerprint2);
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("fingerprint1:"+fingerprint1);
        System.out.println("fingerprint2:"+fingerprint2);
        System.out.println("Cosine Similarity: " + df.format((cosineSimilarity * 100)) + "%");

    }

    public static String extractFingerprintFromCode(String sourceCode) throws Exception {
        CharStream input = CharStreams.fromString(sourceCode);
        CPP14Lexer lexer = new CPP14Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CPP14Parser parser = new CPP14Parser(tokens);

        ParseTree tree = parser.translationUnit();
        return FingerprintExtractor.extractFingerprint(tree);
    }
}
package com.oj.onlinejudge.common;

import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Standardize;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/22 上午4:52
 */
public class DataPreprocessing {

    public static Instances preprocessData(Instances data) throws Exception {
        Standardize standardize = new Standardize();
        standardize.setInputFormat(data);
        Instances standardizedData = Filter.useFilter(data, standardize);
        return standardizedData;
    }

    public static void main(String[] args) {
        try {
            // 加载数据集
            ConverterUtils.DataSource source = new ConverterUtils.DataSource("src/main/resources/dataset.arff");
            Instances data = source.getDataSet();
            data.setClassIndex(data.numAttributes() - 1);

            // 预处理数据
            Instances preprocessedData = preprocessData(data);

            // 保存预处理后的数据
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/preprocessed_dataset.arff"));
            writer.write(preprocessedData.toString());
            writer.close();

            System.out.println("Data preprocessing complete.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

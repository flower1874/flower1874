package com.oj.onlinejudge.common.antlr;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/26 下午1:23
 */
public class CosineSimilarity {

    public static double cosineSimilarity(String fingerprint1, String fingerprint2) {
        Map<Character, Integer> vector1 = createFrequencyVector(fingerprint1);
        Map<Character, Integer> vector2 = createFrequencyVector(fingerprint2);

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (Map.Entry<Character, Integer> entry : vector1.entrySet()) {
            char key = entry.getKey();
            int value = entry.getValue();
            dotProduct += value * vector2.getOrDefault(key, 0);
            normA += Math.pow(value, 2);
        }

        for (int value : vector2.values()) {
            normB += Math.pow(value, 2);
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    private static Map<Character, Integer> createFrequencyVector(String fingerprint) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : fingerprint.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap;
    }
}

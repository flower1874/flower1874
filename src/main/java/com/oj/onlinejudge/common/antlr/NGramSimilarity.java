package com.oj.onlinejudge.common.antlr;

import java.util.HashSet;
import java.util.Set;

/**
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @author : F
 * @date : 2024/11/26 下午1:32
 */


public class NGramSimilarity {

    /**
     * 计算两个指纹的 n-gram 相似度。
     *
     * @param fingerprint1 第一个指纹
     * @param fingerprint2 第二个指纹
     * @param n           n-gram 的大小
     * @return 相似度值（0 到 1 之间的浮点数）
     */
    public static double nGramSimilarity(String fingerprint1, String fingerprint2, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n 必须大于 0");
        }

        Set<String> nGrams1 = generateNGrams(fingerprint1, n);
        Set<String> nGrams2 = generateNGrams(fingerprint2, n);

        if (nGrams1.isEmpty() || nGrams2.isEmpty()) {
            return 0.0; // 如果任意一个 n-gram 集合为空，则相似度为 0
        }

        Set<String> intersection = new HashSet<>(nGrams1);
        intersection.retainAll(nGrams2);

        Set<String> union = new HashSet<>(nGrams1);
        union.addAll(nGrams2);

        return (double) intersection.size() / union.size();
    }

    /**
     * 生成给定字符串的 n-gram 集合。
     *
     * @param fingerprint 指纹字符串
     * @param n          n-gram 的大小
     * @return n-gram 集合
     */
    static Set<String> generateNGrams(String fingerprint, int n) {
        if (fingerprint == null || fingerprint.isEmpty() || n > fingerprint.length()) {
            return new HashSet<>(); // 返回空集合
        }

        Set<String> nGrams = new HashSet<>();
        for (int i = 0; i <= fingerprint.length() - n; i++) {
            nGrams.add(fingerprint.substring(i, i + n));
        }
        return nGrams;
    }
}
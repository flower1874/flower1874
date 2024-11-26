package com.oj.onlinejudge.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/25 上午9:42
 */


public class CodeFingerprintGenerator {

    /**
     * 使用 SHA-256 算法生成代码的指纹。
     *
     * @param code 输入的代码
     * @return 代码的指纹（十六进制表示）
     */
    public static String generateFingerprint(String code) {
        return hashCode(code, "SHA-256");
    }

    /**
     * 使用指定哈希算法生成代码的哈希值。
     *
     * @param code      输入的代码
     * @param algorithm 哈希算法名称（如 SHA-256、SHA-1、MD5）
     * @return 代码的哈希值（十六进制表示）
     */
    public static String hashCode(String code, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hash = md.digest(code.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException("不支持的哈希算法: " + algorithm, e);
}
    }
}

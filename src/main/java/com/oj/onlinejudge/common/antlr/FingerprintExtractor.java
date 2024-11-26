package com.oj.onlinejudge.common.antlr;

import org.antlr.v4.runtime.tree.ParseTree;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/26 上午9:02
 */
public class FingerprintExtractor {

    public static String extractFingerprint(ParseTree node) {
        Set<String> hashes = new HashSet<>();
        computeHashesRecursive(node, hashes);
        return combineHashes(hashes);
    }

    private static void computeHashesRecursive(ParseTree node, Set<String> hashes) {
        if (node == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append(node.getClass().getName());
        sb.append(node.getText());

        for (int i = 0; i < node.getChildCount(); i++) {
            computeHashesRecursive(node.getChild(i), hashes);
            sb.append(node.getChild(i).getText());
        }

        hashes.add(hashString(sb.toString()));
    }

    private static String hashString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String combineHashes(Set<String> hashes) {
        StringBuilder combined = new StringBuilder();
        for (String hash : hashes) {
            combined.append(hash);
        }
        return hashString(combined.toString());
    }
}
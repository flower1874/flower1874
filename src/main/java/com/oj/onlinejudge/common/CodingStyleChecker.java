package com.oj.onlinejudge.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/22 上午3:23
 */
public class CodingStyleChecker {

    /**
     * 检查代码风格
     *
     * @param code 要检查的 C++ 代码
     * @return 如果代码风格不符合规范，返回 true；否则返回 false
     */
    public static boolean hasPoorCodingStyle(String code) {
        // 检查行长度
        if (checkLineLength(code)) {
            return true;
        }

        // 检查缩进
        if (checkIndentation(code)) {
            return true;
        }

        // 检查命名规范
        if (checkNamingConventions(code)) {
            return true;
        }

        // 检查注释质量
        if (checkCommentQuality(code)) {
            return true;
        }

        return false;
    }

    /**
     * 检查行长度
     *
     * @param code 要检查的 C++ 代码
     * @return 如果有超过 80 个字符的行，返回 true；否则返回 false
     */
    private static boolean checkLineLength(String code) {
        String[] lines = code.split("\\n");
        for (String line : lines) {
            if (line.length() > 80) {
                System.out.println("行长度超过 80 个字符: " + line);
                return true;
            }
        }
        return false;
    }

    /**
     * 检查缩进
     *
     * @param code 要检查的 C++ 代码
     * @return 如果缩进不一致，返回 true；否则返回 false
     */
    private static boolean checkIndentation(String code) {
        String[] lines = code.split("\\n");
        int currentIndent = 0;
        for (String line : lines) {
            int indent = countLeadingSpaces(line);
            if (indent % 4 != 0) {
                System.out.println("缩进不一致: " + line);
                return true;
            }
            if (indent < currentIndent && !line.trim().startsWith("}")) {
                System.out.println("缩进不一致: " + line);
                return true;
            }
            currentIndent = indent;
        }
        return false;
    }

    /**
     * 计算行首的空格数
     *
     * @param line 要检查的行
     * @return 行首的空格数
     */
    private static int countLeadingSpaces(String line) {
        int count = 0;
        for (char c : line.toCharArray()) {
            if (c == ' ') {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    /**
     * 检查命名规范
     *
     * @param code 要检查的 C++ 代码
     * @return 如果命名不符合规范，返回 true；否则返回 false
     */
    private static boolean checkNamingConventions(String code) {
        // 检查变量名是否符合驼峰命名法
        Pattern variablePattern = Pattern.compile("\\b[a-z][a-zA-Z0-9]*\\b");
        Matcher variableMatcher = variablePattern.matcher(code);
        while (variableMatcher.find()) {
            String variable = variableMatcher.group();
            if (!variable.matches("[a-z][a-zA-Z0-9]*")) {
                System.out.println("变量命名不符合驼峰命名法: " + variable);
                return true;
            }
        }

        // 检查函数名是否符合驼峰命名法
        Pattern functionPattern = Pattern.compile("\\b[a-z][a-zA-Z0-9]*\\s*\\(");
        Matcher functionMatcher = functionPattern.matcher(code);
        while (functionMatcher.find()) {
            String function = functionPattern.matcher(functionMatcher.group()).replaceAll("");
            if (!function.matches("[a-z][a-zA-Z0-9]*")) {
                System.out.println("函数命名不符合驼峰命名法: " + function);
                return true;
            }
        }

        return false;
    }

    /**
     * 检查注释质量
     *
     * @param code 要检查的 C++ 代码
     * @return 如果注释质量差，返回 true；否则返回 false
     */
    private static boolean checkCommentQuality(String code) {
        // 使用简单的 NLP 技术检查注释质量
        Pattern commentPattern = Pattern.compile("//.*|/\\*.*?\\*/", Pattern.DOTALL);
        Matcher matcher = commentPattern.matcher(code);
        int commentCount = 0;
        while (matcher.find()) {
            commentCount++;
            String comment = matcher.group();
            if (comment.trim().length() < 5) {
                System.out.println("注释过短: " + comment);
                return true;
            }
        }
        // 如果注释少于 5 行，认为注释质量差
        if (commentCount < 5) {
            System.out.println("注释数量不足");
            return true;
        }
        return false;
    }
}

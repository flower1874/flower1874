package com.oj.onlinejudge.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/25 上午9:41
 */


public class CodePreprocessor {

    /**
     * 对代码进行预处理，包括去除注释、多余空格、规范化关键字等。
     *
     * @param code 输入的代码
     * @return 预处理后的代码
     */
    public static String preprocessCode(String code) {
        if (code == null || code.isEmpty()) {
            return "";
        }

        // 去除注释
        code = removeComments(code);

        // 去除多余的空格、换行，并将代码压缩为单行
        code = code.replaceAll("\\s+", " ").trim();

        // 规范化关键字（例如大小写统一）
        code = normalizeKeywords(code);

        return code;
    }

    /**
     * 去除代码中的注释，包括单行注释和多行注释。
     *
     * @param code 输入的代码
     * @return 去除注释后的代码
     */
    private static String removeComments(String code) {
        // 使用正则表达式去除多行注释和单行注释
        String multiLineCommentRegex = "/\\*.*?\\*/";
        String singleLineCommentRegex = "//.*";

        Pattern multiLinePattern = Pattern.compile(multiLineCommentRegex, Pattern.DOTALL);
        Matcher multiLineMatcher = multiLinePattern.matcher(code);
        code = multiLineMatcher.replaceAll("");

        Pattern singleLinePattern = Pattern.compile(singleLineCommentRegex);
        Matcher singleLineMatcher = singleLinePattern.matcher(code);
        code = singleLineMatcher.replaceAll("");

        return code;
    }

    /**
     * 规范化代码中的关键字（例如大小写统一）。
     *
     * @param code 输入的代码
     * @return 关键字规范化后的代码
     */
    private static String normalizeKeywords(String code) {
        // 假设我们针对 C/C++ 语言，将关键字转换为小写
        String[] keywords = {
                "int", "float", "double", "if", "else", "while", "for", "return", "void", "char", "long", "short"
        };

        for (String keyword : keywords) {
            String regex = "\\b" + keyword + "\\b";
            code = code.replaceAll("(?i)" + regex, keyword); // (?i) 表示忽略大小写
        }

        return code;
    }
}

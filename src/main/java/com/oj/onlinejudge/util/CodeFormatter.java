package com.oj.onlinejudge.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/21 下午9:18
 */
public class CodeFormatter {

    private static final String INDENT = "    ";

    /**
     * 格式化代码
     * @param code 待格式化的代码
     * @return 格式化后的代码
     */
    public static String formatCode(String code) {
        // 去除多余的空格和换行符
        String trimmedCode = code.trim();

        // 按行分割代码
        String[] lines = trimmedCode.split("\\r?\\n");

        // 重新构建代码，添加适当的缩进
        StringBuilder formattedCode = new StringBuilder();
        int indentLevel = 0;
        boolean inMultiLineComment = false;

        for (String line : lines) {
            String trimmedLine = line.trim();

            // 处理多行注释
            if (inMultiLineComment) {
                if (trimmedLine.contains("*/")) {
                    inMultiLineComment = false;
                }
                formattedCode.append(line).append("\n");
                continue;
            }

            if (trimmedLine.startsWith("/*")) {
                inMultiLineComment = true;
                formattedCode.append(line).append("\n");
                continue;
            }

            // 处理单行注释
            if (trimmedLine.startsWith("//")) {
                formattedCode.append(line).append("\n");
                continue;
            }

            // 处理字符串字面量
            if (trimmedLine.contains("\"")) {
                List<String> parts = splitByStringLiteral(trimmedLine);
                for (String part : parts) {
                    if (part.startsWith("\"")) {
                        formattedCode.append(part);
                    } else {
                        formattedCode.append(formatPart(part, indentLevel));
                    }
                }
                formattedCode.append("\n");
                continue;
            }

            // 处理普通代码行
            formattedCode.append(formatPart(trimmedLine, indentLevel)).append("\n");

            if (trimmedLine.endsWith("{")) {
                indentLevel++;
            } else if (trimmedLine.startsWith("}")) {
                indentLevel--;
            }
        }

        return formattedCode.toString();
    }

    private static String formatPart(String part, int indentLevel) {
        if (part.endsWith("{")) {
            return INDENT.repeat(indentLevel) + part;
        } else if (part.startsWith("}")) {
            return INDENT.repeat(indentLevel) + part;
        } else {
            return INDENT.repeat(indentLevel) + part;
        }
    }

    private static List<String> splitByStringLiteral(String line) {
        List<String> parts = new ArrayList<>();
        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(line);

        int lastEnd = 0;
        while (matcher.find()) {
            parts.add(line.substring(lastEnd, matcher.start()));
            parts.add(matcher.group());
            lastEnd = matcher.end();
        }
        parts.add(line.substring(lastEnd));

        return parts;
    }
}

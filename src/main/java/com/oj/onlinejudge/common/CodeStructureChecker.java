package com.oj.onlinejudge.common;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/22 上午3:26
 */
public class CodeStructureChecker {

    /**
     * 检查代码中是否包含 main 函数
     *
     * @param code 要检查的 C++ 代码
     * @return 如果包含 main 函数，返回 true；否则返回 false
     */
    public static boolean hasMainFunction(String code) {
        Pattern pattern = Pattern.compile("int\\s+main\\s*\\(");
        Matcher matcher = pattern.matcher(code);
        return matcher.find();
    }

    /**
     * 检查代码中括号是否平衡
     *
     * @param code 要检查的 C++ 代码
     * @return 如果括号平衡，返回 true；否则返回 false
     */
    public static boolean checkParenthesesBalance(String code) {
        Stack<Character> stack = new Stack<>();
        for (char c : code.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty()) {
                    System.out.println("多余的右括号");
                    return false;
                }
                char top = stack.pop();
                if ((c == ')' && top != '(') || (c == '}' && top != '{') || (c == ']' && top != '[')) {
                    System.out.println("不匹配的括号");
                    return false;
                }
            }
        }
        if (!stack.isEmpty()) {
            System.out.println("缺少右括号");
            return false;
        }
        return true;
    }

    /**
     * 检查代码中是否有未关闭的字符串
     *
     * @param code 要检查的 C++ 代码
     * @return 如果字符串平衡，返回 true；否则返回 false
     */
    public static boolean checkStringBalance(String code) {
        boolean inString = false;
        for (char c : code.toCharArray()) {
            if (c == '\"') {
                inString = !inString;
            }
            if (c == '\n' && inString) {
                System.out.println("未关闭的字符串");
                return false;
            }
        }
        if (inString) {
            System.out.println("未关闭的字符串");
            return false;
        }
        return true;
    }
}

package com.oj.onlinejudge.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/22 上午3:22
 */
public class VariableDeclarationChecker {

    /**
     * 检查代码中是否有未声明的变量
     *
     * @param code 要检查的 C++ 代码
     * @return 如果有未声明的变量，返回 true；否则返回 false
     */
    public static boolean hasUndeclaredVariables(String code) {
        Set<String> declaredVariables = new HashSet<>();
        Set<String> usedVariables = new HashSet<>();

        // 检查变量声明
        String[] lines = code.split("\\n");
        for (String line : lines) {
            line = line.trim();
            if (line.endsWith(";")) {
                Matcher matcher = Pattern.compile("(\\bint|char|float|double|bool|std::string|std::vector<.*>|std::map<.*>|std::set<.*>|std::list<.*>|std::pair<.*>|std::array<.*>|std::unique_ptr<.*>|std::shared_ptr<.*>)\\s+([a-zA-Z_][a-zA-Z0-9_]*)\\s*(=|.*)?;").matcher(line);
                while (matcher.find()) {
                    String variable = matcher.group(2);
                    declaredVariables.add(variable);
                }
            }
        }

        // 检查变量使用
        for (String line : lines) {
            line = line.trim();
            Matcher matcher = Pattern.compile("\\b([a-zA-Z_][a-zA-Z0-9_]*)\\b").matcher(line);
            while (matcher.find()) {
                String variable = matcher.group(1);
                if (!declaredVariables.contains(variable) && !isKeyword(variable) && !isStandardLibraryFunction(variable)) {
                    usedVariables.add(variable);
                }
            }
        }

        // 检查未声明的变量
        for (String variable : usedVariables) {
            if (!declaredVariables.contains(variable)) {
                System.out.println("未声明的变量: " + variable);
                return true;
            }
        }

        return false;
    }

    /**
     * 检查是否为关键字
     *
     * @param word 要检查的单词
     * @return 如果是关键字，返回 true；否则返回 false
     */
    private static boolean isKeyword(String word) {
        Set<String> keywords = new HashSet<>(Arrays.asList(
                "alignas", "alignof", "and", "and_eq", "asm", "atomic_cancel", "atomic_commit", "atomic_noexcept",
                "auto", "bitand", "bitor", "bool", "break", "case", "catch", "char", "char8_t", "char16_t", "char32_t",
                "class", "compl", "concept", "const", "consteval", "constexpr", "constinit", "const_cast", "continue",
                "co_await", "co_return", "co_yield", "decltype", "default", "delete", "do", "double", "dynamic_cast",
                "else", "enum", "explicit", "export", "extern", "false", "float", "for", "friend", "goto", "if", "inline",
                "int", "long", "mutable", "namespace", "new", "noexcept", "not", "not_eq", "nullptr", "operator", "or",
                "or_eq", "private", "protected", "public", "reflexpr", "register", "reinterpret_cast", "requires", "return",
                "short", "signed", "sizeof", "static", "static_assert", "static_cast", "struct", "switch", "synchronized",
                "template", "this", "thread_local", "throw", "true", "try", "typedef", "typeid", "typename", "union",
                "unsigned", "using", "virtual", "void", "volatile", "wchar_t", "while", "xor", "xor_eq"
        ));
        return keywords.contains(word);
    }

    /**
     * 检查是否为标准库函数
     *
     * @param word 要检查的单词
     * @return 如果是标准库函数，返回 true；否则返回 false
     */
    private static boolean isStandardLibraryFunction(String word) {
        Set<String> standardLibraryFunctions = new HashSet<>(Arrays.asList(
                "std::cout", "std::endl", "std::cin", "std::cerr", "std::clog", "std::string", "std::vector", "std::map",
                "std::set", "std::list", "std::pair", "std::array", "std::unique_ptr", "std::shared_ptr"
        ));
        return standardLibraryFunctions.contains(word);
    }
}
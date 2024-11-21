package com.oj.onlinejudge.domain.map;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/21 下午11:59
 */
import java.util.HashMap;
import java.util.Map;

public class LanguageMapper {
    private static final Map<String, Integer> LANGUAGE_MAP = new HashMap<>();

    static {
        LANGUAGE_MAP.put("cpp", 52); // C++ 语言 ID
        LANGUAGE_MAP.put("python", 71); // Python 语言 ID
        // 添加其他语言的映射
    }

    public static Integer getLanguageId(String languageCode) {
        return LANGUAGE_MAP.get(languageCode);
    }
}

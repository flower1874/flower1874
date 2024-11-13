package com.oj.onlinejudge.config;

import com.alibaba.dashscope.aigc.generation.Generation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/11 下午11:36
 */
@Configuration
public class AiConfiguration {
    @Bean
    public Generation generation() {
        return new Generation();
    }
}

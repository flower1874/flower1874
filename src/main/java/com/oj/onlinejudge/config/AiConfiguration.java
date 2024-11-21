package com.oj.onlinejudge.config;

import com.alibaba.dashscope.aigc.generation.Generation;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import javax.annotation.Resource;
import java.security.KeyPair;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/11 下午11:36
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class AiConfiguration {

    @Resource
    private JwtProperties jwtProperties;
    @Bean
    public Generation generation() {
        return new Generation();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public KeyPair keyPair() {
        // 获取秘钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(
                        jwtProperties.getLocation(),
                        jwtProperties.getPassword().toCharArray());
        // 读取钥匙对
        return keyStoreKeyFactory.getKeyPair(
                jwtProperties.getAlias(),
                jwtProperties.getPassword().toCharArray());
    }
}

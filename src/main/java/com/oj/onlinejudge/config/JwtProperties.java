package com.oj.onlinejudge.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/21 下午10:46
 */
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private Resource location;
    private String password;
    private String alias;
    private String keyPass;


}
package me.xueyao.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Simon.Xue
 * @date 2020-03-03 12:12
 **/
@Configuration
@Getter
public class MpConfig {

    @Value("${mp.appId}")
    private String appId;

    @Value("${mp.appSecret}")
    private String appSecret;

    @Value("${mp.token}")
    private String token;


    @Value("${base.accessTokenUrl}")
    private String accessTokenUrl;
}

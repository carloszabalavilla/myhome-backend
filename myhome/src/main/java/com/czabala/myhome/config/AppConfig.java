package com.czabala.myhome.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppConfig {

    @Value("${spring.mail.username}")
    public static String mailUsername;

    @Value("${spring.mail.password}")
    public static String mailPassword;

    @Value("jwt.myhome.secret-key")
    public static String secretKey;

    @Value("localhost:8081/")
    public static String apiPath;

}

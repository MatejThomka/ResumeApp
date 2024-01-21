package com.mth.resume_app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "myapp")
public class AppConfig {

    private String seckey;
    private String dbUsername;
    private String dbPassword;
    private String dbUrl;

}

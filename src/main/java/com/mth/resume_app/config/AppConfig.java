package com.mth.resume_app.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "myapp")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppConfig {

    String seckey;
    String dbUsername;
    String dbPassword;
    String dbUrl;

}

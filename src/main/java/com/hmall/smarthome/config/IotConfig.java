package com.hmall.smarthome.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "iot")
public class IotConfig {

    private String projectid;

    private String endpoint;

    private String token;
}

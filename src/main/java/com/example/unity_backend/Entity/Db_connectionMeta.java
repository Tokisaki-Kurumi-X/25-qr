package com.example.unity_backend.Entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Data
@ConfigurationProperties(prefix="spring.datasource")
public class Db_connectionMeta {
    private String url;
    private String username;
    private String password;
    private String driver;
    private String options;
}

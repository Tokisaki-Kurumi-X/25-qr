package com.example.unity_backend;

import com.example.unity_backend.Entity.Db_connectionMeta;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(Db_connectionMeta.class)
public class UnityBackEndApplication {
    public static void main(String[] args){
        SpringApplication.run(UnityBackEndApplication.class,args);
    }
}

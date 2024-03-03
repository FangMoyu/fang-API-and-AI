package com.fangapi.fangapigateway;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;



@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDubbo
public class FangapiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(FangapiGatewayApplication.class, args);
    }
}

package com.fangapi.fangapiclientsdk;


import com.fangapi.fangapiclientsdk.Client.fangAiClient;
import com.fangapi.fangapiclientsdk.Client.fangApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "fangapi.client")
@Configuration
@EnableConfigurationProperties
public class FangSDKClientConfig {

    private String accessKey;
    private String secretKey;

    @Bean
    public fangApiClient CreateFangApiClient() {
        return new fangApiClient(accessKey,secretKey);
    }

    @Bean
    public fangAiClient CreateFangAiClient() {
        return new fangAiClient(accessKey,secretKey);
    }
}

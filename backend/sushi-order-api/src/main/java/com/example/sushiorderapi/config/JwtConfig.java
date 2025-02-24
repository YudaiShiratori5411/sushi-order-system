package com.example.sushiorderapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "app.jwt")
@Data
public class JwtConfig {
    private String secret;
    private long expirationMs;
    private String tokenPrefix;
    private String headerName;

    // エクスピレーション時間を取得するメソッド
    public long getExpirationTime() {
        return expirationMs;
    }
}
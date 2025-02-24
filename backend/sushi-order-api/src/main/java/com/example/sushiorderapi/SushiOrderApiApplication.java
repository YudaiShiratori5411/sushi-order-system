package com.example.sushiorderapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.sushiorderapi.model")
@EnableJpaRepositories("com.example.sushiorderapi.repository")
public class SushiOrderApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SushiOrderApiApplication.class, args);
    }
}
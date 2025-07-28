package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.repository")
@EntityScan(basePackages = "com.example.domain")
@ComponentScan(basePackages = {
        "com.example.service",
        "com.example.repository",
        "com.example.domain",
        "com.example.presentation",
        "com.example.kafka"
})
public class SpringBootBankApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootBankApp.class, args);
    }
}

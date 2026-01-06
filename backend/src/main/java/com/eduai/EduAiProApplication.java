package com.eduai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EduAiProApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduAiProApplication.class, args);
    }
}


package com.green.azuredemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AzureDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AzureDemoApplication.class, args);
    }

}

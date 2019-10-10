package com.lovezz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LovezzApplication {

    public static void main(String[] args) {
        SpringApplication.run(LovezzApplication.class, args);
    }

}

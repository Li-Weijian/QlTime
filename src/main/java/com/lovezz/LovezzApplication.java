package com.lovezz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
//@EnableScheduling
public class LovezzApplication {

    public static void main(String[] args) {


        SpringApplication.run(LovezzApplication.class, args);
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

}

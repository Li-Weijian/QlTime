package com.qltime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
//@EnableScheduling
public class QlTimeApplication {

    public static void main(String[] args) {


        SpringApplication.run(QlTimeApplication.class, args);
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

}

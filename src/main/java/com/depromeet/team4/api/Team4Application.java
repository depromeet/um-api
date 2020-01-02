package com.depromeet.team4.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Team4Application {
    public static void main(String[] args){
        SpringApplication.run(Team4Application.class, args);
    }
}

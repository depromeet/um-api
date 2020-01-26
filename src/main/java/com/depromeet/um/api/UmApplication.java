package com.depromeet.um.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@IntegrationComponentScan
public class UmApplication {
    public static void main(String[] args){
        SpringApplication.run(UmApplication.class, args);
    }
}

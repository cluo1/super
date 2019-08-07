package com.luo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SuperSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperSystemApplication.class, args);

        System.out.println("c");
    }
}

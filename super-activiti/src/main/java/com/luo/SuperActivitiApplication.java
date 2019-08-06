package com.luo;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SuperActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperActivitiApplication.class, args);
    }

}

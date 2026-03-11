package com.fantasylol.fantasy_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FantasyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FantasyApiApplication.class, args);
    }

}
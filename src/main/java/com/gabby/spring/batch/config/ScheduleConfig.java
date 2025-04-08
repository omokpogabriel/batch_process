package com.gabby.spring.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduleConfig {

    @Bean
    @Scheduled( initialDelay = 5000)
    public void getGames( ){
        System.out.println("THIS IS THE SCHEDULER");
    }
}

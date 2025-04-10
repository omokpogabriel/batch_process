package com.gabby.spring.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

public class JobCompletionListener implements JobExecutionListener {
    @BeforeJob
    public void beforeTheJob(JobExecution jobExecution){
        System.out.println("This job is starting "+ jobExecution.getStatus().name());
    }
    @AfterJob
    public void afterTheJob(JobExecution jobExecution){
        System.out.println("THIS IS has ended "+ jobExecution.getStatus().name());
    }

}

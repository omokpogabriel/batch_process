package com.gabby.spring.batch.config;

import com.gabby.spring.batch.dao.PatientDao;
import com.gabby.spring.batch.listener.JobCompletionListener;
import com.gabby.spring.batch.model.Patient;
import com.gabby.spring.batch.services.BatchProcessException;
import com.gabby.spring.batch.services.excel.ExcelReaderFactory;
import com.gabby.spring.batch.services.patient.ExcelItemReader;
import com.gabby.spring.batch.services.patient.PatientProcessor;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class excelConfig {

    @Value("${batching.skip:500}")
    private int skipLimitValue;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final ExcelReaderFactory excelReaderFactory;

    @Bean
    @StepScope
    public ExcelItemReader excelItemReader(ExcelReaderFactory excelReaderFactory){
        return new ExcelItemReader(excelReaderFactory);
    }


    @Bean
    @Primary
    public PatientProcessor processor(){
        return new PatientProcessor();
    }

    @Bean
    public JpaItemWriter<Patient> writer(EntityManagerFactory entityManagerFactory){
        JpaItemWriter<Patient> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    @Primary
    public Step importexcelStep(){

        return new StepBuilder("excelStep", jobRepository)
                .<PatientDao, Patient>chunk(50, transactionManager)
                .reader(excelItemReader(excelReaderFactory)) // we are not passing the location becuase of the JobLauncher args
                .processor(processor())
                .writer(writer(entityManagerFactory))
                .faultTolerant()
                .skipLimit(skipLimitValue)
                .skipPolicy(skipPolicy())
                .retryLimit(0)
                .listener(new SkipListener<PatientDao, Patient>() {
                    @Override
                    public void onSkipInRead(Throwable t) {
                        System.out.println("THE SKIP READ " + t.getMessage());
                       t.printStackTrace();
                    }

                    @Override
                    public void onSkipInWrite(Patient item, Throwable t) {
                        System.out.println("THE SKIP WRITE " + item.getEmail() + t.getMessage());
                    }

                    @Override
                    public void onSkipInProcess(PatientDao item, Throwable t) {
                        System.out.println("THE SKIP process " + item.getEmail() + t.getMessage());
                    }
                })
//                .exceptionHandler()
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("async-batching");
        executor.initialize();
        return executor;
    }

    @Bean
    public SkipPolicy skipPolicy(){

        return new SkipPolicy() {
            @Override
            public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
                if(t instanceof BatchProcessException){
                    return true;
                }

                return false;
            }
        };

    }

    @Bean
    @Primary
    public Job runJobs(){

            return new JobBuilder("excelJob", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .listener(new JobExecutionListener() {
                        @Override
                        public void beforeJob(JobExecution jobExecution) {
                            JobExecutionListener.super.beforeJob(jobExecution);
                            System.out.println("THIS IS NICE");
                        }

                        @Override
                        public void afterJob(JobExecution jobExecution) {
                            JobExecutionListener.super.afterJob(jobExecution);
                            System.out.println("THIS IS closed" + jobExecution.getStatus() + " " +jobExecution.getJobInstance().getJobName());
                        }
                    })
                    .start(importexcelStep())
                    .build();
    }

}

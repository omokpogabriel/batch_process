package com.gabby.spring.batch.config;

import com.gabby.spring.batch.dao.PatientDao;
import com.gabby.spring.batch.model.Patient;
import com.gabby.spring.batch.services.patient.ExcelItemReader;
import com.gabby.spring.batch.services.patient.PatientProcessor;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.nio.file.FileAlreadyExistsException;
import java.util.NoSuchElementException;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class excelConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final ExcelItemReader excelItemReader;

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
                .<PatientDao, Patient>chunk(50 comm, transactionManager)
                .reader(excelItemReader) // we are not passing the location becuase of the JobLauncher args
                .processor(processor())
                .writer(writer(entityManagerFactory))
                .faultTolerant()
                .skip(ConstraintViolationException.class)
                .skip(FileAlreadyExistsException.class)
                .skip(NoSuchElementException.class)
                .skip(Exception.class)
                .skipLimit(1000)
                .listener(new SkipListener<PatientDao, Patient>() {
                    @Override
                    public void onSkipInRead(Throwable t) {
                        System.out.println("THE SKIP READ " + t.getMessage());
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
//                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    @Primary
    public Job runJobs(){
            return new JobBuilder("excelJob", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .start(importexcelStep())
                    .build();
    }

}

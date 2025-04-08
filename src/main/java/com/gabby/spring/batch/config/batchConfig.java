package com.gabby.spring.batch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class batchConfig {
/*
    private final JobRepository jobRepository;
    private final StudentRepository repository;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public FlatFileItemReader<Student> itemReader(){

        FlatFileItemReader<Student> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/students.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    @Bean
    public StudentProcessor SingleProcessor(){
        return new StudentProcessor();
    }

    @Bean
    public RepositoryItemWriter<Student> bWriter(){
        RepositoryItemWriter<Student> writer = new RepositoryItemWriter<>();
        writer.setRepository(repository);
        writer.setMethodName("save");

        return writer;
    }

    @Bean
    public Step importStep(){
        return new StepBuilder("csvImport", jobRepository)
                .<Student, Student>chunk(1000, platformTransactionManager)
                .reader(itemReader())
                .processor(SingleProcessor())
                .writer(bWriter())
                .faultTolerant()
                .retry(SQLException.class)
                .retryLimit(1)
                .skip(NumberFormatException.class)
                .skipLimit(5)
                .build();
    }

    @Bean
    public Job runJob(){
        return new JobBuilder("importstudents", jobRepository)
                .start(importStep())
                .next(multiThreadedStep())
//                .next()  if you have more than one steo
                .build();
    }

    private LineMapper<Student> lineMapper(){
        DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id","firstname","lastname", "age");

        BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Student.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;

    }



    public MultiThreadedItemProcessor itemProcessor(){
        return new MultiThreadedItemProcessor();
    }
    @Bean
    public Step multiThreadedStep() {
        return new StepBuilder("multiThreadedStep", jobRepository)
                .<Student, Student>chunk(10, platformTransactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(bWriter())
                .taskExecutor(new SimpleAsyncTaskExecutor()) // Enables multithreading
                .build();
    }
*/
}

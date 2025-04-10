package com.gabby.spring.batch.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final JobLauncher jobLauncher;
    private final Job job;

    @PostMapping
    public void importCsvToDbJob(@RequestParam(name = "file") MultipartFile file) throws IOException {



//        if(file.isEmpty() || file.getContentType() != "application/vnd.ms-excel"){
//            throw new IllegalArgumentException("Invalid file content type");
//        }

        String fileName = file.getOriginalFilename();

        String[] fileFormatFinder = fileName.split("\\.");

        if(fileFormatFinder.length < 1){
            throw new IllegalArgumentException("another error");
        }
        String fileFormat = '.' + fileFormatFinder[fileFormatFinder.length - 1];

          File tempFile = File.createTempFile("patients", fileFormat);
        System.out.println("the temp file"+ tempFile.getAbsolutePath());
          file.transferTo(tempFile);

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("run.id", UUID.randomUUID().toString())
                .addString("filePath", tempFile.getAbsolutePath())
                .toJobParameters();

        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            e.printStackTrace();
        }

    }


}

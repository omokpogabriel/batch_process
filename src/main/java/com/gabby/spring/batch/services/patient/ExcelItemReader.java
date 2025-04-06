package com.gabby.spring.batch.services.patient;

import com.gabby.spring.batch.dao.PatientDao;
import com.gabby.spring.batch.services.excel.ExcelReaderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExcelItemReader implements ItemReader<PatientDao>, StepExecutionListener {

    private final ExcelReaderFactory excelReaderFactory;
    private ItemReader<PatientDao> delegateReader;
    private StepExecution stepExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
        System.out.println("StepExecution: " + stepExecution);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }


    @Override
    public PatientDao read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
       if(delegateReader == null){

//           JobParameters path = stepExecution.getJobParameters();
//
//           String filePath = path.getString("path");

           String filePath = new ClassPathResource("hello.xls").getFile().getAbsolutePath(); // this can be done throught the
           System.out.println(filePath);

           delegateReader = excelReaderFactory.createReader(filePath);
       }
        return delegateReader.read();
    }
}

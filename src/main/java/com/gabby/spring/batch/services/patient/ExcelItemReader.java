package com.gabby.spring.batch.services.patient;

import com.gabby.spring.batch.dao.PatientDao;
import com.gabby.spring.batch.services.excel.ExcelReaderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.*;

import java.io.File;
import java.io.FileNotFoundException;

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

        if (delegateReader instanceof ItemStream) {
            try {
                ((ItemStream) delegateReader).close();
            } catch (Exception e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }

        return ExitStatus.COMPLETED;
    }


    @Override
    public PatientDao read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
       if(delegateReader == null){

           JobParameters path = stepExecution.getJobParameters();

           String filePath = path.getString("filePath");

           delegateReader = excelReaderFactory.createReader(filePath);

           if (delegateReader instanceof ItemStream) { // for filebase reading  the csv. the xls does not need this
               ((ItemStream) delegateReader).open(new ExecutionContext());
           }

       }
        return delegateReader.read();
    }
}

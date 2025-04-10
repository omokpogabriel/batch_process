package com.gabby.spring.batch.services.excel;

import com.gabby.spring.batch.dao.PatientDao;
import com.gabby.spring.batch.services.csv.PatientFieldsetMapper;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

public class ExcelCsvReader implements ItemReader<PatientDao> {

    private String filePath;
    private FlatFileItemReader<PatientDao> csvFileReader;

    public ExcelCsvReader(String filepath){
        this.filePath = filepath;
        this.csvFileReader = itemReader();
    }


    public FlatFileItemReader<PatientDao> itemReader() {

        FlatFileItemReader<PatientDao> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(1);
        reader.setResource(new FileSystemResource(filePath));
        reader.setName("patientCsv");
        reader.setLineMapper(lineMapper());
        reader.open(new ExecutionContext());
        return reader;
    }

    private LineMapper<PatientDao> lineMapper(){
        DefaultLineMapper<PatientDao> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("id", "title", "first_name", "middle_name", "last_name", "email", "phone_number", "puid", "passport_photograph",
                "date_of_birth", "gender", "whatsapp_number", "status", "status_change_reason", "type", "organization_id", "smarthealth_id", "primary_location"
        );
        lineTokenizer.setStrict(false);
        lineTokenizer.setDelimiter(",");

        BeanWrapperFieldSetMapper<PatientDao> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(PatientDao.class);

        fieldSetMapper.setStrict(false);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(new PatientFieldsetMapper());
        return lineMapper;
    }

    @Override
    public PatientDao read() throws Exception{
        return csvFileReader.read();
    }

}

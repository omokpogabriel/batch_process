package com.gabby.spring.batch.services.excel;

import com.gabby.spring.batch.dao.PatientDao;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
public class ExcelReaderFactory {

    public ItemReader<PatientDao> createReader(String filePath) {
        if(filePath.endsWith(".xls")){
            return new ExcelXlsReader(filePath);
        } else if(filePath.endsWith(".xlsx")){
            return new ExcelXlsReader(filePath);
        }else {
            throw new IllegalArgumentException( String.format("Unsupported file type %s",filePath));
        }
    }


}

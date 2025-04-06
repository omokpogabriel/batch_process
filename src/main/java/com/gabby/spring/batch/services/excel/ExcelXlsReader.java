package com.gabby.spring.batch.services.excel;

import com.gabby.spring.batch.dao.PatientDao;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelXlsReader implements ItemReader<PatientDao> {

    private Workbook workbook;
    private Iterator<Row> rowIterator;

    public ExcelXlsReader(String filePath){
        try{
            FileInputStream fileInputStream = new FileInputStream(filePath);
            this.workbook = new HSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            this.rowIterator = sheet.iterator();

            if(rowIterator.hasNext()) rowIterator.next(); //skip header

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public PatientDao read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
       if(!rowIterator.hasNext()){
           workbook.close();
           return null;
       }

        Row row = rowIterator.next();
        PatientDao patientDao = new PatientDao();
        patientDao.setCuid( (String) row.getCell(3).getStringCellValue());
        patientDao.setUpi( (String) row.getCell(2).getStringCellValue());
        patientDao.setLastname( (String) row.getCell(1).getStringCellValue());
        patientDao.setFirstname( (String) row.getCell(0).getStringCellValue());

        return patientDao;
    }
}

package com.gabby.spring.batch.services.excel;

import com.gabby.spring.batch.dao.PatientDao;
import com.gabby.spring.batch.model.Organization;
import com.gabby.spring.batch.repository.OrganizationRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;

public class ExcelXlsReader implements ItemReader<PatientDao> {

    @Autowired
    private OrganizationRepository organizationRepository;
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

        patientDao.setId(row.getCell(0).getStringCellValue());
        patientDao.setTitle(row.getCell(1).getStringCellValue());
        patientDao.setFirstName(row.getCell(2).getStringCellValue());
        patientDao.setMiddleName(row.getCell(3).getStringCellValue());
        patientDao.setLastName(row.getCell(4).getStringCellValue());
        patientDao.setEmail(row.getCell(5).getStringCellValue());
        patientDao.setPhoneNumber(row.getCell(6).getStringCellValue());
        patientDao.setPuid(row.getCell(7).getStringCellValue());
        patientDao.setPassportPhotograph(row.getCell(8).getStringCellValue());
        if (row.getCell(9).getCellType() == CellType.NUMERIC) {
            patientDao.setDateOfBirth(row.getCell(9).getLocalDateTimeCellValue());
        } else {
            // fallback or parsing if it's a string (optional)
            patientDao.setDateOfBirth(LocalDateTime.parse(row.getCell(9).getStringCellValue()));
        }
        patientDao.setGender(row.getCell(10).getStringCellValue());
        patientDao.setWhatsappNumber(row.getCell(11).getStringCellValue());
        patientDao.setStatus(Boolean.parseBoolean(row.getCell(12).getStringCellValue()));
        patientDao.setStatusChangeReason(row.getCell(13).getStringCellValue());
        patientDao.setType(row.getCell(14).getStringCellValue());

        Organization org = organizationRepository.findById(row.getCell(15).getStringCellValue())
                .orElseThrow(() -> new RuntimeException("Org not found"));
        patientDao.setOrganization(org);

        patientDao.setSmarthealthId(row.getCell(16).getStringCellValue());
        patientDao.setPrimaryLocation(row.getCell(17).getStringCellValue());


        return patientDao;
    }
}

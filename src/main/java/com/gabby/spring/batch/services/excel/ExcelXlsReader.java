package com.gabby.spring.batch.services.excel;

import com.gabby.spring.batch.dao.PatientDao;
import com.gabby.spring.batch.repository.OrganizationRepository;
import com.gabby.spring.batch.services.BatchProcessException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
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

            var row = sheet.getRow(0).iterator();
            while(!row.hasNext()){
                workbook.close();
                throw new IllegalArgumentException("Invalid excel format");
            }

            if(rowIterator.hasNext()) rowIterator.next(); //skip header

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public PatientDao read() throws Exception {
       if(!rowIterator.hasNext()){
           workbook.close();
           return null;
       }
        Row row = rowIterator.next();
        PatientDao patientDao = new PatientDao();
        var res = row.iterator();
            patientDao.setId( formatCell(res).getStringCellValue());
            patientDao.setTitle( formatCell(res).getStringCellValue());
            patientDao.setFirstName(formatCell(res).getStringCellValue());
            patientDao.setMiddleName(formatCell(res).getStringCellValue());
            patientDao.setLastName(formatCell(res).getStringCellValue());
            patientDao.setEmail(formatCell(res).getStringCellValue());
            patientDao.setPhoneNumber(formatCell(res).getStringCellValue());
            patientDao.setPuid(formatCell(res).getStringCellValue());
            patientDao.setPassportPhotograph(formatCell(res).getStringCellValue());
            var dbo = formatCell(res);
            if (dbo.getCellType() == CellType.NUMERIC) {
                patientDao.setDateOfBirth(dbo.getLocalDateTimeCellValue());
            } else {
                patientDao.setDateOfBirth(LocalDateTime.parse(dbo.getStringCellValue()));
            }
            patientDao.setGender(formatCell(res).getStringCellValue());
            patientDao.setWhatsappNumber(formatCell(res).getStringCellValue());
            patientDao.setStatus( getStatus(res));
            patientDao.setStatusChangeReason(formatCell(res).getStringCellValue());
            patientDao.setType(formatCell(res).getStringCellValue());
            patientDao.setOrganizationId(formatCell(res).getStringCellValue());
            patientDao.setSmarthealthId(formatCell(res).getStringCellValue());
            patientDao.setPrimaryLocation(formatCell(res).getStringCellValue());


           return patientDao;

    }

    private Boolean getStatus(Iterator<Cell> cellIterator) throws BatchProcessException {

        if( !cellIterator.hasNext()){
            return false;
        }

        Cell cell = cellIterator.next();
        try{
            if(cell.getCellType() == CellType.BOOLEAN){
                return cell.getBooleanCellValue();
            }else{
                return Boolean.getBoolean(cell.getStringCellValue());
            }
        }catch (Exception ex) {
            throw new BatchProcessException("Failed to read bool" + cell.getCellType());
        }

    }

    private Cell formatCell(Iterator<Cell> currentIterator) throws BatchProcessException {

          if( !currentIterator.hasNext()){
              return new EmptyCellTypeValue();
          }

          Cell cell = currentIterator.next();
        try {
            if (cell.getCellType() == CellType.BLANK || cell.getCellType() == CellType._NONE || cell.getCellType() == CellType.ERROR) {
                return new EmptyCellTypeValue();
            }
            return cell;
        } catch (Exception ex) {
            throw new BatchProcessException("Failed to read " + cell.getCellType());
        }
    }

}

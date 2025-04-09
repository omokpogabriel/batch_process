package com.gabby.spring.batch.services.excel;

import com.gabby.spring.batch.dao.PatientDao;
import com.gabby.spring.batch.repository.OrganizationRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
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

            var row1 = sheet.getRow(0).iterator();
            while(!row1.hasNext()){
                workbook.close();
                throw new IllegalArgumentException("Invalid excel format");
            }

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
        var res = row.iterator();

           patientDao.setId( formatCell(res.next()).getStringCellValue());
           patientDao.setTitle( formatCell(res.next()).getStringCellValue());
           patientDao.setFirstName(formatCell(res.next()).getStringCellValue());
           patientDao.setMiddleName(formatCell(res.next()).getStringCellValue());
           patientDao.setLastName(formatCell(res.next()).getStringCellValue());
           patientDao.setEmail(formatCell(res.next()).getStringCellValue());
           patientDao.setPhoneNumber(formatCell(res.next()).getStringCellValue());
           patientDao.setPuid(formatCell(res.next()).getStringCellValue());
           patientDao.setPassportPhotograph(formatCell(res.next()).getStringCellValue());
           var dbo = formatCell(res.next());
           if (dbo.getCellType() == CellType.NUMERIC) {
               patientDao.setDateOfBirth(dbo.getLocalDateTimeCellValue());
           } else {
               patientDao.setDateOfBirth(LocalDateTime.parse(dbo.getStringCellValue()));
           }
           patientDao.setGender(formatCell(res.next()).getStringCellValue());
           patientDao.setWhatsappNumber(formatCell(res.next()).getStringCellValue());
           patientDao.setStatus( getStatus(res.next()));
           patientDao.setStatusChangeReason(formatCell(res.next()).getStringCellValue());
           patientDao.setType(formatCell(res.next()).getStringCellValue());
           patientDao.setOrganizationId(formatCell(res.next()).getStringCellValue());
           patientDao.setSmarthealthId(formatCell(res.next()).getStringCellValue());
           patientDao.setPrimaryLocation(formatCell(res.next()).getStringCellValue());

           return patientDao;



    }

    private Boolean getStatus(Cell cell){
        if(cell.getCellType() == CellType.BOOLEAN){
            return cell.getBooleanCellValue();
        }else{
            return Boolean.getBoolean(cell.getStringCellValue());
        }
    }

    private Cell formatCell(Cell cell) throws Exception {
        try {
            if (cell.getCellType() == CellType.BLANK || cell.getCellType() == CellType._NONE || cell.getCellType() == CellType.ERROR) {
                cell.setCellValue("");
            }

            return cell;
        } catch (Exception ex) {
            System.out.println(
                    String.format("THE EXCEPTION  cell type %s , the message %s the rowIndex %s the column index %s",
                    cell.getCellType(),
                    ex.getMessage(),
                    cell.getRowIndex(),
                    cell.getColumnIndex())
            );
            throw new Exception("Failed to read " + cell.getCellType());
        }
    }


}

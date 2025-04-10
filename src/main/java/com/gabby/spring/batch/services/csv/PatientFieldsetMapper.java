package com.gabby.spring.batch.services.csv;

import com.gabby.spring.batch.dao.PatientDao;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PatientFieldsetMapper implements FieldSetMapper<PatientDao> {
    @Override
    public PatientDao mapFieldSet(FieldSet fieldSet) throws BindException {
        PatientDao patient = new PatientDao();

        patient.setId(fieldSet.readString("id"));
        patient.setTitle(fieldSet.readString("title"));
        patient.setFirstName(fieldSet.readString("first_name"));
        patient.setMiddleName(fieldSet.readString("middle_name"));
        patient.setLastName(fieldSet.readString("last_name"));
        patient.setEmail(fieldSet.readString("email"));
        patient.setPhoneNumber(fieldSet.readString("phone_number"));
        patient.setPuid(fieldSet.readString("puid"));
        patient.setPassportPhotograph(fieldSet.readString("passport_photograph"));
        patient.setDateOfBirth(parseDate(fieldSet.readString("date_of_birth"))); // or use readString if LocalDateTime conversion is custom

        // Default values
        patient.setGender(fieldSet.readString("gender"));
        patient.setWhatsappNumber(fieldSet.readString("whatsapp_number"));
        patient.setStatus(fieldSet.readBoolean("status", "false"));
        patient.setStatusChangeReason(fieldSet.readString("status_change_reason"));
        patient.setType(fieldSet.readString("type"));
        patient.setOrganizationId(fieldSet.readString("organization_id"));
        patient.setSmarthealthId(fieldSet.readString("smarthealth_id"));
        patient.setPrimaryLocation(fieldSet.readString("primary_location"));

        return patient;
    }


    private LocalDateTime parseDate(String rawDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(rawDate, formatter);
        return date.atStartOfDay();

    }
}

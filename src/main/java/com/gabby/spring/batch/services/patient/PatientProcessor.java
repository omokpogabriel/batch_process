package com.gabby.spring.batch.services.patient;

import com.gabby.spring.batch.dao.PatientDao;
import com.gabby.spring.batch.model.Patient;
import com.gabby.spring.batch.repository.PatientRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class PatientProcessor implements ItemProcessor<PatientDao, Patient> {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Patient process(PatientDao row) throws Exception {
        Optional<Patient> patients = patientRepository.findByPuid(row.getPuid());

        if(patients.isPresent()){
            // you can throw an exception here if you so choose
            return null;
        }

        return Patient.builder()
                .id(row.getId())
                .title(row.getTitle())
                .firstName(row.getFirstName())
                .middleName(row.getMiddleName())
                .lastName(row.getLastName())
                .email(row.getEmail())
                .phoneNumber(row.getPhoneNumber())
                .puid(row.getPuid())
                .passportPhotograph(row.getPassportPhotograph())
                .dateOfBirth(row.getDateOfBirth())
                .gender(row.getGender())
                .whatsappNumber(row.getWhatsappNumber())
                .status(row.getStatus())
                .statusChangeReason(row.getStatusChangeReason())
                .type(row.getType())
                .organization(row.getOrganization())
                .smarthealthId(row.getSmarthealthId())
                .primaryLocation(row.getPrimaryLocation())
                .build();
    }
}

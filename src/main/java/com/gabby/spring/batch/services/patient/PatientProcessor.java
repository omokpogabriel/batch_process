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
        Optional<Patient> patients = patientRepository.findByCuid(row.getCuid());

        if(patients.isPresent()){
            // you can throw an exception here if you so choose
            return null;
        }

        return Patient.builder()
                .upi(row.getUpi())
                .cuid(row.getCuid())
                .firstname(row.getFirstname())
                .lastname(row.getLastname())
                .build();
    }
}

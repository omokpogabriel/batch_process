package com.gabby.spring.batch.services.patient;

import com.gabby.spring.batch.dao.PatientDao;
import com.gabby.spring.batch.model.Organization;
import com.gabby.spring.batch.model.Patient;
import com.gabby.spring.batch.repository.OrganizationRepository;
import com.gabby.spring.batch.repository.PatientRepository;
import com.gabby.spring.batch.services.BatchProcessException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class PatientProcessor implements ItemProcessor<PatientDao, Patient> {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public Patient process(PatientDao row) throws Exception {
        Optional<Patient> patients = patientRepository.findByPuid(row.getPuid());

        if(patients.isPresent()){
            // you can throw an exception here if you so choose
            throw new BatchProcessException("The value already exists");
        }

        var getOrganization = organizationRepository.findById( row.getOrganizationId());
        Organization res;

        if(getOrganization.isEmpty() ){
            Organization org = new Organization();
            org.setId(row.getOrganizationId());
            org.setAddress("Present case");
            org.setName("Lagoon hospital");
            org.setRcNumber("dfdfdfd");
            org.setEmail("default@gmail.com");
            org.setMobileNumber("49374837434");
            org.setOrganizationType("hospital");

            res = organizationRepository.save(org);
        }else {
            res = getOrganization.get();
        }

//        System.out.println("THE FINAL  organization " +res);
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
                .organization(res)
                .smarthealthId(row.getSmarthealthId())
                .primaryLocation(row.getPrimaryLocation())
                .build();
    }
}

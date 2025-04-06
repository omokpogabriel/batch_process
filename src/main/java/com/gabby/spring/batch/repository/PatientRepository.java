package com.gabby.spring.batch.repository;

import com.gabby.spring.batch.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer > {

    public Optional<Patient> findByCuid(String cuid);
}

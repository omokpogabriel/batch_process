package com.gabby.spring.batch.repository;

import com.gabby.spring.batch.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String> {

//    public Organization findByPatientId(String organizationId);
     public Optional<Organization> findById(String organizationId);

}

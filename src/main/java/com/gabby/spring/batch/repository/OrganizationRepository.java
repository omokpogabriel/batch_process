package com.gabby.spring.batch.repository;

import com.gabby.spring.batch.model.Organization;
import com.gabby.spring.batch.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String> {

//    public Organization findByPatientId(String organizationId);

}

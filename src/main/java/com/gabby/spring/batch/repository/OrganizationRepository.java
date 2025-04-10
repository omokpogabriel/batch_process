package com.gabby.spring.batch.repository;

import com.gabby.spring.batch.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String> {

     @Query(value = "SELECT * FROM organizations WHERE id = :organizationId FOR UPDATE SKIP LOCKED", nativeQuery = true)
     public Optional<Organization> findById(String organizationId);

}

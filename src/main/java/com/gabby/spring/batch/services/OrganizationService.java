package com.gabby.spring.batch.services;

import com.gabby.spring.batch.model.Organization;
import com.gabby.spring.batch.repository.OrganizationRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Transactional
    public Optional<Organization> findOrganizationById(String id){
        return organizationRepository.findById(id);
    }
    @Transactional
    public Organization save(Organization  organization){
        return organizationRepository.save(organization);
    }

}

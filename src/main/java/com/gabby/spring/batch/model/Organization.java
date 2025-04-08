package com.gabby.spring.batch.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "organizations", indexes = {@Index(name = "idx_organization_id", columnList = "id")})
public class Organization {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "rc_number")
    private String rcNumber;

    @Column(name = "smarthealth_id")
    private String smarthealthId;

    private String address;

    @Column(name = "organization_type")
    private String organizationType;

    @Column(name = "parent_id")
    private String parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Organization parent;

    @OneToMany(mappedBy = "parent")
    private List<Organization> children;

    @OneToMany(mappedBy = "organization")
    private List<Patient> patients;

}

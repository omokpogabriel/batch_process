package com.gabby.spring.batch.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder

@Table(name = "patients", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"puid", "organization_id"}),
        @UniqueConstraint(columnNames = {"nickname"})
})

public class Patient {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false)
    private String puid;

    @Column(name = "passport_photograph")
    private String passportPhotograph;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDateTime dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(name = "whatsapp_number")
    private String whatsappNumber;

    @Column(nullable = false)
    private Boolean status = true;

    @Column(name = "status_change_reason")
    private String statusChangeReason;

    @Column(nullable = false)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(name = "smarthealth_id")
    private String smarthealthId;

    @Column(name = "primary_location")
    private String primaryLocation;

//    @OneToOne(mappedBy = "patient")
//    private PatientInfo patientInfo;
//
//    @OneToOne(mappedBy = "patient")
//    private NextOfKin nextOfKin;
//
//    @OneToOne(mappedBy = "patient")
//    private ClinicalInfo clinicalInfo;
//
//    @OneToOne(mappedBy = "patient")
//    private CareTeam careTeam;
//
//    @OneToMany(mappedBy = "patient")
//    private List<Sponsor> sponsor;
//
//    @OneToMany(mappedBy = "patient")
//    private List<Appointment> appointments;
//
//    @OneToMany(mappedBy = "patient")
//    private List<Admission> admissions;
//
//    @OneToMany(mappedBy = "patient")
//    private List<RequestAdmission> requestAdmissions;
//
//    @OneToMany(mappedBy = "patient")
//    private List<Allergy> allergy;
//
//    @OneToMany(mappedBy = "patient")
//    private List<VitalSign> vitalSign;
//
//    @ManyToOne
//    @JoinColumn(name = "family_folder_id")
//    private FamilyFolder familyFolder;
//
//    @OneToMany(mappedBy = "patient")
//    private List<DocumentUpload> documentUpload;
//
//    @ManyToOne
//    @JoinColumn(name = "contact_person_id")
//    private FamilyFolder contactPerson;
//
//    @OneToMany(mappedBy = "patient")
//    private List<Task> tasks;

}

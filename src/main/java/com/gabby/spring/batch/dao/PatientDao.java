package com.gabby.spring.batch.dao;

import com.gabby.spring.batch.model.Organization;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PatientDao {
    private String id;
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String puid;
    private String passportPhotograph;
    private LocalDateTime dateOfBirth;
    private String gender;
    private String whatsappNumber;
    private Boolean status;
    private String statusChangeReason;
    private String type;
    private String organizationId;
    private String smarthealthId;
    private String primaryLocation;
}

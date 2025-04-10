package com.gabby.spring.batch.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PatientDao {


    private String id;
    private String title;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("middle_name")
    private String middleName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String puid;
    @JsonProperty("passport_photograph")
    private String passportPhotograph;
    @JsonProperty("date_of_birth")
    private LocalDateTime dateOfBirth;
    private String gender;
    @JsonProperty("whatsapp_number")
    private String whatsappNumber;
    private Boolean status;
    @JsonProperty("status_change_reason")
    private String statusChangeReason;
    private String type;
    @JsonProperty("organization_id")
    private String organizationId;
    @JsonProperty("smarthealth_id")
    private String smarthealthId;
    @JsonProperty("primary_location")
    private String primaryLocation;
}

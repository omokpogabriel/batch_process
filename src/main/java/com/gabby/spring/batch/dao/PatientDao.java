package com.gabby.spring.batch.dao;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
@Setter
public class PatientDao {
    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private String upi;

    private String cuid;
}

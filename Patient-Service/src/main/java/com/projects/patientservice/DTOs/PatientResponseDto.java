package com.projects.patientservice.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class PatientResponseDto {
    private UUID id;
    private String name;
    private String email;
    private String address;
    private LocalDate dateOfBirth;
}

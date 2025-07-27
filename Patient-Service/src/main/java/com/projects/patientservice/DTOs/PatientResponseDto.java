package com.projects.patientservice.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Getter
@Setter
public class PatientResponseDto {

    private String name;
    private String email;
    private String address;
    private LocalDate dateOfBirth;
}

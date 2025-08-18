package com.projects.patientservice;

import com.projects.patientservice.Controller.PatientController;
import com.projects.patientservice.DTOs.PatientResponseDto;
import com.projects.patientservice.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class PatientServiceApplication implements CommandLineRunner {

//    private final PatientController patientController;
//
//    @Autowired
//    public PatientServiceApplication(PatientController patientController) {
//        this.patientController = patientController;
//    }


    public static void main(String[] args) {
        SpringApplication.run(PatientServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        PatientResponseDto patientResponseDto = new PatientResponseDto();
//        System.out.println("############################################3");
//        List<PatientResponseDto> patients = patientController.getPatients();
//        for(PatientResponseDto patient:patients){
//            System.out.println("Patient Name: " + patient.getName());
//            System.out.println("Patient Email: " + patient.getEmail());
//            System.out.println("Patient Address: " + patient.getAddress());
//            System.out.println("Patient Mobile Number: " + patient.getMobileNumber());
//            System.out.println("Patient Date of Birth: " + patient.getDateOfBirth());
//            System.out.println("-------------------------------------------------");
//        }

    }
}

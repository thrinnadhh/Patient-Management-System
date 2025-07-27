package com.projects.patientservice.Services;

import com.projects.patientservice.DTOs.PatientRequestDto;
import com.projects.patientservice.DTOs.PatientResponseDto;
import com.projects.patientservice.Repository.PatientRepository;
import com.projects.patientservice.mapper.Patientmapper;
import com.projects.patientservice.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    public List<PatientResponseDto> getPatients(){
//        System.out.println("#######################################3333");
        System.out.println("Fetching all patients");
        List<Patient> patients = patientRepository.findAll();
        if(patients.isEmpty()){
//            System.out.println("******************************************");
//            System.out.println("No patients found");
            throw new RuntimeException("No patients found");
        }
        else{
//            System.out.println("******************************************2222");

            return patients.stream().map(Patientmapper::toDto).toList();
//            return patients.stream().map(patient ->(Patientmapper.toDto(patient))).toList();
        }
    }
    public PatientResponseDto RegisterPatient(PatientRequestDto patient) {
        Patient patientRegistration = Patientmapper.toPatient(patient);
        patientRepository.save(patientRegistration);
        return Patientmapper.toDto(patientRegistration);
    }

}

package com.projects.patientservice.Services;

import com.projects.patientservice.DTOs.PatientRequestDto;
import com.projects.patientservice.DTOs.PatientResponseDto;
import com.projects.patientservice.Exception.EmailAlreadyExistsException;
import com.projects.patientservice.Exception.PatientNotFoundException;
import com.projects.patientservice.Repository.PatientRepository;
import com.projects.patientservice.grpc.BillingServiceGrpcClient;
import com.projects.patientservice.kafka.PatientProducer;
import com.projects.patientservice.mapper.Patientmapper;
import com.projects.patientservice.models.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final PatientProducer patientProducer;

    @Autowired
    public PatientService(PatientRepository patientRepository,
                          BillingServiceGrpcClient billingServiceGrpcClient,
                          PatientProducer patientProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.patientProducer = patientProducer;


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
    public PatientResponseDto RegisterPatient(PatientRequestDto patientRequestDto) {

        if(patientRepository.existsByEmail(patientRequestDto.getEmail())){
            throw new EmailAlreadyExistsException("Patient with this email already exists "+patientRequestDto.getEmail());
        }
        Patient patientRegistration = Patientmapper.toPatient(patientRequestDto);
        patientRepository.save(patientRegistration);
        log.info("Patient registered successfully with id: {}", patientRegistration.getId());
        billingServiceGrpcClient.createBillingAccount(patientRegistration.getId().toString(),
                patientRegistration.getName(),
                patientRegistration.getEmail());
//        kafka
        patientProducer.sendEvent(patientRegistration);

      return Patientmapper.toDto(patientRegistration);
    }

    public PatientResponseDto updatePatient(UUID id,PatientRequestDto patientRequestDto){
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if(optionalPatient.isEmpty()){
            throw new PatientNotFoundException("Patient with id "+id+" not found");
        }
        if(patientRepository.existsByEmailAndIdNot(patientRequestDto.getEmail(), id)){
            throw new EmailAlreadyExistsException("Patient with this email already exists "+patientRequestDto.getEmail());
        }
        Patient existingPatient = optionalPatient.get();
        existingPatient.setId(id);
        existingPatient.setName(patientRequestDto.getName());
        existingPatient.setEmail(patientRequestDto.getEmail());
        existingPatient.setAddress(patientRequestDto.getAddress());
        existingPatient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));
        existingPatient.setRegisteredDate(LocalDate.parse(patientRequestDto.getRegisteredDate()));
        patientRepository.save(existingPatient);
        return Patientmapper.toDto(existingPatient);
    }

//    logic to delete a patient provided by the patient id
    public void deletePatient(UUID id){
        patientRepository.deleteById(id);
    }

}

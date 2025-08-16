package com.projects.patientservice.kafka;

import com.projects.patientservice.models.Patient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;


@Service
public class PatientProducer {
    private static final Log log = LogFactory.getLog(PatientProducer.class);
    private final KafkaTemplate<String,byte[]> kafkaTemplate;
    public PatientProducer(KafkaTemplate<String,byte[]> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient) {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("Patient Created")
                .build();

        try {
            kafkaTemplate.send("patient", event.toByteArray());
        } catch (Exception e) {
            log.error("Error while sending patient event to kafka :{}" +event);
        }
    }
}

package com.synersence.hospital.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.synersence.hospital.entity.PatientMaster;
import com.synersence.hospital.repository.PatientMasterRepository;

@Service
public class PatientMasterService {

    private final PatientMasterRepository repository;

    public PatientMasterService(PatientMasterRepository repository) {
        this.repository = repository;
    }

    public void savePatient(PatientMaster patient) {
        repository.save(patient);
    }

    public List<PatientMaster> getAllPatients() {
        return repository.findAll();
    }
    public PatientMaster getPatientById(String patientId) {
        return repository.findById(patientId).orElse(null);
    }

}
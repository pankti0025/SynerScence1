package com.synersence.hospital.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.synersence.hospital.entity.FieldCustomization;
import com.synersence.hospital.repository.FieldCustomizationRepository;

@Service
public class FieldCustomizationService {

    private final FieldCustomizationRepository repository;

    public FieldCustomizationService(FieldCustomizationRepository repository) {
        this.repository = repository;
    }

    public void save(FieldCustomization field) {
        repository.save(field);   // 🔴 THIS WAS FAILING EARLIER
    }

    public List<FieldCustomization> getAllFields() {
        return repository.findAll();
    }
}

package com.synersence.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.synersence.hospital.entity.FieldCustomization;

public interface FieldCustomizationRepository
        extends JpaRepository<FieldCustomization, Long> {
}

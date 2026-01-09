package com.synersence.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.synersence.hospital.entity.PatientMaster;

public interface PatientMasterRepository extends JpaRepository<PatientMaster, String> {
    // You can add custom query methods here if needed
}

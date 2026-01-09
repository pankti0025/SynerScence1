package com.synersence.hospital.repository;

import com.synersence.hospital.entity.KycRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycRepository extends JpaRepository<KycRecord, Long> {
}

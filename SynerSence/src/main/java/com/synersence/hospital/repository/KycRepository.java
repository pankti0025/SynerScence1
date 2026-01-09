package com.synersence.hospital.repository;

import com.synersence.hospital.entity.KycRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KycRepository extends MongoRepository<KycRecord, String> {
}

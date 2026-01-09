package com.synersence.hospital.controller;

import com.synersence.hospital.entity.KycRecord;
import com.synersence.hospital.repository.KycRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;

@RestController
@RequestMapping("/api/kyc")
public class KycController {

    @Autowired
    private KycRepository kycRepository;

    @Value("${kyc.storage.path}")
    private String basePath;

    @PostMapping("/save")
    public String saveKyc(@RequestBody KycRecord record) throws Exception {

        // ===== DEBUG LOGS =====
        System.out.println("=== BASE PATH === " + basePath);
        System.out.println("=== PATIENT ID === " + record.getPatientId());

        if (basePath == null || basePath.isEmpty()) {
            return "ERROR: kyc.storage.path is not configured";
        }

        // ===== CREATE PATIENT FOLDER =====
        String patientFolder = basePath + "/patient_" + record.getPatientId();
        Files.createDirectories(Paths.get(patientFolder));

        // ===== IMAGE =====
        String imageFile = patientFolder + "/image.png";
        saveBase64(record.getImageBase64(), imageFile);

        // ===== VIDEO =====
        String videoFile = patientFolder + "/video.webm";
        saveBase64(record.getVideoBase64(), videoFile);

        // ===== SAVE ONLY PATHS TO MONGODB =====
        record.setImagePath(imageFile);
        record.setVideoPath(videoFile);
        record.setImageBase64(null);
        record.setVideoBase64(null);
        record.setCreatedAt(LocalDateTime.now());

        kycRepository.save(record);

        return "KYC Saved Successfully";
    }

    private void saveBase64(String base64, String path) throws Exception {
        if (base64 == null || base64.isEmpty()) return;
        byte[] data = Base64.getDecoder().decode(base64.split(",")[1]);
        Files.write(Paths.get(path), data);
    }
}

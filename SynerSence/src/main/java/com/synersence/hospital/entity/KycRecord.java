package com.synersence.hospital.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "kyc_records")
public class KycRecord {

    @Id
    private String id;

    private String patientId;

    // TEMP (from frontend)
    private String imageBase64;
    private String videoBase64;

    // PERMANENT (saved paths)
    private String imagePath;
    private String videoPath;

    private LocalDateTime createdAt;

    // ===== getters & setters =====

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }

    public String getVideoBase64() { return videoBase64; }
    public void setVideoBase64(String videoBase64) { this.videoBase64 = videoBase64; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getVideoPath() { return videoPath; }
    public void setVideoPath(String videoPath) { this.videoPath = videoPath; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

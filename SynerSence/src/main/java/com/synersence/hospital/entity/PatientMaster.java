package com.synersence.hospital.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "patient_master")
public class PatientMaster {

    @Id
    @Column(name = "PATIENT_ID")
    private String patientId;  // e.g., P001, P002

    @Column(name = "PATIENT_NAME")
    private String patientName;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "VILLAGE")
    private String village;

    // ===== Getters & Setters =====
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }
}

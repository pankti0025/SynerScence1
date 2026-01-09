package com.synersence.hospital.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "patient_custom_field_value")
public class PatientCustomFieldValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private FieldCustomization field;

    @Column(name = "field_value")
    private String fieldValue;

    // ===== GETTERS & SETTERS =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public FieldCustomization getField() { return field; }
    public void setField(FieldCustomization field) { this.field = field; }

    public String getFieldValue() { return fieldValue; }
    public void setFieldValue(String fieldValue) { this.fieldValue = fieldValue; }
}

package com.synersence.hospital.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "field_customization")
public class FieldCustomization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_type", nullable = false)
    private String dataType;

    @Column(name = "label_name", nullable = false)
    private String labelName;

    @Column(name = "mandatory")
    private boolean mandatory;

    @Column(name = "portal")
    private boolean portal;

    // 🔥 REQUIRED FOR DROPDOWN / MULTI-SELECT
    @Column(name = "options")
    private String options;

    // ===== GETTERS & SETTERS =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDataType() { return dataType; }
    public void setDataType(String dataType) { this.dataType = dataType; }

    public String getLabelName() { return labelName; }
    public void setLabelName(String labelName) { this.labelName = labelName; }

    public boolean isMandatory() { return mandatory; }
    public void setMandatory(boolean mandatory) { this.mandatory = mandatory; }

    public boolean isPortal() { return portal; }
    public void setPortal(boolean portal) { this.portal = portal; }

    public String getOptions() { return options; }
    public void setOptions(String options) { this.options = options; }
}

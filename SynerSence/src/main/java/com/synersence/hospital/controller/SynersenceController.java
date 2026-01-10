package com.synersence.hospital.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.synersence.hospital.entity.*;
import com.synersence.hospital.repository.PatientCustomFieldValueRepository;
import com.synersence.hospital.service.FieldCustomizationService;
import com.synersence.hospital.service.PatientMasterService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SynersenceController {

    private final PatientMasterService patientService;
    private final FieldCustomizationService fieldService;
    private final PatientCustomFieldValueRepository customValueRepo;

    public SynersenceController(
            PatientMasterService patientService,
            FieldCustomizationService fieldService,
            PatientCustomFieldValueRepository customValueRepo) {

        this.patientService = patientService;
        this.fieldService = fieldService;
        this.customValueRepo = customValueRepo;
    }

    // ================= DASHBOARD =================
    @GetMapping("/")
    public String home(Model model) {

        List<PatientMaster> patients = patientService.getAllPatients();
        List<FieldCustomization> fields = fieldService.getAllFields();

        // patientId -> (fieldId -> value)
        Map<String, Map<Long, String>> customValues = new HashMap<>();

        for (PatientMaster patient : patients) {

            Map<Long, String> fieldValueMap = new HashMap<>();

            List<PatientCustomFieldValue> values =
                    customValueRepo.findByPatientId(patient.getPatientId());

            for (PatientCustomFieldValue v : values) {
                fieldValueMap.put(v.getField().getId(), v.getFieldValue());
            }

            customValues.put(patient.getPatientId(), fieldValueMap);
        }

        model.addAttribute("patients", patients);
        model.addAttribute("customFields", fields);
        model.addAttribute("customValues", customValues);

        return "index";
    }

    // ================= ADD PATIENT =================
   @GetMapping("/patients/new")
public String addPatientPage(Model model) {

    PatientMaster patient = new PatientMaster();

    // 🔥 AUTO-GENERATE PATIENT ID
    String newPatientId = generatePatientId();
    patient.setPatientId(newPatientId);

    model.addAttribute("patient", patient);
    model.addAttribute("customFields", fieldService.getAllFields());

    return "add-patient";
}
private String generatePatientId() {
    long count = patientMasterRepository.count() + 1;
    return String.format("P%04d", count);
}

    // ================= SAVE PATIENT =================
   @PostMapping("/patients/save")
public String savePatient(
        @ModelAttribute PatientMaster patient,
        HttpServletRequest request) {

    // 🔥 FORCE READ AGE & WEIGHT FROM REQUEST
    String ageParam = request.getParameter("age");
    String weightParam = request.getParameter("weight");

    if (ageParam != null && !ageParam.isEmpty()) {
        patient.setAge(Integer.parseInt(ageParam));
    }

    if (weightParam != null && !weightParam.isEmpty()) {
        patient.setWeight(Double.parseDouble(weightParam));
    }

    // ✅ NOW SAVE PATIENT (age & weight are set)
    patientService.savePatient(patient);

    // ===== SAVE CUSTOM FIELDS =====
    List<FieldCustomization> fields = fieldService.getAllFields();

    for (FieldCustomization field : fields) {
        String value = request.getParameter("custom_" + field.getId());

        if (value != null && !value.trim().isEmpty()) {
            PatientCustomFieldValue v = new PatientCustomFieldValue();
            v.setPatientId(patient.getPatientId());
            v.setField(field);
            v.setFieldValue(value);
            customValueRepo.save(v);
        }
    }

    return "redirect:/kyc-camera?patientId=" + patient.getPatientId();
}

    @GetMapping("/kyc-camera")
    public String kycCamera(@RequestParam String patientId, Model model) {

        // 1️⃣ Fetch patient master (existing logic)
        PatientMaster patient = patientService.getPatientById(patientId);
        model.addAttribute("patient", patient);

        // 2️⃣ Fetch dynamic custom field values (NEW – SAFE ADD)
        List<PatientCustomFieldValue> values =
                customValueRepo.findByPatientId(patientId);

        // 3️⃣ Convert to label → value map (for UI)
        Map<String, String> dynamicFields = new LinkedHashMap<>();

        for (PatientCustomFieldValue v : values) {
            String label = v.getField().getLabelName(); // Doctor Name
            String value = v.getFieldValue();           // Dr. Jimit
            dynamicFields.put(label, value);
        }

        // 4️⃣ Send to UI
        model.addAttribute("dynamicFields", dynamicFields);
        model.addAttribute("patientId", patientId);

        return "kyc-camera";
    }


    // ================= SETTINGS =================
    @GetMapping("/settings")
    public String settings(Model model) {
        model.addAttribute("fieldCustomization", new FieldCustomization());
        return "setting";
    }

    @PostMapping("/custom-field/save")
    public String saveCustomField(@ModelAttribute FieldCustomization fieldCustomization) {
        fieldService.save(fieldCustomization);
        return "redirect:/settings/customize";
    }

    @GetMapping("/settings/customize")
    public String customize(Model model) {
        model.addAttribute("fields", fieldService.getAllFields());
        return "field-customize";
    }
    
    @GetMapping("/prescription")
    public String prescription(
            @RequestParam("patientId") String patientId,
            Model model) {

        System.out.println("=== PRESCRIPTION OPENED ===");
        System.out.println("Patient ID = " + patientId);

        // 1️⃣ FETCH PATIENT
        PatientMaster patient = patientService.getPatientById(patientId);

        if (patient == null) {
            System.out.println("❌ Patient NOT FOUND");
        } else {
            System.out.println("✅ Patient FOUND: " + patient.getPatientName());

            model.addAttribute("patientName", patient.getPatientName());
            model.addAttribute("gender", patient.getGender());

            // ✅ Age & Weight FROM PatientMaster
            model.addAttribute("age", patient.getAge());
            model.addAttribute("weight", patient.getWeight());
            System.out.println(">>> AGE FROM DB = " + patient.getAge());
            System.out.println(">>> WEIGHT FROM DB = " + patient.getWeight());

        }

        // 2️⃣ FETCH CUSTOM FIELD VALUES (ONLY NON-CORE FIELDS)
        List<PatientCustomFieldValue> values =
                customValueRepo.findByPatientId(patientId);

        System.out.println("Custom values count = " + values.size());

        for (PatientCustomFieldValue v : values) {

            String label = v.getField().getLabelName();
            String value = v.getFieldValue();

            System.out.println("Field: " + label + " = " + value);

            // ❌ REMOVE AGE FROM CUSTOM FIELDS (VERY IMPORTANT)
            if (label.equalsIgnoreCase("Case No")) {
                model.addAttribute("caseNo", value);
            }
        }

        // 3️⃣ DATE
        model.addAttribute("date", java.time.LocalDate.now().toString());

        return "prescription";
    }

}



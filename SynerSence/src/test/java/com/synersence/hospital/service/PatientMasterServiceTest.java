package com.synersence.hospital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.synersence.hospital.entity.PatientMaster;
import com.synersence.hospital.repository.PatientMasterRepository;

@ExtendWith(MockitoExtension.class)
class PatientMasterServiceTest {

    @Mock
    private PatientMasterRepository patientMasterRepository;

    @InjectMocks
    private PatientMasterService patientMasterService;

    @Test
    void testGetAllPatients() {

        PatientMaster patient = new PatientMaster();
        patient.setPatientId("P001");          // ✅ FIXED
        patient.setPatientName("Test Patient");

        when(patientMasterRepository.findAll())
             .thenReturn(List.of(patient));

        List<PatientMaster> result =
                patientMasterService.getAllPatients();

        assertEquals(1, result.size());
        assertEquals("Test Patient",
                result.get(0).getPatientName());
    }
}

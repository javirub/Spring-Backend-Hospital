package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.PatientDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors.PatientsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/doctors/patients")
public class PatientsController {
    private final PatientsService patientsService;

    public PatientsController(PatientsService patientsService) {
        this.patientsService = patientsService;
    }

    @GetMapping("/list")
    public Page<PatientDTO> listMyPatients(Principal principal, Pageable pageable) {
        String doctorsUsername = principal.getName();
        Page<Patient> patients = patientsService.listMyPatients(doctorsUsername, pageable);
        return patients.map(PatientDTO::new);
    }

    @GetMapping("/list/{patientId}")
    public PatientDTO getPatientDetails(@PathVariable Long patientId, Principal principal) {
        String doctorsUsername = principal.getName();
        return new PatientDTO(patientsService.getPatientDetails(patientId, doctorsUsername));
    }
}

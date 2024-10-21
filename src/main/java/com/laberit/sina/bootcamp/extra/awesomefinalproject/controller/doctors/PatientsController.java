package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors.PatientsService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/doctors/patients")
public class PatientsController {
    private final PatientsService patientsService;

    public PatientsController(PatientsService patientsService) {
        this.patientsService = patientsService;
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<?> listMyPatients(Principal principal, Pageable pageable) {
        String doctorsUsername = principal.getName();
        return patientsService.listMyPatients(doctorsUsername, pageable);
    }

    @GetMapping("/list/{patientId}")
    @ResponseBody
    public ResponseEntity<?> getPatientDetails(@PathVariable Long patientId, Principal principal) {
        String doctorsUsername = principal.getName();
        return patientsService.getPatientDetails(patientId, doctorsUsername);
    }
}

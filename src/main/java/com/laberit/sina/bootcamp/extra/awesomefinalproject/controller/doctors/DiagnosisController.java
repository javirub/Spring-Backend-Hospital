package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.CreateDiagnosisDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.DiagnosisStatus;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors.DiagnosisService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/doctors/diagnosis")
public class DiagnosisController {
    private final DiagnosisService diagnosisService;

    public DiagnosisController(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> createDiagnosis(@RequestBody @Valid CreateDiagnosisDTO createDiagnosisDTO, Principal principal) {
        String doctorsUsername = principal.getName();
        return diagnosisService.createDiagnosis(createDiagnosisDTO, doctorsUsername);
    }

    @GetMapping("/list/{patientId}")
    @ResponseBody
    public ResponseEntity<?> listDiagnosis(@PathVariable Long patientId, Principal principal, Pageable pageable) {
        String doctorsUsername = principal.getName();
        return diagnosisService.listDiagnosis(patientId, doctorsUsername, pageable);
    }

    @PutMapping("/update/{diagnosisId}")
    @ResponseBody
    public ResponseEntity<?> updateDiagnosis(@PathVariable Long diagnosisId, @RequestParam DiagnosisStatus diagnosisStatus, Principal principal) {
        String doctorsUsername = principal.getName();
        return diagnosisService.updateDiagnosisStatus(diagnosisId, diagnosisStatus, doctorsUsername);
    }
}

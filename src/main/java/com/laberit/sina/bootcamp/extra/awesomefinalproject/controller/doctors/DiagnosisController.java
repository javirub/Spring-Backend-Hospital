package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Diagnosis;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.CreateDiagnosisDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.DiagnosisStatus;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors.DiagnosisService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/doctors/diagnosis")
public class DiagnosisController {
    private final DiagnosisService diagnosisService;

    public DiagnosisController(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }

    @PostMapping("/create")
    public Diagnosis createDiagnosis(@RequestBody @Valid CreateDiagnosisDTO createDiagnosisDTO, Principal principal) {
        String doctorsUsername = principal.getName();
        return diagnosisService.createDiagnosis(createDiagnosisDTO, doctorsUsername);
    }

    @GetMapping("/list")
    public List<Diagnosis> listDiagnosis(Principal principal, Pageable pageable,
                                         @RequestParam(required = false) Long patientId,
                                         @RequestParam(required = false) String disease,
                                         @RequestParam(required = false) String status) {
        String doctorsUsername = principal.getName();
        return diagnosisService.listDiagnosis(patientId, disease, status, doctorsUsername, pageable).getContent();
    }

    @PutMapping("/update/{diagnosisId}")
    public Diagnosis updateDiagnosis(@PathVariable Long diagnosisId,
                                     @RequestParam DiagnosisStatus diagnosisStatus, Principal principal) {
        String doctorsUsername = principal.getName();
        return diagnosisService.updateDiagnosisStatus(diagnosisId, diagnosisStatus, doctorsUsername);
    }
}

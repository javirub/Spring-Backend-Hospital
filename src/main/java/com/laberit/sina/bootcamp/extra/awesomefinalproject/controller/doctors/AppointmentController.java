package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.CreateAppointmentDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/doctors/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> createAppointment(@RequestBody @Valid CreateAppointmentDTO createAppointmentDTO) {
        return appointmentService.createAppointment(createAppointmentDTO);
    }

    @GetMapping("/list/{patientId}")
    @ResponseBody
    public ResponseEntity<?> listPatientAppointments(@PathVariable Long patientId, Principal principal, Pageable pageable) {
        String username = principal.getName();
        return appointmentService.listPatientAppointments(patientId, username, pageable);
    }

    @PutMapping("/confirm/{appointmentId}")
    @ResponseBody
    public ResponseEntity<?> confirmAppointment(@PathVariable Long appointmentId) {
        return appointmentService.confirmAppointment(appointmentId);
    }
}
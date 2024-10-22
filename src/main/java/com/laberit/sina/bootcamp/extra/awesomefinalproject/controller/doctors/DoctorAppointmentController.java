package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Appointment;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.AppointmentDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.CreateAppointmentDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/doctors/appointment")
public class DoctorAppointmentController {
    private final AppointmentService appointmentService;

    public DoctorAppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/create")
    @ResponseBody
    public AppointmentDTO createAppointment(@RequestBody @Valid CreateAppointmentDTO createAppointmentDTO,
                                            Principal principal) {
        String doctorsUsername = principal.getName();
        return new AppointmentDTO(appointmentService.createAppointment(createAppointmentDTO, doctorsUsername));
    }

    @GetMapping("/list/{patientId}")
    @ResponseBody
    public Page<AppointmentDTO> listPatientAppointments(@PathVariable Long patientId, Principal principal, Pageable pageable) {
        String doctorsUsername = principal.getName();
        Page<Appointment> appointments = appointmentService.listPatientAppointments(patientId, doctorsUsername, pageable);
        return appointments.map(AppointmentDTO::new);
    }

    @PutMapping("/confirm/{appointmentId}")
    @ResponseBody
    public AppointmentDTO confirmAppointment(@PathVariable Long appointmentId) {
        return new AppointmentDTO(appointmentService.confirmAppointment(appointmentId));
    }
}
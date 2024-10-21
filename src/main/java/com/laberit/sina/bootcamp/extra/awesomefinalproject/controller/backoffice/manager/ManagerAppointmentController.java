package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.backoffice.manager;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager.ManagerAppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/backoffice/manager")
public class ManagerAppointmentController {
    private final ManagerAppointmentService managerAppointmentService;

    public ManagerAppointmentController(ManagerAppointmentService managerAppointmentService) {
        this.managerAppointmentService = managerAppointmentService;
    }

    @GetMapping("/appointments_by_status")
    public ResponseEntity<?> appointmentsByStatus() {
        return managerAppointmentService.appointmentsByStatus();
    }

    @GetMapping("/cancelled_appointments_by_age")
    public ResponseEntity<?> cancelledAppointmentsByAge() {
        return managerAppointmentService.cancelledAppointmentsByAge();
    }

    @GetMapping("/cancelled_appointments_by_doctor")
    public ResponseEntity<?> cancelledAppointmentsByDoctor() {
        return managerAppointmentService.cancelledAppointmentsByDoctor();
    }
}

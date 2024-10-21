package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.backoffice.manager;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/backoffice/manager")
public class ManagerAppointmentController {

    @GetMapping("/appointments_by_status")
    public ResponseEntity<?> appointmentsByStatus() {
        return null;
    }

    @GetMapping("/cancelled_appointments_by_age")
    public ResponseEntity<?> cancelledAppointmentsByAge() {
        return null;
    }

    @GetMapping("/cancelled_appointments_by_doctor")
    public ResponseEntity<?> cancelledAppointmentsByDoctor() {
        return null;
    }
}

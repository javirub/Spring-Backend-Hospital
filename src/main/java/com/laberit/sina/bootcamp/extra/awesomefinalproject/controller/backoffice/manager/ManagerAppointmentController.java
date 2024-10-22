package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.backoffice.manager;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager.ManagerAppointmentService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Controller
@RequestMapping("/backoffice/manager")
public class ManagerAppointmentController {
    private final ManagerAppointmentService managerAppointmentService;

    public ManagerAppointmentController(ManagerAppointmentService managerAppointmentService) {
        this.managerAppointmentService = managerAppointmentService;
    }

    @GetMapping("/appointments_by_status")
    public Map<String, Long> appointmentsByStatus() {
        return managerAppointmentService.appointmentsByStatus();
    }

    @GetMapping("/cancelled_appointments_by_age")
    public Map<Integer, Long> cancelledAppointmentsByAge() {
        return managerAppointmentService.cancelledAppointmentsByAge();
    }

    @GetMapping("/cancelled_appointments_by_doctor")
    public PageImpl<Map<String, Object>> cancelledAppointmentsByDoctor(Pageable pageable) {
        return managerAppointmentService.cancelledAppointmentsByDoctor(pageable);
    }
}

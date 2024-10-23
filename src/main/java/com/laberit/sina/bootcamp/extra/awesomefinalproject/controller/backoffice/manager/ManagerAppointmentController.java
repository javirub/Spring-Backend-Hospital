package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.backoffice.manager;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.projections.AppointmentStatusCount;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.projections.CancelledAppointmentsByAge;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.projections.CancelledAppointmentsByDoctor;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager.ManagerAppointmentService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/backoffice/manager")
public class ManagerAppointmentController {
    private final ManagerAppointmentService managerAppointmentService;

    public ManagerAppointmentController(ManagerAppointmentService managerAppointmentService) {
        this.managerAppointmentService = managerAppointmentService;
    }

    @GetMapping("/appointments_by_status")
    public List<AppointmentStatusCount> appointmentsByStatus() {
        return managerAppointmentService.appointmentsByStatus();
    }

    @GetMapping("/cancelled_appointments_by_age")
    public List<CancelledAppointmentsByAge> cancelledAppointmentsByAge(Pageable pageable) {
        return managerAppointmentService.cancelledAppointmentsByAge(pageable).getContent();
    }

    @GetMapping("/cancelled_appointments_by_doctor")
    public List<CancelledAppointmentsByDoctor> cancelledAppointmentsByDoctor(Pageable pageable) {
        return managerAppointmentService.cancelledAppointmentsByDoctor(pageable).getContent();
    }
}

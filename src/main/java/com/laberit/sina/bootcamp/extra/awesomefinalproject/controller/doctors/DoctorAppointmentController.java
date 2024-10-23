package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Appointment;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.AppointmentDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.CreateAppointmentDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/doctors/appointment")
public class DoctorAppointmentController {
    private final AppointmentService appointmentService;

    public DoctorAppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/create")
    public AppointmentDTO createAppointment(@RequestBody @Valid CreateAppointmentDTO createAppointmentDTO,
                                            Principal principal) {
        String doctorsUsername = principal.getName();
        return new AppointmentDTO(appointmentService.createAppointment(createAppointmentDTO, doctorsUsername));
    }

    /**
     * List appointments,
     *
     * @param principal  - principal to obtain the username
     * @param pageable   - pageable object
     * @param patientId  - If specified, filter by patient id
     * @param status     - If specified, filter by status
     * @param beforeDate - If specified, filter up to this Date
     * @param afterDate  - If specified, filter from this Date
     * @param doctorId   - If specified, filter the Patient's doctor
     * @param forceAll   - If true, ignore the doctorId and list all appointments
     * @return - A list of appointments filtered by the parameters
     */
    @GetMapping("/list")
    public Page<AppointmentDTO> listPatientAppointments(Principal principal, Pageable pageable,
                                                        @RequestParam(required = false) Long patientId,
                                                        @RequestParam(required = false) String status,
                                                        @RequestParam(required = false) String beforeDate,
                                                        @RequestParam(required = false) String afterDate,
                                                        @RequestParam(required = false) Long doctorId,
                                                        @RequestParam(defaultValue = "false") boolean forceAll) {
        String doctorsUsername = principal.getName();
        Page<Appointment> appointments = appointmentService.listPatientAppointments(patientId, status, beforeDate,
                afterDate, doctorId, forceAll, doctorsUsername, pageable);
        return appointments.map(AppointmentDTO::new);
    }

    @PutMapping("/confirm/{appointmentId}")
    public AppointmentDTO confirmAppointment(@PathVariable Long appointmentId) {
        return new AppointmentDTO(appointmentService.confirmAppointment(appointmentId));
    }
}
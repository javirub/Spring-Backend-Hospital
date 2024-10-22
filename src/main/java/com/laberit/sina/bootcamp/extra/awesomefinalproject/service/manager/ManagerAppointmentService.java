package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ManagerAppointmentService {
    ResponseEntity<?> appointmentsByStatus();

    ResponseEntity<?> cancelledAppointmentsByAge();

    ResponseEntity<?> cancelledAppointmentsByDoctor(Pageable pageable);
}

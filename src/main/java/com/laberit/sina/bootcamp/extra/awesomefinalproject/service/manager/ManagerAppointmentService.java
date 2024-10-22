package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ManagerAppointmentService {
    Map<String, Long> appointmentsByStatus();

    Map<Integer, Long> cancelledAppointmentsByAge();

    PageImpl<Map<String, Object>> cancelledAppointmentsByDoctor(Pageable pageable);
}

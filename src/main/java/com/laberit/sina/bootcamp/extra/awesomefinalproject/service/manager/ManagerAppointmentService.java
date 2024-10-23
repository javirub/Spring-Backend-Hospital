package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.projections.AppointmentStatusCount;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.projections.CancelledAppointmentsByAge;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.projections.CancelledAppointmentsByDoctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ManagerAppointmentService {
    List<AppointmentStatusCount> appointmentsByStatus();

    Page<CancelledAppointmentsByAge> cancelledAppointmentsByAge(Pageable pageable);

    Page<CancelledAppointmentsByDoctor> cancelledAppointmentsByDoctor(Pageable pageable);
}

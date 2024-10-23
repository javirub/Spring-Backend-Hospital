package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl.manager;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.exception.NoContentException;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.projections.AppointmentStatusCount;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.projections.CancelledAppointmentsByAge;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.projections.CancelledAppointmentsByDoctor;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.AppointmentRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager.ManagerAppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.PermissionUtils.checkPermissions;

@Service
public class ManagerAppointmentServiceImpl implements ManagerAppointmentService {
    private final AppointmentRepository appointmentRepository;

    public ManagerAppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    @Transactional
    public List<AppointmentStatusCount> appointmentsByStatus() {
        checkPermissions("WATCH_APPOINTMENT_STATISTICS");

        List<AppointmentStatusCount> statusCount = appointmentRepository.countAppointmentsByStatus();

        if (statusCount.isEmpty()) {
            throw new NoContentException("No appointments found");
        }

        return statusCount;
    }

    @Override
    @Transactional
    public Page<CancelledAppointmentsByAge> cancelledAppointmentsByAge(Pageable pageable) {
        checkPermissions("WATCH_APPOINTMENT_STATISTICS");

        Page<CancelledAppointmentsByAge> results = appointmentRepository.countCancelledAppointmentsByAge(pageable);

        if (results.isEmpty()) {
            throw new NoContentException("No cancelled appointments found");
        }

        return results;
    }


    @Override
    @Transactional
    public Page<CancelledAppointmentsByDoctor> cancelledAppointmentsByDoctor(Pageable pageable) {
        checkPermissions("WATCH_APPOINTMENT_STATISTICS");

        Page<CancelledAppointmentsByDoctor> results = appointmentRepository.countCancelledAppointmentsByDoctor(pageable);

        if (results.isEmpty()) {
            throw new NoContentException("No cancelled appointments found");
        }


        return results;
    }
}

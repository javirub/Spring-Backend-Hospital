package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl.manager;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.AppointmentStatus;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.AppointmentRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager.ManagerAppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.PermissionUtils.checkPermissions;

@Service
public class ManagerAppointmentServiceImpl implements ManagerAppointmentService {
    private final AppointmentRepository appointmentRepository;

    public ManagerAppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<?> appointmentsByStatus() {
        ResponseEntity<?> hasPermission = checkPermissions("WATCH_APPOINTMENT_STATISTICS");
        if (hasPermission != null) return hasPermission;


        long cancelledCount = appointmentRepository.countByStatusIs(AppointmentStatus.CANCELLED);
        long pendingCount = appointmentRepository.countByStatusIs(AppointmentStatus.PENDING);
        long confirmedCount = appointmentRepository.countByStatusIs(AppointmentStatus.CONFIRMED);

        return ResponseEntity.ok().body(Map.of("cancelled", cancelledCount,
                "pending", pendingCount, "confirmed", confirmedCount));
    }

    @Override
    @Transactional
    public ResponseEntity<?> cancelledAppointmentsByAge() {
        ResponseEntity<?> hasPermission = checkPermissions("WATCH_APPOINTMENT_STATISTICS");
        if (hasPermission != null) return hasPermission;

        List<Object[]> results = appointmentRepository.countCancelledAppointmentsByAge();
        System.out.println(results);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Map<Integer, Long> ageCounts = results.stream()
                .collect(Collectors.toMap(
                        result -> ((BigDecimal) result[0]).intValue(),
                        result -> (Long) result[1]
                ));

        return ResponseEntity.ok().body(ageCounts);
    }


    @Override
    @Transactional
    public ResponseEntity<?> cancelledAppointmentsByDoctor() {
        ResponseEntity<?> hasPermission = checkPermissions("WATCH_APPOINTMENT_STATISTICS");
        if (hasPermission != null) return hasPermission;

        // TODO: Does an appointment have a doctor?

        return null;
    }
}

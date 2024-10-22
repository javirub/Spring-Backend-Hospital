package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl.manager;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.AppointmentStatus;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.AppointmentRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager.ManagerAppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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


        List<Object[]> statusCounts = appointmentRepository.countAppointmentsByStatus();
        Map<String, Long> statusCountMap = statusCounts.stream()
                .collect(Collectors.toMap(
                        result -> ((AppointmentStatus) result[0]).name(),
                        result -> (Long) result[1]
                ));
        long totalCount = statusCountMap.values().stream().mapToLong(Long::longValue).sum();

        return ResponseEntity.ok().body(Map.of(
                "cancelled", statusCountMap.getOrDefault(AppointmentStatus.CANCELLED.name(), 0L),
                "pending", statusCountMap.getOrDefault(AppointmentStatus.PENDING.name(), 0L),
                "confirmed", statusCountMap.getOrDefault(AppointmentStatus.CONFIRMED.name(), 0L),
                "done", statusCountMap.getOrDefault(AppointmentStatus.DONE.name(), 0L),
                "total", totalCount));
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
    public ResponseEntity<?> cancelledAppointmentsByDoctor(Pageable pageable) {
        ResponseEntity<?> hasPermission = checkPermissions("WATCH_APPOINTMENT_STATISTICS");
        if (hasPermission != null) return hasPermission;

        Page<Object[]> results = appointmentRepository.countCancelledAppointmentsByDoctor(pageable);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Map<String, Object>> content = results.stream().map(result -> Map.of(
                "doctorId", result[0],
                "name", result[1],
                "surnames", result[2],
                "cancelledCount", result[3]
        )).collect(Collectors.toList());

        return ResponseEntity.ok(new PageImpl<>(content, pageable, results.getTotalElements()));
    }
}

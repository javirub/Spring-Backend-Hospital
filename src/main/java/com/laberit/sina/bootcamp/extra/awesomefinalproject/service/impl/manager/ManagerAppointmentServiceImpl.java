package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl.manager;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.exception.NoContentException;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.AppointmentStatus;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.AppointmentRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager.ManagerAppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Map<String, Long> appointmentsByStatus() {
        checkPermissions("WATCH_APPOINTMENT_STATISTICS");

        Map<String, Long> statusCountMap = appointmentRepository.countAppointmentsByStatus().stream()
                .collect(Collectors.toMap(
                        result -> ((AppointmentStatus) result[0]).name(),
                        result -> (Long) result[1]
                ));
        long totalCount = statusCountMap.values().stream().mapToLong(Long::longValue).sum();

        statusCountMap.put("total", totalCount);
        return statusCountMap;
    }

    @Override
    @Transactional
    public Map<Integer, Long> cancelledAppointmentsByAge() {
        checkPermissions("WATCH_APPOINTMENT_STATISTICS");

        List<Object[]> results = appointmentRepository.countCancelledAppointmentsByAge();

        if (results.isEmpty()) {
            throw new NoContentException("No cancelled appointments found");
        }

        return results.stream()
                .collect(Collectors.toMap(
                        result -> ((BigDecimal) result[0]).intValue(),
                        result -> (Long) result[1]
                ));
    }


    @Override
    @Transactional
    public PageImpl<Map<String, Object>> cancelledAppointmentsByDoctor(Pageable pageable) {
        checkPermissions("WATCH_APPOINTMENT_STATISTICS");

        Page<Object[]> results = appointmentRepository.countCancelledAppointmentsByDoctor(pageable);
        if (results.isEmpty()) {
            throw new NoContentException("No cancelled appointments found");
        }

        List<Map<String, Object>> content = results.stream()
                .map(result -> Map.of(
                        "doctorId", result[0],
                        "completeName", result[1] + " " + result[2],
                        "cancelledCount", result[3]
                )).collect(Collectors.toList());

        return new PageImpl<>(content, pageable, results.getTotalElements());
    }
}

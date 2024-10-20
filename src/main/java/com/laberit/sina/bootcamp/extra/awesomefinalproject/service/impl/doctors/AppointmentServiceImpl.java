package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Appointment;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.CreateAppointmentDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.AppointmentStatus;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.AppointmentRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.PatientRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.laberit.sina.bootcamp.extra.awesomefinalproject.service.utils.PermissionUtils.checkPermissions;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<?> createAppointment(CreateAppointmentDTO createAppointmentDTO) {
        if (createAppointmentDTO == null) {
            return ResponseEntity.badRequest().body("You must provide a valid appointment");
        }

        if (patientRepository.findById(createAppointmentDTO.getPatientId()).isEmpty()) {
            return ResponseEntity.badRequest().body("Patient not found");
        }

        if (createAppointmentDTO.getDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Invalid date");
        }

        ResponseEntity<?> hasPermission = checkPermissions("CREATE_APPOINTMENT");
        if (hasPermission != null) {
            return hasPermission;
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patientRepository.findById(createAppointmentDTO.getPatientId()).get());
        appointment.setDate(createAppointmentDTO.getDate());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointmentRepository.save(appointment);
        return ResponseEntity.ok(appointment);
    }

    @Override
    @Transactional
    public ResponseEntity<?> confirmAppointment(Long appointmentId) {
        ResponseEntity<?> hasPermission = checkPermissions("CONFIRM_APPOINTMENT");
        if (hasPermission != null) {
            return hasPermission;
        }

        if (appointmentRepository.findById(appointmentId).isEmpty()) {
            return ResponseEntity.badRequest().body("Appointment not found");
        }

        // TODO: Check if the appointment is from the doctor
        Appointment appointment = appointmentRepository.findById(appointmentId).get();
        appointment.setStatus(AppointmentStatus.DONE); // TODO: ¿Change to CONFIRMED?
        appointmentRepository.save(appointment);
        return ResponseEntity.ok(appointment);
    }

    @Override
    @Transactional
    public ResponseEntity<?> listPatientAppointments(Long patientId, String username) {
        ResponseEntity<?> hasPermission = checkPermissions("WATCH_PATIENT_APPOINTMENTS");
        if (hasPermission != null) {
            return hasPermission;
        }

        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            return ResponseEntity.badRequest().body("Patient not found");
        }

        boolean isDoctorOfPatient = patient.getDoctors().stream()
                .anyMatch(doctor -> doctor.getUsername().equals(username));

        if (!isDoctorOfPatient) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authorized to view this patient's appointments");
        }

        List<Appointment> appointments = appointmentRepository.findAllByPatientId(patientId);
        if (appointments.isEmpty()) {
            return ResponseEntity.ok("No appointments found");
        }
        return ResponseEntity.ok(appointments);
    }
}
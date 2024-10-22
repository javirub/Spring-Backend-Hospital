package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Appointment;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.AppointmentDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.CreateAppointmentDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.AppointmentStatus;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.AppointmentRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.PatientRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UnauthorizedAccessRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UserRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.AppointmentUtils.saveAppointmentAndReturn;
import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.DoctorUtils.checkDoctorOfPatient;
import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.PermissionUtils.checkPermissions;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final UnauthorizedAccessRepository unauthorizedAccessRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PatientRepository patientRepository,
                                  UserRepository userRepository, UnauthorizedAccessRepository unauthorizedAccessRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.unauthorizedAccessRepository = unauthorizedAccessRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<?> createAppointment(CreateAppointmentDTO createAppointmentDTO, String doctorsUsername) {
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

        User doctor = userRepository.findByUsername(doctorsUsername).orElse(null);
        if (doctor == null) {
            return ResponseEntity.badRequest().body("Doctor not found");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patientRepository.findById(createAppointmentDTO.getPatientId()).get());
        appointment.setDate(createAppointmentDTO.getDate());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setDoctor(doctor);
        return saveAppointmentAndReturn(appointmentRepository, appointment);
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

        Appointment appointment = appointmentRepository.findById(appointmentId).get();
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        return saveAppointmentAndReturn(appointmentRepository, appointment);
    }

    @Override
    @Transactional
    public ResponseEntity<?> listPatientAppointments(Long patientId, String doctorsUsername, Pageable pageable) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            return ResponseEntity.badRequest().body("Patient not found");
        }

        ResponseEntity<?> hasPermission = checkPermissions("WATCH_PATIENT_APPOINTMENTS");
        if (hasPermission != null) {
            return hasPermission;
        }

        User doctor = userRepository.findByUsername(doctorsUsername).orElse(null);
        if (doctor == null) {
            return ResponseEntity.badRequest().body("Doctor not found");
        }

        checkDoctorOfPatient(patient, doctor, "Watch Patient Appointments", unauthorizedAccessRepository);
        Page<Appointment> appointments = appointmentRepository.findAllByPatientId(patientId, pageable);
        if (appointments.isEmpty()) {
            return ResponseEntity.ok("No appointments found");
        }

        Page<AppointmentDTO> appointmentDTOS = appointments.map(AppointmentDTO::new);

        return ResponseEntity.ok(appointmentDTOS);
    }
}
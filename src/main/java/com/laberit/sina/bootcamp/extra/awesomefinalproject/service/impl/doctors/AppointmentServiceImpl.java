package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.exception.NoContentException;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Appointment;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.dtos.CreateAppointmentDTO;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.AppointmentStatus;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.AppointmentRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.PatientRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UnauthorizedAccessRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UserRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    public Appointment createAppointment(CreateAppointmentDTO createAppointmentDTO, String doctorsUsername) {
        checkPermissions("CREATE_APPOINTMENT");

        if (createAppointmentDTO.getDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Date cannot be in the past");
        }

        User doctor = userRepository.findByUsername(doctorsUsername).orElseThrow(() ->
                new RuntimeException("Doctor not found"));

        Patient patient = patientRepository.findById(createAppointmentDTO.getPatientId()).orElseThrow(() ->
                new IllegalArgumentException("Patient not found"));

        checkDoctorOfPatient(patient, doctor, "Create Appointment", unauthorizedAccessRepository);

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDate(createAppointmentDTO.getDate());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setDoctor(doctor);

        return appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public Appointment confirmAppointment(Long appointmentId) {
        checkPermissions("CONFIRM_APPOINTMENT");

        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() ->
                new IllegalArgumentException("Appointment not found"));


        checkDoctorOfPatient(appointment.getPatient(), appointment.getDoctor(),
                "Confirm Appointment", unauthorizedAccessRepository);

        appointment.setStatus(AppointmentStatus.CONFIRMED);
        return appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public Page<Appointment> listPatientAppointments(Long patientId, String doctorsUsername, Pageable pageable) {
        checkPermissions("WATCH_PATIENT_APPOINTMENTS");
        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->
                new IllegalArgumentException("Patient not found"));

        User doctor = userRepository.findByUsername(doctorsUsername).orElseThrow(() ->
                new RuntimeException("Doctor not found"));

        checkDoctorOfPatient(patient, doctor, "Watch Patient Appointments", unauthorizedAccessRepository);

        Page<Appointment> appointments = appointmentRepository.findAllByPatientId(patientId, pageable);
        if (appointments.isEmpty()) {
            throw new NoContentException("No appointments found");
        }

        return appointments;
    }
}
package com.laberit.sina.bootcamp.extra.awesomefinalproject.service.impl.doctors;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.UnauthorizedAccess;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.PatientRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UnauthorizedAccessRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.repository.UserRepository;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.doctors.PatientsService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.laberit.sina.bootcamp.extra.awesomefinalproject.utils.PermissionUtils.checkPermissions;

@Service
public class PatientsServiceImpl implements PatientsService {
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final UnauthorizedAccessRepository unauthorizedAccessRepository;

    public PatientsServiceImpl(PatientRepository patientRepository, UserRepository userRepository,
                               UnauthorizedAccessRepository unauthorizedAccessRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.unauthorizedAccessRepository = unauthorizedAccessRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<?> listMyPatients(String doctorsUsername, Pageable pageable) {
        ResponseEntity<?> hasPermissions = checkPermissions("WATCH_PATIENT_INFO");

        if (hasPermissions != null) return hasPermissions;

        User doctor = userRepository.findByUsername(doctorsUsername).orElse(null);

        if (doctor == null) {
            return ResponseEntity.badRequest().body("Doctor not found");
        }

        return ResponseEntity.ok(patientRepository.findByDoctorsContaining(doctor, pageable));
    }

    @Override
    @Transactional
    public ResponseEntity<?> getPatientDetails(Long patientId, String doctorsUsername) {
        ResponseEntity<?> hasPermissions = checkPermissions("WATCH_PATIENT_INFO");

        if (hasPermissions != null) return hasPermissions;

        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            return ResponseEntity.badRequest().body("Patient not found");
        }

        User doctor = userRepository.findByUsername(doctorsUsername).orElse(null);

        if (doctor == null) {
            return ResponseEntity.badRequest().body("Doctor not found");
        }

        if (!patient.getDoctors().contains(doctor)) {
            UnauthorizedAccess unauthorizedAccess = new UnauthorizedAccess();
            unauthorizedAccess.setPatient(patient);
            unauthorizedAccess.setDoctor(doctor);
            unauthorizedAccess.setTimestamp(java.time.LocalDateTime.now());
            unauthorizedAccess.setQuery("Get Patient Details");
            unauthorizedAccessRepository.save(unauthorizedAccess);
        }
        return ResponseEntity.ok(patient);
    }
}

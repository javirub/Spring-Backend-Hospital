package com.laberit.sina.bootcamp.extra.awesomefinalproject.repository;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findByDoctorsContaining(User doctor, Pageable pageable);

    @Query("SELECT AVG(YEAR(CURRENT_DATE) - YEAR(p.birthDate) - " +
            "CASE WHEN MONTH(CURRENT_DATE) < MONTH(p.birthDate) OR " +
            "(MONTH(CURRENT_DATE) = MONTH(p.birthDate) AND DAY(CURRENT_DATE) < DAY(p.birthDate)) " +
            "THEN 1 ELSE 0 END) FROM Patient p")
    double getMeanAge();

    int countPatientByGender(Gender gender);

    long countByDiagnosisNotEmpty();

}
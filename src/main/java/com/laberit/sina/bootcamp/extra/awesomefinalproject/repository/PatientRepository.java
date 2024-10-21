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

    @Query(value = "SELECT AVG(EXTRACT(YEAR FROM AGE(p.birth_date))) FROM patient p", nativeQuery = true)
    double getMeanAge();

    int countPatientByGender(Gender gender);

    long countByDiagnosisNotEmpty();

}
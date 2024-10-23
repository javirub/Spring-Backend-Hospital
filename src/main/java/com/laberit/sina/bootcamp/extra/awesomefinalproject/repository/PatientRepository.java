package com.laberit.sina.bootcamp.extra.awesomefinalproject.repository;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.Patient;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.User;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.projections.GenderCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findByDoctorsContaining(User doctor, Pageable pageable);

    @Query(value = "SELECT AVG(EXTRACT(YEAR FROM AGE(p.birth_date))) FROM patient p", nativeQuery = true)
    double getMeanAge();

    @Query("SELECT p.gender as gender, COUNT(p) as count FROM Patient p GROUP BY p.gender")
    List<GenderCount> countPatientsByGender();

    long countByDiagnosisNotEmpty();

}
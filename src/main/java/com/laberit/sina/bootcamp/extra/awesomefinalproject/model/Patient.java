package com.laberit.sina.bootcamp.extra.awesomefinalproject.model;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surnames;

    private LocalDate birthDate;

    private Gender gender;

    @OneToMany(mappedBy = "patient")
    private List<Diagnosis> diagnosis;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    @ManyToMany
    @JoinTable(
            name = "patient_doctor",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private List<User> doctors;
}
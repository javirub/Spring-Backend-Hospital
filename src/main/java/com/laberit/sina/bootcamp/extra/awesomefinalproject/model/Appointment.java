package com.laberit.sina.bootcamp.extra.awesomefinalproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    private Patient patient;

    @Column(columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
}
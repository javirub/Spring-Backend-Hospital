package com.laberit.sina.bootcamp.extra.awesomefinalproject.model;

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

    @ManyToOne
    private Patient patient;

    @Column(columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
}
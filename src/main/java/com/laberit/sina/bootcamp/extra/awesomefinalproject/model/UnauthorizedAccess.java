package com.laberit.sina.bootcamp.extra.awesomefinalproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class UnauthorizedAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private String doctorUsername;
    private LocalDateTime timestamp;
    private String query; // Nuevo campo para almacenar la consulta
}

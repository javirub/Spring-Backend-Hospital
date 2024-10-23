package com.laberit.sina.bootcamp.extra.awesomefinalproject.model.projections;

public interface CancelledAppointmentsByDoctor {
    Long getDoctorId();
    String getName();
    String getSurnames();
    Long getAppointmentCount();
}

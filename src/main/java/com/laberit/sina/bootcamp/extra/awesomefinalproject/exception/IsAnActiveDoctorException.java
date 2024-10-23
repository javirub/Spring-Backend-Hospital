package com.laberit.sina.bootcamp.extra.awesomefinalproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
public class IsAnActiveDoctorException extends RuntimeException {
    public IsAnActiveDoctorException(String message) {
        super(message);
    }
}

package com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums;

import lombok.Getter;

import java.util.List;

@Getter
public enum RoleName {
    ADMIN(List.of(Permissions.REGISTER_USER, Permissions.UPDATE_USER, Permissions.DELETE_USER, Permissions.WATCH_USERS)),
    DOCTOR(List.of(Permissions.WATCH_PATIENT_INFO, Permissions.CREATE_DIAGNOSIS, Permissions.UPDATE_DIAGNOSIS,
            Permissions.DELETE_DIAGNOSIS, Permissions.WATCH_DIAGNOSIS, Permissions.CREATE_APPOINTMENT,
            Permissions.CONFIRM_APPOINTMENT, Permissions.UPDATE_APPOINTMENT, Permissions.DELETE_APPOINTMENT,
            Permissions.WATCH_PATIENT_APPOINTMENTS)),
    MANAGER(List.of(Permissions.WATCH_PATIENT_STATISTICS, Permissions.WATCH_APPOINTMENT_STATISTICS));

    private final List<Permissions> permissions;

    RoleName(List<Permissions> permissions) {
        this.permissions = permissions;
    }
}
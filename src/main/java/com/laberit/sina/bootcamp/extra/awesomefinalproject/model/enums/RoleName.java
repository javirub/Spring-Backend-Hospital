package com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum RoleName {
    ADMIN(Arrays.asList(Permissions.values())),
    DOCTOR(List.of(Permissions.WRITE_DIAGNOSIS, Permissions.READ_DIAGNOSIS, Permissions.READ_APPOINTMENT,
            Permissions.WRITE_APPOINTMENT, Permissions.READ_PATIENT, Permissions.WRITE_PATIENT)),
    MANAGER(List.of(Permissions.CREATE_DOCTOR, Permissions.CREATE_MANAGER));

    private final List<Permissions> permissions;

    RoleName(List<Permissions> permissions) {
        this.permissions = permissions;
    }
}
package com.laberit.sina.bootcamp.extra.awesomefinalproject.model;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Permissions;
import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.RoleName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName role;

    @ElementCollection(targetClass = Permissions.class)
    @Enumerated(EnumType.STRING)
    private List<Permissions> permissions;

    public Role() {
    }

    public Role(RoleName role) {
        this.role = role;
        this.permissions = role.getPermissions();
    }
}

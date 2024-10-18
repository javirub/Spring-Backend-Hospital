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
    private Short id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private RoleName name;

    @ElementCollection(targetClass = Permissions.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Permissions> permissions;

    public Role() {
    }

    public Role(RoleName name) {
        this.name = name;
        this.permissions = name.getPermissions();
    }
}

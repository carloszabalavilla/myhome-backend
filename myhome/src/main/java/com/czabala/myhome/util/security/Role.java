package com.czabala.myhome.util.security;

import lombok.Getter;

import java.util.List;

@Getter
public enum Role {
    USER(List.of(Permission.ALL_USER)),
    ADMINISTRATOR(List.of(Permission.ALL_PERMISSION));

    private final List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }
}

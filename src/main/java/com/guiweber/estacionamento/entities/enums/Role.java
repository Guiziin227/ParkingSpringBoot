package com.guiweber.estacionamento.entities.enums;

public enum Role {
    ROLE_ADMIN(1),
    ROLE_USER(2),
    ;

    private int value;

    private Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Role valueOf(int value) {
        for (Role role : Role.values()) {
            if (role.value == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role value: " + value);
    }

}

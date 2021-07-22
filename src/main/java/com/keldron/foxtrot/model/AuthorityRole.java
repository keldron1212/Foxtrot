package com.keldron.foxtrot.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

public enum AuthorityRole implements GrantedAuthority {
    USER("ROLE_USER"),
    TRAINER("ROLE_TRAINER"),
    ADMIN("ROLE_ADMIN");

    private String role;

    AuthorityRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }
}

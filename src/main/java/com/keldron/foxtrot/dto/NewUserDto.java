package com.keldron.foxtrot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewUserDto {
    private String username;
    private String password;
    private String name;
    private String surname;
    private Set<String> authorities;

    public NewUserDto(String username, String name, String surname, Set<String> authorities) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }
}

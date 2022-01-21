package com.keldron.foxtrot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String username;
    private String name;
    private String surname;
    private Set<String> authorities;
    private Set<Long> trainings;

    public UserDto() {}
    
    public UserDto(String username, String name, String surname, Set<String> authorities, Set<Long> trainings) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.authorities = authorities;
        this.trainings = trainings;
    }

    @JsonCreator
    public UserDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Set<Long> getTrainings() {
        return trainings;
    }

    public void setTrainings(Set<Long> trainings) {
        this.trainings = trainings;
    }
}

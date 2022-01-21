package com.keldron.foxtrot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserDto {
    private String username;
    private String password;
    private String name;
    private String surname;
    private Set<String> authorities;

	public UpdateUserDto() {}
    
    public UpdateUserDto(String username, String password, String name, String surname, Set<String> authorities) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.authorities = authorities;
    }


    public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setAuthorities(Set<String> authorities) {
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

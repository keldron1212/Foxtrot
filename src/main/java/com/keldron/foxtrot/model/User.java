package com.keldron.foxtrot.model;

import com.keldron.foxtrot.model.trainings.Training;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity(name="user")
@Table(name = "users")
public class User implements UserDetails, CredentialsContainer {
    @Id
    private String username;

    private String password;

    private String name;

    private String surname;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = AuthorityRole.class)
    @Enumerated(EnumType.ORDINAL)
    private Set<AuthorityRole> authorities = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "users_trainings",
            joinColumns = @JoinColumn(name = "user_username"),
            inverseJoinColumns = @JoinColumn(name = "training_id")
    )
    private Set<Training> trainings;

    //Public non-argument contructor required by JPA
    public User() {
    }

    public User(String username, String password, String name, String surname, Set<AuthorityRole> grantedAuthorities) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.authorities = grantedAuthorities;
        this.accountNonExpired = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.accountNonLocked = true;
        this.trainings = new HashSet<>();
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityRole> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public Set<Training> getTrainings() {
        return trainings;
    }

    public void addTraining(Training training) {
        this.trainings.add(training);
        training.getParticipants().add(this);
    }

    public void removeTraining(Training training) {
        this.trainings.remove(training);
        training.getParticipants().remove(this);
    }

    //TODO think if equals should be different
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return this.username.equals(((User) obj).username);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                ", authorities=" + authorities +
                ", trainings=" + trainings +
                '}';
    }
}

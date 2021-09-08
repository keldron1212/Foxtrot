package com.keldron.foxtrot.model.trainings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.keldron.foxtrot.model.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "training")
@Table(name = "trainings")
public class Training {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
    private int maxRegistrations;
    @ManyToOne(fetch = FetchType.EAGER)
    private User trainer;
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("trainings")
    private Set<User> participants;
    private TrainingType trainingType;
    @ManyToOne(fetch = FetchType.EAGER)
    private Venue venue;

    //Empty non-arg contructor required by JPA
    public Training() {
    }

    public Training(String name, LocalDateTime startDate, LocalDateTime endDate, BigDecimal price, int maxRegistrations, User trainer, TrainingType trainingType, Venue venue) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.maxRegistrations = maxRegistrations;
        this.trainer = trainer;
        this.trainingType = trainingType;
        this.venue = venue;
        this.participants = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getMaxRegistrations() {
        return maxRegistrations;
    }

    public void setMaxRegistrations(int maxRegistrations) {
        this.maxRegistrations = maxRegistrations;
    }

    public User getTrainer() {
        return trainer;
    }

    public void setTrainer(User trainer) {
        this.trainer = trainer;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void addParticipant(User participant) {
        this.participants.add(participant);
        participant.getTrainings().add(this);
    }

    public void removeParticipant(User participant) {
        this.participants.remove(participant);
        participant.getTrainings().remove(this);
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", maxRegistrations=" + maxRegistrations +
                ", trainer=" + trainer +
                ", participants=" + participants +
                ", trainingType=" + trainingType +
                ", venue=" + venue +
                '}';
    }
}

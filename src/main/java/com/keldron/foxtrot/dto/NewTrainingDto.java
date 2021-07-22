package com.keldron.foxtrot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewTrainingDto {
        private Long id;
        private String name;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private BigDecimal price;
        private int maxRegistrations;
        private UserDto trainer;
        private String trainingType;
        private Long venueId;

    @JsonCreator
    public NewTrainingDto(Long id, String name, LocalDateTime startDate, LocalDateTime endDate, BigDecimal price, int maxRegistrations, UserDto trainer, String trainingType, Long venueId) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.maxRegistrations = maxRegistrations;
        this.trainer = trainer;
        this.trainingType = trainingType;
        this.venueId = venueId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getMaxRegistrations() {
        return maxRegistrations;
    }

    public UserDto getTrainer() {
        return trainer;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public Long getVenueId() {
        return venueId;
    }
}

package com.keldron.foxtrot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.keldron.foxtrot.model.trainings.TrainingType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainingDto {
    private Long id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
    private int maxRegistrations;
    private UserDto trainer;
    private Set<String> participants;
    private String trainingType;
    private VenueDto venue;
    private String trainingStatus;

    public TrainingDto() {}

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

    public Set<String> getParticipants() {
        return participants;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public VenueDto getVenue() {
        return venue;
    }

    public String getTrainingStatus() {
        return trainingStatus;
    }

    public static class TrainingDtoBuilder {

        private TrainingDto trainingDto;

        public TrainingDtoBuilder() {
            this.trainingDto = new TrainingDto();
        }

        public TrainingDtoBuilder withId(Long id) {
            trainingDto.id = id;
            return this;
        }

        public TrainingDtoBuilder withName(String name) {
            trainingDto.name = name;
            return this;
        }

        public TrainingDtoBuilder withStartDate(LocalDateTime date) {
            trainingDto.startDate = date;
            return this;
        }

        public TrainingDtoBuilder withEndDate(LocalDateTime date) {
            trainingDto.endDate = date;
            return this;
        }

        public TrainingDtoBuilder withMaxRegistrations(int max) {
            trainingDto.maxRegistrations = max;
            return this;
        }

        public TrainingDtoBuilder withPrice(BigDecimal price) {
            trainingDto.price = price;
            return this;
        }

        public TrainingDtoBuilder withTrainingType(TrainingType trainingType) {
            trainingDto.trainingType = trainingType.getName();
            return this;
        }

        public TrainingDtoBuilder withTrainer(UserDto trainer) {
            trainingDto.trainer = trainer;
            return this;
        }

        public TrainingDtoBuilder withParticipants(Set<String> participants) {
            trainingDto.participants = participants;
            return this;
        }

        public TrainingDtoBuilder withVenue(VenueDto venue) {
            trainingDto.venue = venue;
            return this;
        }

        public TrainingDtoBuilder SetStatus(LocalDateTime startDate, LocalDateTime endDate) {
            LocalDateTime now = LocalDateTime.now();
            if (startDate.isBefore(now) && endDate.isAfter(now)) {
                trainingDto.trainingStatus = TrainingStatus.IN_PROGRESS.toString();
            } else if (endDate.isBefore(now)) {
                trainingDto.trainingStatus = TrainingStatus.ENDED.toString();
            } else {
                trainingDto.trainingStatus = TrainingStatus.OPEN.toString();
            }
            return this;
        }

        public TrainingDto build() {
            return trainingDto;
        }
    }
}

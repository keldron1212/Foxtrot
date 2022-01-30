package com.keldron.foxtrot.mappers;

import com.keldron.foxtrot.dto.NewTrainingDto;
import com.keldron.foxtrot.dto.TrainingDto;
import com.keldron.foxtrot.model.User;
import com.keldron.foxtrot.model.trainings.Training;
import com.keldron.foxtrot.model.trainings.TrainingType;
import com.keldron.foxtrot.model.trainings.Venue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TrainingMapper {

    public static TrainingDto toTrainingDto(Training training) {
        List<User> participants = training.getParticipants();

        Set<String> participantsIds = participants.stream().map(User -> {
    		return User.getName() != null ? User.getName() : (User.getUsername());
    	}).collect(Collectors.toUnmodifiableSet());

        TrainingDto.TrainingDtoBuilder builder = new TrainingDto.TrainingDtoBuilder();
        return builder.withId(training.getId()).withName(training.getName()).withEndDate(training.getEndDate())
                .withStartDate(training.getStartDate()).withMaxRegistrations(training.getMaxRegistrations())
                .withPrice(training.getPrice()).withTrainingType(training.getTrainingType()).withVenue(VenueMapper.toDto(training.getVenue()))
                .withTrainer(UserMapper.toDto(training.getTrainer())).withParticipants(participantsIds).SetStatus(training.getStartDate(), training.getEndDate()).build();
    }

    public static TrainingDto toUsersTrainingDto(Training training) {
        TrainingDto.TrainingDtoBuilder builder = new TrainingDto.TrainingDtoBuilder();
        return builder.withId(training.getId()).withName(training.getName()).withEndDate(training.getEndDate())
                .withStartDate(training.getStartDate()).withMaxRegistrations(training.getMaxRegistrations())
                .withPrice(training.getPrice()).withTrainingType(training.getTrainingType()).withVenue(VenueMapper.toDto(training.getVenue()))
                .withTrainer(UserMapper.toDto(training.getTrainer())).build();
    }

    public static Training toTraining(NewTrainingDto dto, User trainer, Venue venue) {
        return new Training(dto.getName(), dto.getStartDate(), dto.getEndDate(), dto.getPrice(),
                dto.getMaxRegistrations(), trainer, TrainingType.valueOf(dto.getTrainingType()), venue);
    }
}

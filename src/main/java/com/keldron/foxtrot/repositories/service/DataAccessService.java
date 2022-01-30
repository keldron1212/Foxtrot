package com.keldron.foxtrot.repositories.service;

import com.keldron.foxtrot.Utils;
import com.keldron.foxtrot.dto.*;
import com.keldron.foxtrot.info.APIInfoCodes;
import com.keldron.foxtrot.info.Info;
import com.keldron.foxtrot.mappers.TrainingMapper;
import com.keldron.foxtrot.mappers.UserMapper;
import com.keldron.foxtrot.mappers.VenueMapper;
import com.keldron.foxtrot.model.User;
import com.keldron.foxtrot.model.trainings.Training;
import com.keldron.foxtrot.model.trainings.TrainingType;
import com.keldron.foxtrot.model.trainings.Venue;
import com.keldron.foxtrot.repositories.trainings.TrainingRepository;
import com.keldron.foxtrot.repositories.users.UserRepository;
import com.keldron.foxtrot.repositories.venues.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

//data persistence logic
@Service
public class DataAccessService implements UserDetailsService {

    private UserRepository userRepository;
    private TrainingRepository trainingRepository;
    private VenueRepository venueRepository;

    @Autowired
    public DataAccessService(UserRepository userRepository, TrainingRepository trainingRepository, VenueRepository venueRepository) {
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.venueRepository = venueRepository;
    }

    //Returns user by username
    public Info getByUsername(String username) {
        Optional<User> optUser = userRepository.findByUsername(username);

        if (optUser.isEmpty()) {
            return new Info.InfoBuilder().setInfoCode(APIInfoCodes.USERNAME_NOT_FOUND).setDescription("User not found").build();
        }

        UserDto userDto = UserMapper.toDto(optUser.get());

        return new Info.InfoBuilder().setInfoCode(APIInfoCodes.OK).setDescription("User found").setObject(userDto).build();
    }

    //Returns all users
    public Info getUsers() {
        Iterable<User> users = userRepository.findAll();
        Set<UserDto> usersDto = new HashSet<>();

        users.forEach(user -> {
            UserDto userDto = UserMapper.toDto(user);
            usersDto.add(userDto);
        });

        return new Info.InfoBuilder().setInfoCode(APIInfoCodes.OK).setDescription("Users found").setObject(usersDto).build();
    }

    //registers new user with ROLE_USER privilege
    public Info registerNewUser(NewUserDto userDto) {
        Optional<User> optUser = userRepository.findByUsername(userDto.getUsername());

        if (optUser.isPresent()) {
            return new Info.InfoBuilder().setInfoCode(APIInfoCodes.USERNAME_ALREADY_USED)
                    .setDescription("Username already in use").build();
        }

        User newUser = UserMapper.toStandardUser(userDto);
        userRepository.save(newUser);

        return getOKInfo();
    }

    //Allows to change name and surname of the user
    public Info userUpdate(UserDto userDto) {
        Optional<User> optUser = userRepository.findByUsername(userDto.getUsername());

        if (optUser.isEmpty()) {
            return new Info.InfoBuilder().setInfoCode(APIInfoCodes.USERNAME_NOT_FOUND)
                    .setDescription("Trying to update non-existing user").build();
        }

        User user = optUser.get();

        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());

        userRepository.save(user);
        return getOKInfo();
    }

    //Registers user to training
    public Info registerToTraining(String username, Long trainingId) {
        Optional<User> optUser = userRepository.findByUsername(username);

        if (optUser.isEmpty()) {
            return new Info.InfoBuilder().setInfoCode(APIInfoCodes.USERNAME_NOT_FOUND)
                    .setDescription("Username not found").build();
        }

        Optional<Training> optTraining = trainingRepository.findById(trainingId);

        if (optTraining.isEmpty()) {
            return new Info.InfoBuilder().setInfoCode(APIInfoCodes.TRAINING_NOT_FOUND)
                    .setDescription("Training does not exists").build();
        }

        Training training = optTraining.get();

        training.addParticipant(optUser.get());

        trainingRepository.save(training);

        return getOKInfo();
    }

    public Info unregisterFromTraining(String username, Long trainingId) {
        Optional<User> optUser = userRepository.findByUsername(username);

        if (optUser.isEmpty()) {
            return new Info.InfoBuilder().setInfoCode(APIInfoCodes.USERNAME_NOT_FOUND)
                    .setDescription("Username not found").build();
        }

        Optional<Training> optTraining = trainingRepository.findById(trainingId);

        if (optTraining.isEmpty()) {
            return new Info.InfoBuilder().setInfoCode(APIInfoCodes.TRAINING_NOT_FOUND)
                    .setDescription("Training does not exists").build();
        }

        Training training = optTraining.get();

        training.removeParticipant(optUser.get());

        trainingRepository.save(training);

        return getOKInfo();
    }

    //Allows to change all User information. For ROLE_ADMIN only!
    public Info adminUpdate(UpdateUserDto userDto) {
        Optional<User> optUser = userRepository.findByUsername(userDto.getUsername());

        if (optUser.isEmpty()) {
            return new Info.InfoBuilder().setInfoCode(APIInfoCodes.USERNAME_NOT_FOUND)
                    .setDescription("Trying to update non-existing user").build();
        }

        userDto.setPassword(optUser.get().getPassword());
        
        Set<String> authorities = optUser.get().getAuthorities().stream().map(str -> {
        	String[] splitted = str.getAuthority().split("_");
        	return splitted[1];
        }).collect(Collectors.toUnmodifiableSet());
        
        userDto.setAuthorities(authorities);
        User updateUser = UserMapper.toPrivilegedUser(userDto);
        userRepository.save(updateUser);

        return getOKInfo();
    }

    //Create new training
    public Info createTraining(NewTrainingDto trainingDto) {
        Optional<User> optUser = userRepository.findByUsername(trainingDto.getTrainer().getUsername());

        if (optUser.isEmpty()) {
            return new Info.InfoBuilder().setInfoCode(APIInfoCodes.USERNAME_NOT_FOUND)
                    .setDescription("Username not found").build();
        }

        Optional<Venue> optVenue = venueRepository.findById(trainingDto.getVenueId());

        if (optVenue.isEmpty()) {
            return new Info.InfoBuilder().setInfoCode(APIInfoCodes.VENUE_NOT_FOUND)
                    .setDescription("Venue not found").build();
        }

        try {
            TrainingType.valueOf(trainingDto.getTrainingType());
        } catch (IllegalArgumentException i) {
            return new Info.InfoBuilder().setInfoCode(APIInfoCodes.INCORECT_REQUEST)
                    .setDescription("Unknown training type. Allowed types: " + Arrays.toString(TrainingType.values())).build();
        }


        Training training = TrainingMapper.toTraining(trainingDto, optUser.get(), optVenue.get());
        trainingRepository.save(training);

        return getOKInfo();
    }

    public Info getTraining(Long id, boolean isAdmin) {
        Optional<Training> optTraining = trainingRepository.findById(id);

        if (optTraining.isEmpty()) {
            return new Info.InfoBuilder().setInfoCode(APIInfoCodes.TRAINING_NOT_FOUND)
                    .setDescription("Training does not exists").build();
        }

        Training training = optTraining.get();

        if (isAdmin) {
            TrainingDto trainingDto = TrainingMapper.toTrainingDto(training);
            return new Info.InfoBuilder().setInfoCode(APIInfoCodes.OK).setDescription("Training found").setObject(trainingDto).build();
        }
        TrainingDto trainingDto = TrainingMapper.toUsersTrainingDto(training);
        return new Info.InfoBuilder().setInfoCode(APIInfoCodes.OK).setDescription("Training found").setObject(trainingDto).build();
    }

    public Info getTrainings(boolean isAdmin) {
        Iterable<Training> trainings = trainingRepository.findAll();
        List<TrainingDto> trainingDtos = new ArrayList<>();

        trainings.forEach(t -> {
            if(isAdmin) {
                trainingDtos.add(TrainingMapper.toTrainingDto(t));
            } else {
                //Ensures user will see only available courses
                if (!t.getStartDate().isBefore(LocalDateTime.now())) {
                    trainingDtos.add(TrainingMapper.toUsersTrainingDto(t));
                }
            }
        });

        
        return new Info.InfoBuilder().setInfoCode(APIInfoCodes.OK).setDescription("Trainings found").setObject(trainingDtos).build();
    }

    //create Venue
    public Info createVenue(VenueDto venueDto) {
        Venue venue = VenueMapper.toVenue(venueDto);
        venueRepository.save(venue);

        return getOKInfo();
    }

    public Info getVenues() {
        Iterable<Venue> all = venueRepository.findAll();

        List<VenueDto> dtos = new ArrayList<>();

        all.forEach(e -> {
            VenueDto venueDto = VenueMapper.toDto(e);
            dtos.add(venueDto);
        });

        return new Info.InfoBuilder().setInfoCode(APIInfoCodes.OK).setObject(dtos).build();
    }

    //Method needed by WebSecurityConfig
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findByUsername(username);

        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }

        return optUser.get();
    }

    private Info getOKInfo() {
        return new Info.InfoBuilder().setInfoCode(APIInfoCodes.OK).build();
    }

    public Info deleteTraining(Long trainingId) {
        Optional<Training> optTraining = trainingRepository.findById(trainingId);

        if (optTraining.isEmpty()) {
            return new Info.InfoBuilder().setInfoCode(APIInfoCodes.TRAINING_NOT_FOUND)
                    .setDescription("Training does not exists").build();
        }

        Training training = optTraining.get();
        trainingRepository.delete(training);

        return new Info.InfoBuilder().setInfoCode(APIInfoCodes.OK).build();
    }

    public Info deleteUser(String username) {
        Optional<User> optUser = userRepository.findById(username);

        if (optUser.isEmpty()) {
            return new Info.InfoBuilder().setInfoCode(APIInfoCodes.USERNAME_NOT_FOUND)
                    .setDescription("User does not exists").build();
        }

        User user = optUser.get();
        userRepository.delete(user);

        return new Info.InfoBuilder().setInfoCode(APIInfoCodes.OK).build();
    }
}

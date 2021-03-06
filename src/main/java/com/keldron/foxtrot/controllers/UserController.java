package com.keldron.foxtrot.controllers;

import com.keldron.foxtrot.Utils;
import com.keldron.foxtrot.dto.NewUserDto;
import com.keldron.foxtrot.dto.UpdateUserDto;
import com.keldron.foxtrot.dto.UserDto;
import com.keldron.foxtrot.info.Info;
import com.keldron.foxtrot.repositories.service.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class UserController {

    private DataAccessService dataAccessService;

    @Autowired
    public UserController(DataAccessService userDataAccessService) {
        this.dataAccessService = userDataAccessService;
    }

    //Gets user info
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<Info> getUser(@RequestParam("username") String username, Authentication authentication) {
        if (!Utils.hasAdminRights(authentication)) {
            if (isNotTheSameUser(username, authentication.getName())) {
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }
            Info result = dataAccessService.getByUsername(username);
            return Info.getInfoResponseEntity(result, HttpStatus.OK, HttpStatus.BAD_REQUEST);
        }

        if (!username.equals("")) {
            Info result = dataAccessService.getByUsername(username);
            return Info.getInfoResponseEntity(result, HttpStatus.OK, HttpStatus.BAD_REQUEST);
        }
        Info result = dataAccessService.getUsers();
        return Info.getInfoResponseEntity(result, HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }

    //Registers new user
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Info> registerNewUser(@ModelAttribute NewUserDto newUserDto, HttpServletResponse response) throws IOException {
        Info result = dataAccessService.registerNewUser(newUserDto);
        response.sendRedirect("/home");
        return Info.getInfoResponseEntity(result, HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
    }

    //Updates user info
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ResponseEntity<Info> registerNewUser(@RequestBody UserDto userDto, Authentication authentication) {
        if (isNotTheSameUser(userDto.getUsername(), authentication.getName())) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        Info result = dataAccessService.userUpdate(userDto);
        return Info.getInfoResponseEntity(result, HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public ResponseEntity<Info> deleteUser(@RequestBody String username, Authentication authentication) {
        if (isNotTheSameUser(username, authentication.getName())) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        Info result = dataAccessService.deleteUser(username);
        return Info.getInfoResponseEntity(result, HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/users/trainings", method = RequestMethod.POST)
    public ResponseEntity<Info> registerUserToTraining(@RequestParam Long trainingID, Authentication authentication) {
        Info result = dataAccessService.registerToTraining(authentication.getName(), trainingID);
        return Info.getInfoResponseEntity(result, HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/users/trainings", method = RequestMethod.DELETE)
    public ResponseEntity<Info> unregisterUserFromTraining(@RequestParam Long trainingID, Authentication authentication) {
        Info result = dataAccessService.unregisterFromTraining(authentication.getName(), trainingID);
        return Info.getInfoResponseEntity(result, HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
    }

    //Allows admin to modify all user information
    @RequestMapping(value = "/admin/users", method = RequestMethod.PUT)
    public ResponseEntity<Info> updateUserByAdmin(@RequestBody UpdateUserDto newUserDto) {
        Info result = dataAccessService.adminUpdate(newUserDto);
        return Info.getInfoResponseEntity(result, HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
    }

    private boolean isNotTheSameUser(String username, String authenticationName) {
        return !authenticationName.equals(username);
    }
}

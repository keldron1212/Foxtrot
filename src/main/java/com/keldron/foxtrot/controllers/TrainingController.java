package com.keldron.foxtrot.controllers;

import com.keldron.foxtrot.Utils;
import com.keldron.foxtrot.dto.NewTrainingDto;
import com.keldron.foxtrot.dto.TrainingDto;
import com.keldron.foxtrot.info.Info;
import com.keldron.foxtrot.model.AuthorityRole;
import com.keldron.foxtrot.repositories.service.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class TrainingController {

    private DataAccessService service;

    @Autowired
    public TrainingController(DataAccessService service) {
        this.service = service;
    }

    @RequestMapping(value = "/trainings", method = RequestMethod.POST)
    public ResponseEntity<Info> createNewTraining(@RequestBody NewTrainingDto training) {
        Info result = service.createTraining(training);
        return Info.getInfoResponseEntity(result, HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/trainings", method = RequestMethod.GET)
    public ResponseEntity<Info> getTraining(@RequestParam(required = false) Long trainingId, Authentication authentication) {
        if (Utils.hasAdminRights(authentication)) {
            return findTraining(trainingId, true);
        }
        return findTraining(trainingId, false);
    }

    private ResponseEntity<Info> findTraining(Long trainingId, boolean isAdmin) {
        if (trainingId != null) {
            Info result = service.getTraining(trainingId, isAdmin);
            return Info.getInfoResponseEntity(result, HttpStatus.OK, HttpStatus.BAD_REQUEST);
        }
        Info result = service.getTrainings(true);
        return Info.getInfoResponseEntity(result, HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }
}

package com.keldron.foxtrot.controllers;

import com.keldron.foxtrot.dto.VenueDto;
import com.keldron.foxtrot.info.Info;
import com.keldron.foxtrot.repositories.service.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VenueController {

    private DataAccessService service;

    @Autowired
    public VenueController(DataAccessService service) {
        this.service = service;
    }

    @RequestMapping(value = "/venues", method = RequestMethod.POST)
    public ResponseEntity<Info> createNewVenue(@RequestBody VenueDto venueDto) {
        Info result = service.createVenue(venueDto);
        return Info.getInfoResponseEntity(result, HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/venues", method = RequestMethod.GET)
    public ResponseEntity<Info> getVenues() {
        Info result = service.getVenues();
        return Info.getInfoResponseEntity(result, HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
    }
}

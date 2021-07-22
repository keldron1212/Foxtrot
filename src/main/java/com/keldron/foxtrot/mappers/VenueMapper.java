package com.keldron.foxtrot.mappers;

import com.keldron.foxtrot.dto.VenueDto;
import com.keldron.foxtrot.model.trainings.Venue;

public class VenueMapper {

    public static VenueDto toDto(Venue venue) {
        return new VenueDto(venue.getId(), venue.getName(), venue.getLocation());
    }

    public static Venue toVenue(VenueDto dto) {
        return new Venue(dto.getName(), dto.getLocation());
    }
}

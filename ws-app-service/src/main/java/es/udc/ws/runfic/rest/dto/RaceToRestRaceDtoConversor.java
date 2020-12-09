package es.udc.ws.runfic.rest.dto;

import es.udc.ws.runfic.model.race.Race;

import java.time.LocalDateTime;

public class RaceToRestRaceDtoConversor {

    public static RestRaceDto toRestRaceDto(Race modelRace){
        return new RestRaceDto(modelRace.getRaceID(), modelRace.getCity(), modelRace.getDescription(),
                modelRace.getStartDateTime().toString(), modelRace.getPrice(),
                modelRace.getParticipants(), modelRace.getMaxParticipants());
    }

    public Race toRace(RestRaceDto restRace){
        return new Race(restRace.getRaceID(), restRace.getCity(), restRace.getDescription(), LocalDateTime.parse(restRace.getStartDateTime()),
                restRace.getPrice(), restRace.getParticipants(), restRace.getMaxParticipants(), null);
    }
}

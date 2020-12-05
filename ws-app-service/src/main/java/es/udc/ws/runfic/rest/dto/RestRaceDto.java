package es.udc.ws.runfic.rest.dto;

import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.runfic.model.race.Race;

import java.time.LocalDateTime;

public class RestRaceDto {
    public RestRaceDto(){}
    private Long raceID;
    private String city;
    private String description;
    private LocalDateTime startDateTime;
    private float price;
    private int participants;
    private int maxParticipants;

    public RestRaceDto(Long raceID, String city, String description,
                       LocalDateTime startDateTime, float price,
                       int participants, int maxParticipants) {
        this.raceID = raceID;
        this.city = city;
        this.description = description;
        this.startDateTime = startDateTime;
        this.price = price;
        this.participants = participants;
        this.maxParticipants = maxParticipants;
    }

    public static RestRaceDto from(Race modelRace){
        return new RestRaceDto(modelRace.getRaceID(), modelRace.getCity(), modelRace.getDescription(),
                modelRace.getStartDateTime(), modelRace.getPrice(),
                modelRace.getParticipants(), modelRace.getMaxParticipants());
    }

    public Race toRace(){
        return new Race(this.raceID, this.city, this.description, this.startDateTime,
                this.price, this.participants, this.maxParticipants, null);
    }

    public static RestRaceDto from(ObjectNode jsonNode){
        throw new UnsupportedOperationException();
    }

    public ObjectNode toJson(){
        throw new UnsupportedOperationException();
    }
}

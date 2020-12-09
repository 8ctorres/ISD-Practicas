package es.udc.ws.runfic.rest.dto;

public class RestRaceDto {
    public RestRaceDto(){}
    private Long raceID;
    private String city;
    private String description;
    private String startDateTime;
    private float price;
    private int participants;
    private int maxParticipants;

    public RestRaceDto(Long raceID, String city, String description,
                       String startDateTime, float price,
                       int participants, int maxParticipants) {
        this.raceID = raceID;
        this.city = city;
        this.description = description;
        this.startDateTime = startDateTime;
        this.price = price;
        this.participants = participants;
        this.maxParticipants = maxParticipants;
    }

    public Long getRaceID() {
        return raceID;
    }

    public void setRaceID(Long raceID) {
        this.raceID = raceID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }
}

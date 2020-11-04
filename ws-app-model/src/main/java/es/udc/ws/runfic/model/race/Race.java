package es.udc.ws.runfic.model.race;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Race {
    private Long raceID;
    private String city;
    private String description;
    private LocalDateTime startDateTime;
    private BigDecimal price;
    private int participants;
    private int maxParticipants;
    private LocalDateTime addedDateTime;

    public Race(int raceID, String city, String description, LocalDateTime startDateTime, BigDecimal price, int maxParticipants) {
        this.raceID = raceID;
        this.city = city;
        this.description = description;
        this.startDateTime = startDateTime;
        this.price = price;
        this.maxParticipants = maxParticipants;
    }

    public Race(int raceID, String city, String description, LocalDateTime startDateTime, BigDecimal price,
                int participants, int maxParticipants, LocalDateTime addedDateTime) {
        this(raceID, city, description, startDateTime, price, maxParticipants);
        this.participants = participants;
        this.addedDateTime = addedDateTime;
    }

    public int getRaceID() {
        return raceID;
    }

    public void setRaceID(int raceID) {
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

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public LocalDateTime getAddedDateTime() {
        return addedDateTime;
    }

    public void setAddedDateTime(LocalDateTime addedDateTime) {
        this.addedDateTime = addedDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Race race = (Race) o;
        return raceID == race.raceID &&
                participants == race.participants &&
                maxParticipants == race.maxParticipants &&
                city.equals(race.city) &&
                description.equals(race.description) &&
                startDateTime.equals(race.startDateTime) &&
                price.equals(race.price) &&
                addedDateTime.equals(race.addedDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(raceID, city, description, startDateTime, price, participants, maxParticipants, addedDateTime);
    }
}

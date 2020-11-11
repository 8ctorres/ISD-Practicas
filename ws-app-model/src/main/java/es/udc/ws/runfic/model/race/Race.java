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

    public Race(Long raceID, String city, String description, LocalDateTime startDateTime, BigDecimal price, int maxParticipants) {
        this.raceID = raceID;
        this.city = city;
        this.description = description;
        this.startDateTime = startDateTime;
        this.price = price;
        this.maxParticipants = maxParticipants;
        this.participants = -1;
        this.addedDateTime = null;
    }

    public Race(Long raceID, String city, String description, LocalDateTime startDateTime, BigDecimal price,
                int participants, int maxParticipants, LocalDateTime addedDateTime) {
        this(raceID, city, description, startDateTime, price, maxParticipants);
        this.participants = participants;
        this.addedDateTime = addedDateTime;
    }

    public Long getRaceID() {
        return raceID;
    }

    private void setRaceID(Long raceID) {
        this.raceID = raceID;
    }

    public String getCity() {
        return city;
    }

    private void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    private void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    private void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getParticipants() {
        return participants;
    }

    private void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    private void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public LocalDateTime getAddedDateTime() {
        return addedDateTime;
    }

    private void setAddedDateTime(LocalDateTime addedDateTime) {
        this.addedDateTime = addedDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Race race = (Race) o;
        return raceID.equals(race.raceID) &&
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

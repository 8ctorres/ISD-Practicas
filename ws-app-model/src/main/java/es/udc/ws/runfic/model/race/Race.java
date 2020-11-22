package es.udc.ws.runfic.model.race;

import java.time.LocalDateTime;
import java.util.Objects;

public class Race {
    private Long idrace;
    private String city;
    private String description;
    private LocalDateTime startDateTime;
    private float price;
    private int participants;
    private int maxParticipants;
    private LocalDateTime addedDateTime;

    public Race(Long idrace, String city, String description, LocalDateTime startDateTime, float price,
                int participants, int maxParticipants, LocalDateTime addedDateTime) {
        this.idrace = idrace;
        this.city = city;
        this.description = description;
        this.startDateTime = startDateTime;
        this.price = price;
        this.participants = participants;
        this.maxParticipants = maxParticipants;
        this.addedDateTime = addedDateTime;
    }

    public Race(String description, LocalDateTime startDateTime, float price, int maxParticipants, int participants) {
    }

    public Long getRaceID() {
        return idrace;
    }

    private void setRaceID(Long idrace) {
        this.idrace = idrace;
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

    public float getPrice() {
        return price;
    }

    private void setPrice(float price) {
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
        return idrace.equals(race.idrace) &&
                participants == race.participants &&
                maxParticipants == race.maxParticipants &&
                city.equals(race.city) &&
                description.equals(race.description) &&
                startDateTime.equals(race.startDateTime) &&
                price == race.price &&
                addedDateTime.equals(race.addedDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idrace, city, description, startDateTime, price, participants, maxParticipants, addedDateTime);
    }
}

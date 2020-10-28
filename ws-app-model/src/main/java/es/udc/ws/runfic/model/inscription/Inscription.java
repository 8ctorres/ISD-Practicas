package es.udc.ws.runfic.model.inscription;

import java.time.LocalDateTime;
import java.util.Objects;

public class Inscription {
    private int inscriptionID;
    private String user;
    private String creditCardNumber;
    private int raceID;
    private LocalDateTime inscriptionDateTime;
    private int runnerNumber;

    public Inscription(String user, String creditCardNumber, int raceID) {
        this.user = user;
        this.creditCardNumber = creditCardNumber;
        this.raceID = raceID;
    }

    public Inscription(int inscriptionID, String user, String creditCardNumber, int raceID, LocalDateTime inscriptionDateTime, int runnerNumber) {
        this(user, creditCardNumber, raceID);
        this.inscriptionID = inscriptionID;
        this.inscriptionDateTime = inscriptionDateTime;
        this.runnerNumber = runnerNumber;
    }

    public int getInscriptionID() {
        return inscriptionID;
    }

    public void setInscriptionID(int inscriptionID) {
        this.inscriptionID = inscriptionID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public int getRaceID() {
        return raceID;
    }

    public void setRaceID(int raceID) {
        this.raceID = raceID;
    }

    public LocalDateTime getInscriptionDateTime() {
        return inscriptionDateTime;
    }

    public void setInscriptionDateTime(LocalDateTime inscriptionDateTime) {
        this.inscriptionDateTime = inscriptionDateTime;
    }

    public int getRunnerNumber() {
        return runnerNumber;
    }

    public void setRunnerNumber(int runnerNumber) {
        this.runnerNumber = runnerNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inscription that = (Inscription) o;
        return inscriptionID == that.inscriptionID &&
                raceID == that.raceID &&
                runnerNumber == that.runnerNumber &&
                user.equals(that.user) &&
                creditCardNumber.equals(that.creditCardNumber) &&
                inscriptionDateTime.equals(that.inscriptionDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inscriptionID, user, creditCardNumber, raceID, inscriptionDateTime, runnerNumber);
    }
}

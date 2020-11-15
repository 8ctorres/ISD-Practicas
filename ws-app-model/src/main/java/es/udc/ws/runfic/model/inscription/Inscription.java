package es.udc.ws.runfic.model.inscription;

import java.time.LocalDateTime;
import java.util.Objects;

public class Inscription {
    private Long inscriptionID;
    private String user;
    private String creditCardNumber;
    private Long raceID;
    private LocalDateTime inscriptionDateTime;
    private int runnerNumber;
    private boolean isNumberTaken;

    public Inscription(String user, String creditCardNumber, Long raceID) {
        this.user = user;
        this.creditCardNumber = creditCardNumber;
        this.raceID = raceID;
    }

    public Inscription(Long inscriptionID, String user, String creditCardNumber, Long raceID, LocalDateTime inscriptionDateTime, int runnerNumber) {
        this(user, creditCardNumber, raceID);
        this.inscriptionID = inscriptionID;
        this.inscriptionDateTime = inscriptionDateTime;
        this.runnerNumber = runnerNumber;
        this.isNumberTaken = true;
    }

    public Long getInscriptionID() {
        return inscriptionID;
    }

    private void setInscriptionID(Long inscriptionID) {
        this.inscriptionID = inscriptionID;
    }

    public String getUser() {
        return user;
    }

    private void setUser(String user) {
        this.user = user;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    private void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Long getRaceID() {
        return raceID;
    }

    private void setRaceID(Long raceID) {
        this.raceID = raceID;
    }

    public LocalDateTime getInscriptionDateTime() {
        return inscriptionDateTime;
    }

    private void setInscriptionDateTime(LocalDateTime inscriptionDateTime) {
        this.inscriptionDateTime = inscriptionDateTime;
    }

    public int getRunnerNumber() {
        return runnerNumber;
    }

    private void setRunnerNumber(int runnerNumber) {
        this.runnerNumber = runnerNumber;
    }

    public boolean isNumberTaken() {
        return isNumberTaken;
    }

    public void setNumberTaken(boolean numberTaken) {
        isNumberTaken = numberTaken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inscription that = (Inscription) o;
        return inscriptionID.equals(that.inscriptionID) &&
                raceID.equals(that.raceID) &&
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

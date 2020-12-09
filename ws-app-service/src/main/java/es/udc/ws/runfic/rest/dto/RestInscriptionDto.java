package es.udc.ws.runfic.rest.dto;

import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.runfic.model.inscription.Inscription;

import java.time.LocalDateTime;

public class RestInscriptionDto {
    public RestInscriptionDto(){}
    private Long inscriptionID;
    private String user;
    private String creditCardNumber;
    private Long raceID;
    private int runnerNumber;
    private boolean isNumberTaken;

    public RestInscriptionDto(Long inscriptionID, String user, String creditCardNumber,
                              Long raceID,
                              int runnerNumber, boolean isNumberTaken) {
        this.inscriptionID = inscriptionID;
        this.user = user;
        this.creditCardNumber = creditCardNumber;
        this.raceID = raceID;
        this.runnerNumber = runnerNumber;
        this.isNumberTaken = isNumberTaken;
    }

    public Long getInscriptionID() {
        return inscriptionID;
    }

    public void setInscriptionID(Long inscriptionID) {
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

    public Long getRaceID() {
        return raceID;
    }

    public void setRaceID(Long raceID) {
        this.raceID = raceID;
    }

    public int getRunnerNumber() {
        return runnerNumber;
    }

    public void setRunnerNumber(int runnerNumber) {
        this.runnerNumber = runnerNumber;
    }

    public boolean isNumberTaken() {
        return isNumberTaken;
    }

    public void setNumberTaken(boolean numberTaken) {
        isNumberTaken = numberTaken;
    }
}

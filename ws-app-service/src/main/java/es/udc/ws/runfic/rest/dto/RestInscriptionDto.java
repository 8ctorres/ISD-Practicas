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

    public static RestInscriptionDto from(Inscription modelIns){
        return new RestInscriptionDto(modelIns.getInscriptionID(), modelIns.getUser(),
                modelIns.getCreditCardNumber(), modelIns.getRaceID(),
                modelIns.getRunnerNumber(), modelIns.isNumberTaken());
    }

    public Inscription toInscription(){
        return new Inscription(this.inscriptionID, this.user, this.creditCardNumber, this.raceID,
                null, this.runnerNumber, this.isNumberTaken);
    }

    public static RestInscriptionDto from(ObjectNode jsonNode){
        throw new UnsupportedOperationException();
    }

    public ObjectNode toJson(){
        throw new UnsupportedOperationException();
    }
}

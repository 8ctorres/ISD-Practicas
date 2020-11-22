package es.udc.ws.runfic.model.inscription;

import java.time.LocalDateTime;
import java.util.Objects;

public class Inscription {
    private Long idinscription;
    private String user;
    private String creditCardNumber;
    private Long idrace;
    private LocalDateTime inscriptionDateTime;
    private int runnerNumber;
    private boolean isNumberTaken;

    public Inscription(String user, String creditCardNumber, Long idrace) {
        this.user = user;
        this.creditCardNumber = creditCardNumber;
        this.idrace = idrace;
    }

    public Inscription(Long idinscription, String user, String creditCardNumber, Long idrace, LocalDateTime inscriptionDateTime, int runnerNumber, boolean numberTaken) {
        this(user, creditCardNumber, idrace);
        this.idinscription = idinscription;
        this.inscriptionDateTime = inscriptionDateTime;
        this.runnerNumber = runnerNumber;
        this.isNumberTaken = numberTaken;
    }

    public boolean getIsNumberTaken() {return isNumberTaken;}

    public Long getInscriptionID() {
        return idinscription;
    }

    private void setInscriptionID(Long idinscription) {
        this.idinscription = idinscription;
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
        return idrace;
    }

    private void setRaceID(Long idrace) {
        this.idrace = idrace;
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
        return idinscription.equals(that.idinscription) &&
                idrace.equals(that.idrace) &&
                runnerNumber == that.runnerNumber &&
                user.equals(that.user) &&
                creditCardNumber.equals(that.creditCardNumber) &&
                inscriptionDateTime.equals(that.inscriptionDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idinscription, user, creditCardNumber, idrace, inscriptionDateTime, runnerNumber);
    }
}

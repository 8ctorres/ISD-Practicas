package es.udc.ws.runfic.model.runservice;

import es.udc.ws.runfic.model.inscription.Inscription;
import es.udc.ws.runfic.model.inscription.SqlInscriptionDao;
import es.udc.ws.runfic.model.race.Race;
import es.udc.ws.runfic.model.race.SqlRaceDao;
import es.udc.ws.runfic.model.runservice.exceptions.InscriptionClosedException;
import es.udc.ws.runfic.model.runservice.exceptions.InvalidUserException;
import es.udc.ws.runfic.model.runservice.exceptions.NumberTakenException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static es.udc.ws.runfic.utils.RunficPropertyValidator.validateEmail;
import static es.udc.ws.util.validation.PropertyValidator.validateCreditCard;

public class RunServiceImpl implements RunService{

    private DataSource datasource;
    private SqlRaceDao raceDao;
    private SqlInscriptionDao inscriptionDao;

    public RunServiceImpl(){
        //Falta asignar el datasource
        //Falta asignar los daos
    }

    @Override
    public Race addRace(String city, String description, LocalDateTime startDateTime, BigDecimal price, int maxParticipants) throws InputValidationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Race findRace(Long raceID) throws InstanceNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Race> findByDate(LocalDateTime date) {
        throw new UnsupportedOperationException();

    }

    @Override
    public List<Race> findByDate(LocalDateTime date, String city) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Inscription inscribe(Long raceID, String email, String creditCardNumber)
            throws InputValidationException, InscriptionClosedException, InstanceNotFoundException {
        try (Connection connection = datasource.getConnection()) {
            validateEmail(email);
            validateCreditCard(creditCardNumber);

            Race thisrace = raceDao.find(connection, raceID);

            if ((LocalDateTime.now().plusDays(1).compareTo(thisrace.getStartDateTime())) > 0)
                throw new InscriptionClosedException("Inscriptions close 24 hours before the race starts");

            try {
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                Inscription newinsc = new Inscription(
                        null, email, creditCardNumber, raceID, LocalDateTime.now(), thisrace.getParticipants() + 1);
                //IDs are null because the database will create them

                Inscription createdinsc = inscriptionDao.create(connection, newinsc);

                connection.commit();

                return createdinsc;

            }catch(SQLException | RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Inscription> findAllFromUser(String email) throws InputValidationException {
        validateEmail(email);
        try(Connection connection = this.datasource.getConnection()){
            return this.inscriptionDao.findByUser(connection, email);
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getRunnerNumber(String email, int inscriptionID, String creditCardNumber) throws InputValidationException, InstanceNotFoundException, NumberTakenException, InvalidUserException {
        throw new UnsupportedOperationException();
    }
}

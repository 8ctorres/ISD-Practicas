package es.udc.ws.runfic.model.runservice;

import static es.udc.ws.runfic.utils.ModelConstants.MAX_PRICE;
import static es.udc.ws.runfic.utils.ModelConstants.MAX_PARTICIPANTS;

import es.udc.ws.runfic.model.inscription.Inscription;
import es.udc.ws.runfic.model.inscription.SqlInscriptionDao;
import es.udc.ws.runfic.model.race.Race;
import es.udc.ws.runfic.model.race.SqlRaceDao;
import es.udc.ws.runfic.model.runservice.exceptions.InscriptionClosedException;
import es.udc.ws.runfic.model.runservice.exceptions.InvalidUserException;
import es.udc.ws.runfic.model.runservice.exceptions.NumberTakenException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.validation.PropertyValidator;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static es.udc.ws.runfic.utils.RunficPropertyValidator.validateBigDecimal;
import static es.udc.ws.runfic.utils.RunficPropertyValidator.validateEmail;
import static es.udc.ws.util.validation.PropertyValidator.validateCreditCard;

public class RunServiceImpl implements RunService{

    private DataSource datasource;
    private SqlRaceDao raceDao;
    private SqlInscriptionDao inscriptionDao;

    public RunServiceImpl(){
        //Falta asignar el datasource
        //datasource = DataSourceLocator.getDataSource(RACE_DATA_SOURCE)
        //Falta asignar los daos
        //raceDao =
        //inscriptionDao =
    }

    @Override
    public Race addRace(String city, String description, LocalDateTime startDateTime, BigDecimal price, int maxParticipants) throws InputValidationException {
        PropertyValidator.validateMandatoryString("city", city);
        PropertyValidator.validateMandatoryString("description", description);
        validateBigDecimal("price", price, 0, MAX_PRICE);
        PropertyValidator.validateDouble("maxParticipants", maxParticipants, 0, MAX_PARTICIPANTS);

        try (Connection connection = datasource.getConnection()) {

            try {
                //Prepare connection.
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                Race race = new Race(null, city, description, startDateTime, price, maxParticipants);

                //Do work.
                Race createdRace = raceDao.create(connection, race);

                //Commit.
                connection.commit();

                return createdRace;

            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);

            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Race findRace(Long raceID) throws InstanceNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Race> findByDate(LocalDateTime date) {
        try (Connection connection = datasource.getConnection()) {
            return raceDao.findByDate(connection, date);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Race> findByDate(LocalDateTime date, String city) {
        try (Connection connection = datasource.getConnection()) {
            return raceDao.findByDateCity(connection, date, city);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Inscription inscribe(Long raceID, String email, String creditCardNumber)
            throws InputValidationException, InscriptionClosedException, InstanceNotFoundException {
        try (Connection connection = datasource.getConnection()) {
            validateEmail(email);
            validateCreditCard(creditCardNumber);

            Race thisRace = raceDao.find(connection, raceID);

            if ((LocalDateTime.now().plusDays(1).compareTo(thisRace.getStartDateTime())) > 0)
                throw new InscriptionClosedException("Inscriptions close 24 hours before the race starts");

            try {
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                Inscription newInscription = new Inscription(
                        null, email, creditCardNumber, raceID, LocalDateTime.now(), thisRace.getParticipants() + 1);
                //IDs are null because the database will create them

                Inscription createdInscription = inscriptionDao.create(connection, newInscription);

                connection.commit();

                return createdInscription;

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

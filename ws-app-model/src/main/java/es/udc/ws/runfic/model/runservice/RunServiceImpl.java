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
    public Race findRace(int raceID) throws InstanceNotFoundException {
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
    public int inscribe(int raceID, String email, String creditCardNumber) throws InputValidationException, InscriptionClosedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Inscription> findAllFromUser(String email) throws InputValidationException {
        if (false){ //Pendiente implementar en la clase PropertyValidators, un método para validar emails
            throw new InputValidationException("Invalid email. Must be in the form user@domain.tld");
        }
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

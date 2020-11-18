package es.udc.ws.runfic.test.model.runservice;

import es.udc.ws.runfic.model.inscription.Inscription;
import es.udc.ws.runfic.model.inscription.SqlInscriptionDao;
import es.udc.ws.runfic.model.inscription.SqlInscriptionDaoFactory;
import es.udc.ws.runfic.model.race.Race;
import es.udc.ws.runfic.model.runservice.RunService;
import es.udc.ws.runfic.model.runservice.RunServiceImpl;
import es.udc.ws.runfic.model.runservice.RunServiceFactory;
import es.udc.ws.runfic.model.race.SqlRaceDao;
import es.udc.ws.runfic.model.race.SqlRaceDaoFactory;
import static es.udc.ws.runfic.utils.ModelConstants.RUNFIC_DATA_SOURCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import es.udc.ws.runfic.model.runservice.exceptions.InscriptionClosedException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.junit.jupiter.api.BeforeAll;

import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class RunServiceTest {

    //This class stores the implementation of the service to test and the daos
    private static DataSource dataSource;
    private static RunService runService;
    private static SqlRaceDao raceDao;
    private static SqlInscriptionDao inscriptionDao;


    @BeforeAll
    public static void init() {
        //Creates SimpleDataSource and assigns it

        dataSource = new SimpleDataSource();

        //Add it to DataSourceLocator so the methods can access it
        DataSourceLocator.addDataSource(RUNFIC_DATA_SOURCE, dataSource);

        RunService runService = RunServiceFactory.getService();
        SqlRaceDao raceDao = SqlRaceDaoFactory.getDao();
        SqlInscriptionDao inscriptionDao = SqlInscriptionDaoFactory.getDao();
    }

    private void removeRace(Long raceID) {
        try (Connection connection = dataSource.getConnection()) {
            try {
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
                raceDao.remove(connection, raceID);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void removeInscription(Long inscriptionID) {
        try (Connection connection = dataSource.getConnection()) {
            try {
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
                inscriptionDao.remove(connection, inscriptionID);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    //Carlos
    @Test
    public void testFindOnlyOneFromUser() throws InputValidationException, InstanceNotFoundException, InscriptionClosedException {
        //Calls findAllFromUser on a user that only has one inscription
        //and it should return only that one


        //First insert a Race
        LocalDateTime inicioCarrera = LocalDateTime.of(2021, Month.APRIL, 14, 17, 30);
        Race addedRace = runService.addRace("Coruña", "Carrera popular semana santa", inicioCarrera, BigDecimal.valueOf(8.50), 400);

        //Inscribe the User in that Race
        Inscription addedInscription = runService.inscribe(addedRace.getRaceID(), "carlos.torres@udc.es", "1234 2345 3456 4567");

        try {
            //Try and find the inscription
            List<Inscription> found = runService.findAllFromUser("carlos.torres@udc.es");

            //There should be only one Inscription
            assertEquals(found.size(), 1);
            //And it should be the one we added
            assertEquals(addedInscription, found.get(0));

        } finally {
            //Remove everything from the DB
            removeRace(addedRace.getRaceID());
            removeInscription(addedInscription.getInscriptionID());
        }
    }

    //Brais
    private Race createRace(Race race) {

        String city = race.getCity();
        String description = race.getDescription();
        int maxParticipants = race.getMaxParticipants();
        BigDecimal price = race.getPrice();
        LocalDateTime startDateTime = race.getStartDateTime();

        Race addedRace = null;

        try {
            addedRace = runService.addRace(city, description, startDateTime, price, maxParticipants);
        } catch (InputValidationException e) {
            throw new RuntimeException(e);
        }
        return addedRace;

    }

    private Race getValidRace() {
        return null;
    }

    //Brais
    @Test
    public void testAddMovieAndFindMovie() throws InputValidationException, InstanceNotFoundException {

        Race race = getValidRace();
        Race addedRace = null;

        String city = race.getCity();
        String description = race.getDescription();
        int maxParticipants = race.getMaxParticipants();
        BigDecimal price = race.getPrice();
        LocalDateTime startDateTime = race.getStartDateTime();

        try {

            // Create Movie
            LocalDateTime beforeCreationDate = LocalDateTime.now().withNano(0);

            addedRace = runService.addRace(city, description, startDateTime, price, maxParticipants);

            LocalDateTime afterCreationDate = LocalDateTime.now().withNano(0);

            // Find Movie
            Race foundRace = runService.findRace(addedRace.getRaceID());

            assertEquals(addedRace, foundRace);
            assertEquals(foundRace.getCity(),race.getCity());
            assertEquals(foundRace.getDescription(),race.getDescription());
            assertEquals(foundRace.getMaxParticipants(),race.getMaxParticipants());
            assertEquals(foundRace.getPrice(),race.getPrice());
            assertTrue((foundRace.getAddedDateTime().compareTo(beforeCreationDate) >= 0)
                    && (foundRace.getAddedDateTime().compareTo(afterCreationDate) <= 0));

        } finally {
            // Clear Database
            if (addedRace!=null) {
                removeRace(addedRace.getRaceID());
            }
        }
    }

}

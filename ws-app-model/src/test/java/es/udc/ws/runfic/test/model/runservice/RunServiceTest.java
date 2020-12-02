package es.udc.ws.runfic.test.model.runservice;

import es.udc.ws.runfic.model.inscription.Inscription;
import es.udc.ws.runfic.model.inscription.SqlInscriptionDao;
import es.udc.ws.runfic.model.inscription.SqlInscriptionDaoFactory;
import es.udc.ws.runfic.model.race.Race;
import es.udc.ws.runfic.model.race.SqlRaceDao;
import es.udc.ws.runfic.model.race.SqlRaceDaoFactory;
import es.udc.ws.runfic.model.runservice.RunService;
import es.udc.ws.runfic.model.runservice.RunServiceFactory;
import es.udc.ws.runfic.model.runservice.exceptions.InscriptionClosedException;
import es.udc.ws.runfic.model.runservice.exceptions.InvalidUserException;
import es.udc.ws.runfic.model.runservice.exceptions.NumberTakenException;
import es.udc.ws.runfic.model.runservice.exceptions.RaceFullException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static es.udc.ws.runfic.utils.ModelConstants.RUNFIC_DATA_SOURCE;
import static org.junit.jupiter.api.Assertions.*;

public class RunServiceTest {

    //This class stores the implementation of the service to test and the daos
    private static DataSource dataSource;
    private static RunService runService;
    private static SqlRaceDao raceDao;
    private static SqlInscriptionDao inscriptionDao;
    private final long NON_EXISTENT_RACE_ID = -1;


    @BeforeAll
    public static void init() {
        //Creates SimpleDataSource and assigns it
        dataSource = new SimpleDataSource();

        //Add it to DataSourceLocator so the methods can access it
        DataSourceLocator.addDataSource(RUNFIC_DATA_SOURCE, dataSource);

        runService = RunServiceFactory.getService();
        raceDao = SqlRaceDaoFactory.getDao();
        inscriptionDao = SqlInscriptionDaoFactory.getDao();
    }

    //Carlos
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

    //Carlos
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

    //Brais
    private Race createRace(Race race) {

        String city = race.getCity();
        String description = race.getDescription();
        int maxParticipants = race.getMaxParticipants();
        float price = race.getPrice();
        LocalDateTime startDateTime = race.getStartDateTime();

        Race addedRace = null;

        try {
            addedRace = runService.addRace(city, description, startDateTime, price, maxParticipants);
        } catch (InputValidationException e) {
            throw new RuntimeException(e);
        }
        return addedRace;

    }

//
//    Casos de prueba Brais
//
//    Relativos al CU 1 - Añadir Carrera
//
//        CP 1 - Añadir la carrera correctamente
//        CP 2 - Añadir carrera con StartDate anterior al tiempo actual
//
//

    //Caso de Prueba 1
    @Test
    public void testAddRaceAndFindRace() throws InputValidationException, InstanceNotFoundException {

        Race race = runService.addRace("Ourense", "Carrera del turrón 1", LocalDateTime.of(2021, Month.JANUARY, 6, 10, 30), 5, 1000);
        Race addedRace = null;

        String city = race.getCity();
        String description = race.getDescription();
        int maxParticipants = race.getMaxParticipants();
        float price = race.getPrice();
        LocalDateTime startDateTime = race.getStartDateTime();

        addedRace = runService.addRace(city, description, startDateTime, price, maxParticipants);

        // Find Race
        Race foundRace = runService.findRace(addedRace.getRaceID());

        assertEquals(foundRace.getCity(),city);
        assertEquals(foundRace.getDescription(),description);
        assertEquals(foundRace.getMaxParticipants(),maxParticipants);
        assertEquals(foundRace.getPrice(),price);

        // Clear Database
        removeRace(addedRace.getRaceID());
    }

    //Caso de Prueba 2
    @Test
    public void testAddRaceAndFindRaceInvalidDate() throws InputValidationException {
        assertThrows(InputValidationException.class, () -> {
                runService.addRace("Ourense", "Carrera del turrón 2", LocalDateTime.of(1956, Month.JANUARY, 6, 10, 30), 5, 1000);
            });
    }

//
//    Casos de prueba Brais
//
//    Relativos al CU 3 - Buscar Carreras anteriores a una fecha
//
//        CP 1 - Encontrar las carreras anteriores a una fecha correctamente
//        CP 2 - Encontrar las carreras de una ciudad y anteriores a una fecha correctamente
//
//

    //Caso de Prueba 1
    @Test
    public void testFindByDate() throws InputValidationException {

        Race race = runService.addRace("Ourense", "Carrera del turrón 3", LocalDateTime.of(2021, Month.JANUARY, 6, 10, 30), 5, 1000);
        Race addedRace = null;

        String city = race.getCity();
        String description = race.getDescription();
        int maxParticipants = race.getMaxParticipants();
        float price = race.getPrice();
        LocalDateTime startDateTime = race.getStartDateTime();

        LocalDateTime date = LocalDateTime.of(2021, Month.JANUARY, 6, 10, 30);


            // Create Race
            addedRace = runService.addRace(city, description, startDateTime, price, maxParticipants);

            // Find Race
            List<Race> list = runService.findByDate(date);

            for(Race foundRace: list){
                assertTrue((foundRace.getStartDateTime().compareTo(date) <= 0) &&
                        (foundRace.getStartDateTime().compareTo(LocalDateTime.now().withNano(0)) >= 0));
            }


            // Clear Database
            removeRace(addedRace.getRaceID());
    }

    //Caso de Prueba 2
    @Test
    public void testFindByDateCity() throws InputValidationException {

        Race race = runService.addRace("Ourense", "Carrera del turrón 4", LocalDateTime.of(2021, Month.JANUARY, 6, 10, 30), 5, 1000);
        Race addedRace = null;

        String city = race.getCity();
        String description = race.getDescription();
        int maxParticipants = race.getMaxParticipants();
        float price = race.getPrice();
        LocalDateTime startDateTime = race.getStartDateTime();

        LocalDateTime date = LocalDateTime.of(2021, Month.JANUARY, 6, 10, 30);

            // Create Race
            addedRace = runService.addRace(city, description, startDateTime, price, maxParticipants);

            // Find Race
            List<Race> list = runService.findByDate(date, city);

            for(Race foundRace: list){
                assertEquals(foundRace.getCity(), city);
                assertTrue((foundRace.getStartDateTime().compareTo(date) <= 0) &&
                        (foundRace.getStartDateTime().compareTo(LocalDateTime.now().withNano(0)) >= 0));
            }

            // Clear Database
            removeRace(addedRace.getRaceID());
    }

//
//    Casos de prueba Carlos
//
//    Relativos al CU 4 - Inscribir un usuario
//
//        CP 1 - Inscribir un usuario con email inválido
//        CP 2 - Inscribir un usuario con tarjeta de crédito inválida
//        CP 3 - Inscribir un usuario en una carrera que no existe
//        CP 4 - Inscribir un usuario en una carrera que ya está llena
//        CP 5 - Inscribir un usuario en una carrera que empieza en menos de 24h
//        CP 6 - Realizar una inscripción y el inscriptionID es correcto
//

    //Caso de Prueba 1
    @Test
    public void testInscribeInvalidEmail() throws InputValidationException{

        //Creates a Race
        Race createdRace = runService.addRace("Betanzos", "Carrera de san valentín", LocalDateTime.of(2021, Month.FEBRUARY, 14, 12, 30), 6f, 800);

        assertThrows(InputValidationException.class, () -> {
            runService.inscribe(createdRace.getRaceID(), "unemailquenosirve", "1233 4566 7899 7410");
        });

        //Clear
        removeRace(createdRace.getRaceID());
    }

    //Caso de prueba 2
    @Test
    public void testInscribeInvalidCard() throws InputValidationException{
        //Creates a Race
        Race createdRace = runService.addRace("Betanzos", "Carrera de san valentín", LocalDateTime.of(2021, Month.FEBRUARY, 14, 12, 30), 6f, 800);

        assertThrows(InputValidationException.class, () -> {
            runService.inscribe(createdRace.getRaceID(), "carlos.torres@udc.es", "te pago con dinero del monopoly");
        });

        //Clear Database
        removeRace(createdRace.getRaceID());
    }

    //Caso de Prueba 3
    @Test
    public void testInscribeNonexistentRace(){
        //Does NOT create a Race
        assertThrows(InstanceNotFoundException.class, () -> {
            runService.inscribe(Long.valueOf("456481"), "carlos.torres@udc.es", "1233 4566 7899 8520");
        });
    }

    //Caso de Prueba 4
    @Test
    public void testInscribeRaceFull() throws InputValidationException, InstanceNotFoundException, InscriptionClosedException, RaceFullException{
        //Creates a Race with a maximum of 2 participants
        Race createdRace = runService.addRace("Ferrol", "Carrera muy exclusiva", LocalDateTime.of(2021, Month.JUNE, 29, 17, 15), 800f, 2);

        //Se inscribe a los primeros dos corredores
        Inscription ins1 = runService.inscribe(createdRace.getRaceID(), "ricachon1@udc.es", "7899 4566 1233 0258");
        Inscription ins2 = runService.inscribe(createdRace.getRaceID(), "ricachon2@usc.gal", "7410 8520 9630 4568");

        assertThrows(RaceFullException.class, () -> {
            //Se intenta inscribir un tercer corredor
            runService.inscribe(createdRace.getRaceID(), "ricachon3@uvigo.es", "9874 6520 3214 8529");
        });

        //Borramos las dos inscripciones anteriores, y la carrera

        removeInscription(ins1.getInscriptionID());
        removeInscription(ins2.getInscriptionID());
        removeRace(createdRace.getRaceID());
    }

    //Caso de Prueba 5
    @Test
    public void testInscribeLate() throws InputValidationException{
        //Creates a Race that starts in three hours
        LocalDateTime starttime = LocalDateTime.now().plusHours(3);
        Race createdRace = runService.addRace("Moaña", "Carrera pa ya mismo", starttime, 5f, 200);

        assertThrows(InscriptionClosedException.class, () -> {
            runService.inscribe(createdRace.getRaceID(), "carlos.torres@udc.es", "9632 8521 7412 8547");
        });

        //Remove the Race
        removeRace(createdRace.getRaceID());

    }

    //Caso de prueba 6
    @Test
    public void testSuccessfullInscription() throws InputValidationException, InscriptionClosedException, InstanceNotFoundException, RaceFullException {
        //Creates a valid Race
        Race createdRace = runService.addRace("Ribeira", "Carreira popular do veran", LocalDateTime.of(2021, Month.JULY, 10, 10, 0), 5f, 2500);
        Inscription inscription = runService.inscribe(createdRace.getRaceID(), "carlos.torres@udc.es", "9856 6523 8745 5412");

        //Get the Race from the DB
        Race readRace = runService.findRace(createdRace.getRaceID());

        //Check it has an inscriptionID
        assertNotNull(inscription.getInscriptionID());
        //Check the participant was registered
        assertEquals(1, readRace.getParticipants());

        //Removes Race from DB
        removeRace(readRace.getRaceID());
    }

//
//
//        Casos de Prueba Carlos
//
//        Relativos al CU 5 - Recuperar las inscripciones de un usuario
//
//        CP 1 - Si la BBDD está vacia
//        CP 2 - Si no hay ninguna de ese usuario pero hay de otros
//        CP 3 - Si hay solo una de ese usuario y nada más
//        CP 4 - Si hay solo una de ese usuario, mezclada con otros
//        CP 5 - Si todas son de ese usuario
//        CP 6 - Si hay varias de ese usuario, mezcladas con otras
//
//

    //Caso de Prueba 1
    @Test
    public void testFindOnEmpty() throws InputValidationException {
        //Try to find the Inscription
        List<Inscription> found = runService.findAllFromUser("carlos.torres@udc.es");

        //Should contain nothing
        assertEquals(0, found.size());
    }

    //Caso de Prueba 2
    @Test
    public void testFindOnOthers() throws InputValidationException, InscriptionClosedException, InstanceNotFoundException, RaceFullException {
        //First insert a Race
        LocalDateTime inicioCarrera = LocalDateTime.of(2021, Month.APRIL, 14, 17, 30);
        Race addedRace = runService.addRace("Coruña", "Carrera popular semana santa", inicioCarrera, 8f, 400);

        //Inscribe some users
        Inscription ins1 = runService.inscribe(addedRace.getRaceID(), "usuario1@udc.es", "1231 4564 7897 8585");
        Inscription ins2 = runService.inscribe(addedRace.getRaceID(), "usuario2@udc.es", "3211 6544 9877 9969");

        //Find all from a different user
        List<Inscription> found = runService.findAllFromUser("carlos.torres@udc.es");

        //Should contain nothing
        assertEquals(0, found.size());

        //Remove everything from DB
        removeInscription(ins1.getInscriptionID());
        removeInscription(ins2.getInscriptionID());
        removeRace(addedRace.getRaceID());
    }

    //Caso de Prueba 3
    @Test
    public void testFindOnlyOneFromUser() throws InputValidationException, InstanceNotFoundException, InscriptionClosedException, RaceFullException {
        //First insert a Race
        LocalDateTime inicioCarrera = LocalDateTime.of(2021, Month.APRIL, 14, 17, 30);
        Race addedRace = runService.addRace("Coruña", "Carrera popular semana santa", inicioCarrera, 8f, 400);

        //Inscribe the User in that Race
        Inscription addedInscription = runService.inscribe(addedRace.getRaceID(), "carlos.torres@udc.es", "1234 2345 3456 4567");

        //Try and find the inscription
        List<Inscription> found = runService.findAllFromUser("carlos.torres@udc.es");

        //There should be only one Inscription
        assertEquals(found.size(), 1);

        //Remove everything from the DB
        removeRace(addedRace.getRaceID());
        removeInscription(addedInscription.getInscriptionID());
    }



    //Caso de Prueba 4
    @Test
    public void testFindOnlyOneAmongOthers() throws InputValidationException, InscriptionClosedException, InstanceNotFoundException, RaceFullException {
        //First insert a Race
        LocalDateTime inicioCarrera = LocalDateTime.of(2021, Month.APRIL, 14, 17, 30);
        Race addedRace = runService.addRace("Coruña", "Carrera popular semana santa", inicioCarrera, 8f, 400);

        //Inscribe some users
        Inscription ins1 = runService.inscribe(addedRace.getRaceID(), "usuario1@udc.es", "1231 4564 7897 8585");
        Inscription ins2 = runService.inscribe(addedRace.getRaceID(), "usuario2@udc.es", "3211 6544 9877 9969");
        //Inscribe the User in that Race
        Inscription addedInscription = runService.inscribe(addedRace.getRaceID(), "carlos.torres@udc.es", "1234 2345 3456 4567");
        //Inscribe some more users
        Inscription ins3 = runService.inscribe(addedRace.getRaceID(), "usuario3@udc.es", "1231 4564 7897 8585");
        Inscription ins4 = runService.inscribe(addedRace.getRaceID(), "usuario4@udc.es", "3211 6544 9877 9969");

        //Try and find the inscription
        List<Inscription> found = runService.findAllFromUser("carlos.torres@udc.es");

        //There should be only one Inscription
        assertEquals(found.size(), 1);

        //Remove everything from DB
        removeInscription(ins1.getInscriptionID());
        removeInscription(ins2.getInscriptionID());
        removeInscription(ins3.getInscriptionID());
        removeInscription(ins4.getInscriptionID());
        removeInscription(addedInscription.getInscriptionID());
        removeRace(addedRace.getRaceID());
    }



    //Caso de Prueba 5
    @Test
    public void testFindAllFromUser() throws InputValidationException, InscriptionClosedException, InstanceNotFoundException, RaceFullException {
        //Create some Races
        Race race1 = runService.addRace("Coruña", "Carrera popular semana santa", LocalDateTime.of(2021, Month.APRIL, 14, 17, 30), 8f, 400);
        Race race2 = runService.addRace("Ribeira", "Carreira popular do veran", LocalDateTime.of(2021, Month.JULY, 10, 10, 0), 5f, 2500);
        Race race3 = runService.addRace("Arteixo", "Carrera sin coronavirus", LocalDateTime.of(2021, Month.AUGUST, 21, 20, 30), 9f, 1200);
        Race race4 = runService.addRace("Betanzos", "Carrera de san valentín", LocalDateTime.of(2021, Month.FEBRUARY, 14, 12, 30), 6f, 800);

        //Inscribe one user in some of those Races
        Inscription ins1 = runService.inscribe(race1.getRaceID(), "carlos.torres@udc.es", "1231 4564 7897 8585");
        Inscription ins2 = runService.inscribe(race3.getRaceID(), "carlos.torres@udc.es", "3211 6544 9877 9969");
        Inscription ins3 = runService.inscribe(race4.getRaceID(), "carlos.torres@udc.es", "1231 4564 7897 8585");

        List<Inscription> found = runService.findAllFromUser("carlos.torres@udc.es");

        //There are 3 inscriptions
        assertEquals(3, found.size());

        //Remove everything
        removeInscription(ins1.getInscriptionID());
        removeInscription(ins2.getInscriptionID());
        removeInscription(ins3.getInscriptionID());
        removeRace(race1.getRaceID());
        removeRace(race2.getRaceID());
        removeRace(race3.getRaceID());
        removeRace(race4.getRaceID());
    }

    //Caso de prueba 6
    @Test
    public void testFindAllAmongMany() throws InputValidationException, InscriptionClosedException, InstanceNotFoundException, RaceFullException {
        //Create some Races
        Race race1 = runService.addRace("Coruña", "Carrera popular semana santa", LocalDateTime.of(2021, Month.APRIL, 14, 17, 30), 8f, 400);
        Race race2 = runService.addRace("Ribeira", "Carreira popular do veran", LocalDateTime.of(2021, Month.JULY, 10, 10, 0), 5f, 2500);
        Race race3 = runService.addRace("Arteixo", "Carrera sin coronavirus", LocalDateTime.of(2021, Month.AUGUST, 21, 20, 30), 9f, 1200);
        Race race4 = runService.addRace("Betanzos", "Carrera de san valentín", LocalDateTime.of(2021, Month.FEBRUARY, 14, 12, 30), 6f, 800);

        //Inscribe some users in some Races
        Inscription ins1 = runService.inscribe(race1.getRaceID(), "carlos.torres@udc.es", "1231 4564 7897 8585");
        Inscription ins2 = runService.inscribe(race3.getRaceID(), "carlos.torres@udc.es", "3211 6544 9877 9969");
        Inscription ins3 = runService.inscribe(race4.getRaceID(), "carlos.torres@udc.es", "1231 4564 7897 8585");

        Inscription ins4 = runService.inscribe(race1.getRaceID(), "usuario2@udc.es", "1231 4564 7897 8585");
        Inscription ins5 = runService.inscribe(race2.getRaceID(), "usuario2@udc.es", "3211 6544 9877 9969");
        Inscription ins6 = runService.inscribe(race4.getRaceID(), "usuario2@udc.es", "1231 4564 7897 8585");

        Inscription ins7 = runService.inscribe(race2.getRaceID(), "usuario3@udc.es", "1231 4564 7897 8585");
        Inscription ins8 = runService.inscribe(race3.getRaceID(), "usuario4@udc.es", "3211 6544 9877 9969");
        Inscription ins9 = runService.inscribe(race4.getRaceID(), "usuario5@udc.es", "1231 4564 7897 8585");

        //Get all from user carlos.torres
        List<Inscription> found = runService.findAllFromUser("carlos.torres@udc.es");

        //There are 3 inscriptions
        assertEquals(3, found.size());

        //Remove everything
        removeRace(race1.getRaceID());
        removeRace(race2.getRaceID());
        removeRace(race3.getRaceID());
        removeRace(race4.getRaceID());
    }


//
//        Casos de Prueba Isma
//
//        Relativos al CU 2 - Buscar carreras por su identificador
//
//        CP 1 - Si no hay ninguna carrera con ese identificador
//        CP 2 - Si hay una carrera con ese identificador
//
//

    //Caso de prueba 1
    @Test
    public void testNotFound() throws InstanceNotFoundException {
        assertThrows(InstanceNotFoundException.class, () -> runService.findRace(NON_EXISTENT_RACE_ID));
    }

    //Caso de prueba 2
    @Test
    public void testFound() throws InstanceNotFoundException, InputValidationException {
        //Se añade una carrera
        Race race = runService.addRace("Vigo", "Carrera Abel Caballero", LocalDateTime.of(2021, Month.FEBRUARY, 23, 12, 30), 28f, 600);

        //Se busca y encuentra la carrera
        Race foundRace = runService.findRace(race.getRaceID());

        //Se comprueba que todos los parámetros sean iguales
        assertEquals(race.getCity(),foundRace.getCity());
        assertEquals(race.getDescription(),foundRace.getDescription());
        assertEquals(race.getMaxParticipants(),foundRace.getMaxParticipants());
        assertEquals(race.getPrice(),foundRace.getPrice());
        assertEquals(race.getStartDateTime(),foundRace.getStartDateTime());


        //Se borra la carrera creada
        removeRace(race.getRaceID());
    }



//        Casos de Prueba Isma
//
//        Relativos al CU 6 - Indicar que usuario ha recogido un dorsal correspondiente a una inscripción
//
//        CP 1 - Recoger dorsal con email inválido
//        CP 2 - Recoger dorsal con tarjeta de crédito inválida
//        CP 3 - Recoger dorsal de una carrera que no existe
//        CP 4 - El dorsal ya ha sido entregado
//        CP 5 - Recoge el dorsal y todo en orden


    //Caso de Prueba 1
    @Test
    public void testGetDorsalWithInvalidEmail()
            throws InputValidationException, InvalidUserException, InstanceNotFoundException, NumberTakenException, RaceFullException, InscriptionClosedException {

        //Creamos una carrera
        Race createdRace = runService.addRace("Domaio", "Carrera espacial", LocalDateTime.of(2021, Month.JULY, 24, 17, 30), 8f, 700);

        //Inscribimos a una persona
        Inscription createdIns = runService.inscribe(createdRace.getRaceID(), "ismael.verdec@udc.es", "4944 9485 4849 8426");

        //Intenta obtener el dorsal pero se equivoca de correo
        assertThrows(InstanceNotFoundException.class, () -> runService.getRunnerNumber("notismael.verdec@udc.es", createdRace.getRaceID(), "4944 9485 4849 8426"));

        //Borramos los elementos creados
        removeInscription(createdIns.getInscriptionID());
        removeRace(createdRace.getRaceID());
    }

    //Caso de Prueba 2
    @Test
    public void testGetDorsalWithInvalidCreditCard()
            throws InputValidationException, InvalidUserException, InstanceNotFoundException, NumberTakenException, RaceFullException, InscriptionClosedException {

        //Creamos una carrera
        Race createdRace = runService.addRace("Coruña", "Marineda City Race", LocalDateTime.of(2021, Month.JULY, 24, 17, 30), 8f, 700);

        //Inscribimos a una persona
        Inscription createdIns = runService.inscribe(createdRace.getRaceID(), "ismael.verdec@udc.es", "4944 9485 4849 8426");

        //Intenta obtener el dorsal pero se equivoca de tarjeta
        assertThrows(InputValidationException.class, () -> runService.getRunnerNumber("ismael.verdec@udc.es", createdRace.getRaceID(), "tarjeta metropolitana"));

        //Borramos los elementos creados
        removeInscription(createdIns.getInscriptionID());
        removeRace(createdRace.getRaceID());
    }

    //Caso de Prueba 3
    @Test
    public void testGetDorsalOfNonExistentRace()
            throws InputValidationException, InvalidUserException, InstanceNotFoundException, NumberTakenException, RaceFullException, InscriptionClosedException {

        //Creamos una carrera
        Race createdRace = runService.addRace("Moaña", "Carrera Iago Aspas", LocalDateTime.of(2021, Month.JUNE, 18, 12, 30), 6f, 800);

        //Inscribimos a una persona
        Inscription createdIns = runService.inscribe(createdRace.getRaceID(), "ismael.verdec@udc.es", "4944 9485 4849 8426");

        //Intenta obtener el dorsal pero se equivoca de codigo de carrera
        assertThrows(InstanceNotFoundException.class, () -> runService.getRunnerNumber("ismael.verdec@udc.es", NON_EXISTENT_RACE_ID, "4944 9485 4849 8426"));

        //Borramos los elementos creados
        removeInscription(createdIns.getInscriptionID());
        removeRace(createdRace.getRaceID());
    }
/*
    //Caso de Prueba 4
    @Test
    public void testDorsalHasBeenTaken()
            throws InputValidationException, InvalidUserException, InstanceNotFoundException, NumberTakenException, RaceFullException, InscriptionClosedException {

        //Creamos una carrera
        Race createdRace = runService.addRace("Cangas", "Carrera Moaña es mejor que Cangas", LocalDateTime.of(2021, Month.MAY, 12, 16, 30), 6f, 600);

        //Inscribimos a una persona
        Inscription createdIns = runService.inscribe(createdRace.getRaceID(), "ismael.verdec@udc.es", "4944 9485 4849 8426");

        //Obtiene el dorsal y luego vuelve a por otro para colar a su primo en la carrera
        runService.getRunnerNumber("ismael.verdec@udc.es", createdRace.getRaceID(), "4944 9485 4849 8426");
        assertThrows(NumberTakenException.class, () -> runService.getRunnerNumber("ismael.verdec@udc.es", createdRace.getRaceID(), "4944 9485 4849 8426"));

        //Borramos los elementos creados
        removeInscription(createdIns.getInscriptionID());
        removeRace(createdRace.getRaceID());
    }
*/

    //Caso de Prueba 5
    @Test
    public void testGetRunnerNumber()
            throws InputValidationException, InvalidUserException, InstanceNotFoundException, NumberTakenException, RaceFullException, InscriptionClosedException {

        //Creamos una carrera
        Race createdRace = runService.addRace("Bueu", "Carrera Bueu es incluso peor que Cangas", LocalDateTime.of(2021, Month.SEPTEMBER, 21, 17, 30), 7f, 700);

        //Inscribimos a una persona
        Inscription createdIns = runService.inscribe(createdRace.getRaceID(), "ismael.verdec@udc.es", "4944 9485 4849 8426");

        //Obtiene el dorsal 1 de la carrera y este aparece como recogido
        runService.getRunnerNumber("ismael.verdec@udc.es", createdIns.getInscriptionID(), "4944 9485 4849 8426");

        Inscription foundIns;
        try (Connection connection = dataSource.getConnection()){
            foundIns = inscriptionDao.find(connection, createdIns.getInscriptionID());
            assertEquals(1, createdIns.getRunnerNumber());
            assertTrue(foundIns.isNumberTaken());
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        //Borramos los elementos creados
        removeInscription(createdIns.getInscriptionID());
        removeRace(createdRace.getRaceID());
    }

}

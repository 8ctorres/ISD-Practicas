package es.udc.ws.runfic.test.model.runservice;

import es.udc.ws.runfic.model.race.Race;
import es.udc.ws.runfic.model.runservice.RunService;
import es.udc.ws.runfic.model.runservice.RunServiceImpl;
import es.udc.ws.runfic.model.runservice.RunServiceFactory;
import es.udc.ws.runfic.model.race.SqlRaceDao;
import es.udc.ws.runfic.model.race.SqlRaceDaoFactory;
import static es.udc.ws.runfic.utils.ModelConstants.RACE_DATA_SOURCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.junit.jupiter.api.BeforeAll;

import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RunServiceTest {

    private static RunService runService = null;
    private static SqlRaceDao raceDao = null;

    @BeforeAll
    public static void init() {

        /*
         * Create a simple data source and add it to "DataSourceLocator" (this
         * is needed to test "es.udc.ws.runfic.model.runservice.RunService"
         */
        DataSource dataSource = new SimpleDataSource();

        /* Add "dataSource" to "DataSourceLocator". */
        DataSourceLocator.addDataSource(RACE_DATA_SOURCE, dataSource);

        runService = RunServiceFactory.getService();

        raceDao = SqlRaceDaoFactory.getDao();

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

    @Test
    public void testAddMovieAndFindMovie() throws InputValidationException, InstanceNotFoundException {

        Race race = getValidRace();
        Race addedRace = null;

        try {

            // Create Movie
            LocalDateTime beforeCreationDate = LocalDateTime.now().withNano(0);

            addedRace = runService.addRace(race);

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

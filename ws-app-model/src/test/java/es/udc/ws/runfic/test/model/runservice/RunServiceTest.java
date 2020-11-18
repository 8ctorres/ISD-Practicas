package es.udc.ws.runfic.test.model.runservice;

import es.udc.ws.runfic.model.race.Race;
import es.udc.ws.runfic.model.runservice.RunService;
import es.udc.ws.runfic.model.runservice.RunServiceImpl;
import es.udc.ws.runfic.model.runservice.RunServiceFactory;
import es.udc.ws.runfic.model.race.SqlRaceDao;
import es.udc.ws.runfic.model.race.SqlRaceDaoFactory;
import static es.udc.ws.runfic.utils.ModelConstants.RACE_DATA_SOURCE;

import es.udc.ws.util.exceptions.InputValidationException;
import org.junit.jupiter.api.BeforeAll;

import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;

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

}

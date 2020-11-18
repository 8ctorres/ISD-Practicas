package es.udc.ws.runfic.test.model.runservice;

import es.udc.ws.runfic.model.runservice.RunService;
import es.udc.ws.runfic.model.runservice.RunServiceImpl;
import es.udc.ws.runfic.model.runservice.RunServiceFactory;
import es.udc.ws.runfic.model.race.SqlRaceDao;
import es.udc.ws.runfic.model.race.SqlRaceDaoFactory;
import static es.udc.ws.runfic.utils.ModelConstants.RACE_DATA_SOURCE;

import org.junit.jupiter.api.BeforeAll;

import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;

import javax.sql.DataSource;

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

}

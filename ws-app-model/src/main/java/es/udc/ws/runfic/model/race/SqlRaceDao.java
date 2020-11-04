package es.udc.ws.runfic.model.race;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public interface SqlRaceDao {
    //Persiste una carrera a la BBDD. El método devuelve un objeto Race con el atributo raceID inicializado al valor correcto.
    public Race create(Connection connection, Race race);

    //Encuentra una carrera según el ID
    public Race find(Connection connection, Long raceID) throws InstanceNotFoundException;

    //Encuentra una carrera según fecha y/o ciudad
    public List<Race> findByDate(Connection connection, LocalDateTime date);
    public List<Race> findByDateCity(Connection connection, LocalDateTime date, String city);
}

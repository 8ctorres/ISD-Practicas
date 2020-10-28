package es.udc.ws.runfic.model.race;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public interface SqlRaceDao {
    //Persiste una carrera a la BBDD. El método devuelve un objeto Race con el atributo raceID inicializado al valor correcto.
    public Race create(Connection connection, String city, String description, LocalDateTime startDateTime, BigDecimal price, int maxParticipants);
    public Race create(Connection connection, Race race);
    //TODO preguntar duda cual es mejor. Nos planteamos el segundo porque en el addRace del servicio ya se le pasan los parámetros por separado

    //Encuentra una carrera según el ID
    public Race find(Connection connection, int raceID);

    //Encuentra una carrera según fecha y/o ciudad
    public List<Race> findByDate(Connection connection, LocalDateTime date);
    public List<Race> findByDateCity(Connection connection, LocalDateTime date, String city);
}

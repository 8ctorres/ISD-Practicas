package es.udc.ws.runfic.model.race;

import es.udc.ws.runfic.model.inscription.Inscription;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public abstract class AbstractSqlRaceDao implements SqlRaceDao{
    protected AbstractSqlRaceDao(){}

    //Carlos
    @Override
    public Race find(Connection connection, Long raceID)
            throws InstanceNotFoundException {

        String queryStr =
                "SELECT raceID, city, description, startDateTime, price, maxParticipants" +
                        "FROM race WHERE raceID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryStr)){
            preparedStatement.setLong(1, raceID);

            ResultSet results = preparedStatement.executeQuery();

            if (!results.next()){
                throw new InstanceNotFoundException(raceID, Race.class.getName());
            }

            //Fetch results
            int i = 1;
            Long id = results.getLong(i++);
            String city = results.getString(i++);
            String description = results.getString(i++);
            LocalDateTime startDateTime = results.getTimestamp(i++).toLocalDateTime();
            BigDecimal price = results.getBigDecimal(i++);
            int maxParticipants = results.getInt(i);

            return new Race(id, city, description, startDateTime, price, maxParticipants);


        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    //Isma
    @Override
    public List<Race> findByDate(Connection connection, LocalDateTime date) {
        String queryStr =
                "SELECT raceID, city, description, startDateTime, price, maxParticipants" +
                        "FROM race WHERE date = ?";

        Timestamp timestamp = Timestamp.valueOf(date);

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryStr)){
            preparedStatement.setTimestamp(1,timestamp);

            ResultSet results = preparedStatement.executeQuery();

            List<Race> list = new ArrayList<>();

            while (results.next()) {
                int i = 1;

                Long id = results.getLong(i++);
                String city = results.getString(i++);
                String description = results.getString(i++);
                LocalDateTime startDateTime = results.getTimestamp(i++).toLocalDateTime();
                BigDecimal price = results.getBigDecimal(i++);
                int maxParticipants = results.getInt(i);

                Race race = new Race(id, city, description, startDateTime, price, maxParticipants);
                list.add(race);
            }

            return list;

        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Race> findByDateCity(Connection connection, LocalDateTime date, String city) {
        return null;
    }

    //Carlos
    @Override
    public int remove(Connection connection, Long raceID) {
        String queryStr = "DELETE FROM race WHERE raceID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryStr)) {
            preparedStatement.setLong(1, raceID);
            return preparedStatement.executeUpdate();
            //We dont check if it actually deleted something because we don't really care
            //If it deleted nothing, it's not a problem
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


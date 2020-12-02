package es.udc.ws.runfic.model.race;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSqlRaceDao implements SqlRaceDao{
    protected AbstractSqlRaceDao(){}

    //Carlos
    @Override
    public Race find(Connection connection, Long idrace)
            throws InstanceNotFoundException {

        String queryStr =
                "SELECT idrace, city, description, startDateTime, price, participants, maxParticipants, addedDateTime" +
                        " FROM Race WHERE idrace = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryStr)){
            preparedStatement.setLong(1, idrace);

            ResultSet results = preparedStatement.executeQuery();

            if (!results.next()){
                throw new InstanceNotFoundException(idrace, Race.class.getName());
            }

            //Fetch results
            int i = 1;
            Long id = results.getLong(i++);
            String city = results.getString(i++);
            String description = results.getString(i++);
            LocalDateTime startDateTime = results.getTimestamp(i++).toLocalDateTime();
            float price = results.getFloat(i++);
            int participants = results.getInt(i++);
            int maxParticipants = results.getInt(i++);
            LocalDateTime addedDateTime = results.getTimestamp(i).toLocalDateTime();

            return new Race(id, city, description, startDateTime, price, participants, maxParticipants, addedDateTime);


        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    //Isma
    @Override
    public List<Race> findByDate(Connection connection, LocalDateTime date) {
        String queryStr =
                "SELECT idrace, city, description, startDateTime, price, participants, maxParticipants, addedDateTime" +
                        " FROM Race WHERE startDateTime = ?";

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
                float price = results.getFloat(i++);
                int participants = results.getInt(i++);
                int maxParticipants = results.getInt(i++);
                LocalDateTime addedDateTime = results.getTimestamp(i).toLocalDateTime();

                Race race = new Race(id, city, description, startDateTime, price, participants, maxParticipants, addedDateTime);
                list.add(race);
            }

            return list;

        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Brais
    @Override
    public List<Race> findByDateCity(Connection connection, LocalDateTime date, String city) {
        String queryStr =
                "SELECT idrace, city, description, startDateTime, price, participants, maxParticipants, addedDateTime" +
                        " FROM Race WHERE startDateTime = ? AND city = ?";

        Timestamp timestamp = Timestamp.valueOf(date);

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryStr)){
            preparedStatement.setTimestamp(1,timestamp);
            preparedStatement.setString(2, city);

            ResultSet results = preparedStatement.executeQuery();

            List<Race> list = new ArrayList<>();

            while (results.next()) {
                int i = 1;
                Long id = results.getLong(i++);
                String cityresult = results.getString(i++);
                String description = results.getString(i++);
                LocalDateTime startDateTime = results.getTimestamp(i++).toLocalDateTime();
                float price = results.getFloat(i++);
                int participants = results.getInt(i++);
                int maxParticipants = results.getInt(i++);
                LocalDateTime addedDateTime = results.getTimestamp(i).toLocalDateTime();

                Race race = new Race(id, cityresult, description, startDateTime, price, participants, maxParticipants, addedDateTime);
                list.add(race);
            }

            return list;

        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Carlos
    @Override
    public int update(Connection connection, Race newRace){
        String queryStr = "UPDATE Race" +
                " SET city = ?, description = ?, startDateTime = ?, " +
                " price = ?, participants = ?, maxParticipants = ? WHERE idrace = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryStr)) {
            int i = 1;
            preparedStatement.setString(i++, newRace.getCity());
            preparedStatement.setString(i++, newRace.getDescription());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(newRace.getStartDateTime()));
            preparedStatement.setFloat(i++, newRace.getPrice());
            preparedStatement.setInt(i++, newRace.getParticipants());
            preparedStatement.setInt(i++, newRace.getMaxParticipants());
            preparedStatement.setLong(i, newRace.getRaceID());

            int alteredRows = preparedStatement.executeUpdate();
            return alteredRows;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    //Carlos
    @Override
    public int remove(Connection connection, Long idrace) {
        String queryStr = "DELETE FROM Race WHERE idrace = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryStr)) {
            preparedStatement.setLong(1, idrace);
            return preparedStatement.executeUpdate();
            //We dont check if it actually deleted something because we don't really care
            //If it deleted nothing, it's not a problem
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


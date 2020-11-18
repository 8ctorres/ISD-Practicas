package es.udc.ws.runfic.model.inscription;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSqlInscriptionDao implements SqlInscriptionDao{
    //Carlos
    @Override
    public Inscription find(Connection connection, Long inscriptionID)
            throws InstanceNotFoundException {

        String queryStr =
            "SELECT inscriptionID, user, creditCardNumber, raceID, inscriptionDateTime, runnerNumber, isNumberTaken"
            + "FROM inscription WHERE inscriptionID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryStr)){
            preparedStatement.setLong(1, inscriptionID);

            //Execute query
            ResultSet results = preparedStatement.executeQuery();

            if (!results.next()){
                throw new InstanceNotFoundException(inscriptionID, Inscription.class.getName());
            }

            //Fetch results
            int i = 1;
            Long id = results.getLong(i++);
            String user = results.getString(i++);
            String creditCardNumber = results.getString(i++);
            Long raceId = results.getLong(i++);
            LocalDateTime inscriptionDateTime = results.getTimestamp(i++).toLocalDateTime();
            int runnerNumber = results.getInt(i++);
            boolean numberTaken = results.getBoolean(i);

            return new Inscription(inscriptionID, user, creditCardNumber, raceId, inscriptionDateTime, runnerNumber, numberTaken);

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    //Carlos
    @Override
    public List<Inscription> findByUser(Connection connection, String email) {

        String queryStr =
                "SELECT inscriptionID, user, creditCardNumber, raceID, inscriptionDateTime, runnerNumber, isNumberTaken"
                        + "FROM inscription WHERE user = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryStr)){
            preparedStatement.setString(1, email);

            //Execute query
            ResultSet results = preparedStatement.executeQuery();

            List<Inscription> list = new ArrayList<>();

            while (results.next()) {
                int i = 1;

                Long inscriptionID = results.getLong(i++);
                String user = results.getString(i++);
                String creditCardNumber = results.getString(i++);
                Long raceId = results.getLong(i++);
                LocalDateTime inscriptionDateTime = results.getTimestamp(i++).toLocalDateTime();
                int runnerNumber = results.getInt(i++);
                boolean numberTaken = results.getBoolean(i);

                Inscription inscription = new Inscription(inscriptionID, user, creditCardNumber, raceId, inscriptionDateTime, runnerNumber, numberTaken);
                list.add(inscription);
            }

            return list;

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}

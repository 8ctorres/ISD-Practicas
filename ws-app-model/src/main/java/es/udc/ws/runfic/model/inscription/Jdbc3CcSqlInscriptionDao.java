package es.udc.ws.runfic.model.inscription;

import java.sql.*;

public class Jdbc3CcSqlInscriptionDao extends AbstractSqlInscriptionDao{
    //Carlos
    @Override
    public Inscription create(Connection connection, Inscription inscription) {
        //Create SQL Insert statement
        String queryStr = "INSERT into Inscription" +
                "(user, creditCardNumber, raceID, inscriptionDateTime, runnerNumber)" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(queryStr)){
            //Fill prepared Statement
            int i = 1;
            preparedStatement.setString(i++, inscription.getUser());
            preparedStatement.setString(i++, inscription.getCreditCardNumber());
            preparedStatement.setLong(i++, inscription.getRaceID());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(inscription.getInscriptionDateTime()));
            preparedStatement.setInt(i, inscription.getRunnerNumber());

            //Execute Query
            int modifiedRows = preparedStatement.executeUpdate();
            if (modifiedRows == 0) {
                throw new SQLException("No rows were added");
            }

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(!resultSet.next()){
                throw new SQLException("JDBC Driver returned no keys");
            }
            Long newID = resultSet.getLong(1);

            //Returns an Inscription object with the new ID
            return new Inscription(newID, inscription.getUser(), inscription.getCreditCardNumber(),
                    inscription.getRaceID(), inscription.getInscriptionDateTime(), inscription.getRunnerNumber());

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

package es.udc.ws.runfic.model.inscription;

import es.udc.ws.runfic.model.race.Race;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSqlInscriptionDao implements SqlInscriptionDao{
    //Carlos
    @Override
    public Inscription find(Connection connection, Long idinscription)
            throws InstanceNotFoundException {

        String queryStr =
            "SELECT idinscription, user, creditCardNumber, idrace, inscriptionDateTime, runnerNumber, isNumberTaken"
            + " FROM Inscription WHERE idinscription = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryStr)){
            preparedStatement.setLong(1, idinscription);

            //Execute query
            ResultSet results = preparedStatement.executeQuery();

            if (!results.next()){
                throw new InstanceNotFoundException(idinscription, Inscription.class.getName());
            }

            //Fetch results
            int i = 1;
            Long id = results.getLong(i++);
            String user = results.getString(i++);
            String creditCardNumber = results.getString(i++);
            Long idrace = results.getLong(i++);
            LocalDateTime inscriptionDateTime = results.getTimestamp(i++).toLocalDateTime();
            int runnerNumber = results.getInt(i++);
            boolean numberTaken = results.getBoolean(i);

            return new Inscription(idinscription, user, creditCardNumber, idrace, inscriptionDateTime, runnerNumber, numberTaken);

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    //Carlos
    @Override
    public List<Inscription> findByUser(Connection connection, String email) {

        String queryStr =
                "SELECT idinscription, user, creditCardNumber, idrace, inscriptionDateTime, runnerNumber, isNumberTaken"
                        + " FROM Inscription WHERE user = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryStr)){
            preparedStatement.setString(1, email);

            //Execute query
            ResultSet results = preparedStatement.executeQuery();

            List<Inscription> list = new ArrayList<>();

            while (results.next()) {
                int i = 1;

                Long idinscription = results.getLong(i++);
                String user = results.getString(i++);
                String creditCardNumber = results.getString(i++);
                Long idrace = results.getLong(i++);
                LocalDateTime inscriptionDateTime = results.getTimestamp(i++).toLocalDateTime();
                int runnerNumber = results.getInt(i++);
                boolean numberTaken = results.getBoolean(i);

                Inscription inscription = new Inscription(idinscription, user, creditCardNumber, idrace, inscriptionDateTime, runnerNumber, numberTaken);
                list.add(inscription);
            }

            return list;

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    //Carlos
    @Override
    public int update(Connection connection, Long idinscription, Inscription newInscription){
        String queryStr = "UPDATE Inscription SET user = ?, creditCardNumber = ?, idrace = ?, inscriptionDateTime = ?, runnerNumber = ?, isNumberTaken = ? WHERE idinscription = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryStr)) {
            int i = 1;
            preparedStatement.setString(i++, newInscription.getUser());
            preparedStatement.setString(i++, newInscription.getCreditCardNumber());
            preparedStatement.setLong(i++, newInscription.getRaceID());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(newInscription.getInscriptionDateTime()));
            preparedStatement.setInt(i++, newInscription.getRunnerNumber());
            preparedStatement.setBoolean(i++, newInscription.getIsNumberTaken());
            preparedStatement.setLong(i, idinscription);

            int alteredRows = preparedStatement.executeUpdate();
            return alteredRows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Carlos
    @Override
    public int remove(Connection connection, Long idinscription) {
        String queryStr = "DELETE FROM Inscription WHERE idinscription = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryStr)) {
            preparedStatement.setLong(1, idinscription);
            return preparedStatement.executeUpdate();
            //We dont check if it actually deleted something because we don't really care
            //If it deleted nothing, it's not a problem
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

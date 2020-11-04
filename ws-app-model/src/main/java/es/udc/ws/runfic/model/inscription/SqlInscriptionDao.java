package es.udc.ws.runfic.model.inscription;

import java.sql.Connection;
import java.util.List;

public interface SqlInscriptionDao {
    //Persiste una inscripción a la BBDD. El método devuelve un objeto Inscription con sus atributos correctamente inicializados
    public Inscription create(Connection connection, Inscription inscription);

    //Recuperar una inscripción de la BBDD
    public Inscription find(Connection connection, int inscriptionID);

    //Recuperar todas las inscripciones de un usuario
    public List<Inscription> findByUser(Connection connection, String email);
}

package es.udc.ws.runfic.rest.json;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import es.udc.ws.runfic.model.inscription.Inscription;
import es.udc.ws.runfic.model.runservice.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class ExceptionToJsonConverter {
    private ExceptionToJsonConverter(){}

    public static ObjectNode from(InputValidationException ex){

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "InputValidation");
        exceptionObject.put("message", ex.getMessage());

        return exceptionObject;
    }

    public static ObjectNode from(InstanceNotFoundException ex){

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "InstanceNotFound");
        exceptionObject.put("instanceId", (ex.getInstanceId() != null) ?
                ex.getInstanceId().toString() : null);
        exceptionObject.put("instanceType",
                ex.getInstanceType().substring(ex.getInstanceType().lastIndexOf('.') + 1));

        return exceptionObject;
    }

    public static ObjectNode from(AlreadyInscribedException ex){
        throw new UnsupportedOperationException();
    }

    public static ObjectNode from(InscriptionClosedException ex){
        throw new UnsupportedOperationException();
    }

    public static ObjectNode from(InvalidUserException ex){
        throw new UnsupportedOperationException();
    }

    public static ObjectNode from(NumberTakenException ex){
        throw new UnsupportedOperationException();
    }

    public static ObjectNode from(RaceFullException ex){
        throw new UnsupportedOperationException();
    }
}

package es.udc.ws.runfic.rest.json;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.runfic.rest.dto.RestInscriptionDto;

public class JsonToRestInscriptionDtoConversor {

    //Isma
    public static ObjectNode toObjectNode(RestInscriptionDto inscription) {

        ObjectNode inscriptionNode = JsonNodeFactory.instance.objectNode();

        if (inscription != null) {
            inscriptionNode.put("InscriptionId", inscription.getInscriptionID());
            inscriptionNode.put("user", inscription.getUser()).
                    put("creditCard", inscription.getCreditCardNumber()).
                    put("raceID", inscription.getRaceID());
        }

        return inscriptionNode;
    }
}

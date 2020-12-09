package es.udc.ws.runfic.rest.json;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.runfic.rest.dto.RestInscriptionDto;

import java.util.List;

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

    public static ArrayNode toArrayNode(List<RestInscriptionDto> inscriptions) {

        ArrayNode inscriptionsNode = JsonNodeFactory.instance.arrayNode();
        for (int i = 0; i < inscriptions.size(); i++) {
            RestInscriptionDto inscriptionDto = inscriptions.get(i);
            ObjectNode inscriptionObject = toObjectNode(inscriptionDto);
            inscriptionsNode.add(inscriptionObject);
        }

        return inscriptionsNode;
    }
}

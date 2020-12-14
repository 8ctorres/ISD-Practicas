package es.udc.ws.runfic.rest.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.runfic.rest.dto.RestInscriptionDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;
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
        for (RestInscriptionDto inscriptionDto : inscriptions) {
            ObjectNode inscriptionObject = toObjectNode(inscriptionDto);
            inscriptionsNode.add(inscriptionObject);
        }

        return inscriptionsNode;
    }

    public static RestInscriptionDto toServiceInscriptionDto(InputStream jsonInscription) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonInscription);

            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                ObjectNode inscriptionObject = (ObjectNode) rootNode;

                JsonNode inscriptionIdNode = inscriptionObject.get("inscriptionID");
                Long inscriptionID = (inscriptionIdNode != null) ? inscriptionIdNode.longValue() : null;

                String user = inscriptionObject.get("user").textValue().trim();
                String creditCardNumber = inscriptionObject.get("creditCardNumber").textValue().trim();
                Long raceID =  inscriptionObject.get("raceID").longValue();
                int runnerNumber = inscriptionObject.get("runnerNumber").intValue();
                boolean isNumberTaken = inscriptionObject.get("isNumberTaken").booleanValue();

                return new RestInscriptionDto(inscriptionID, user, creditCardNumber,
                        raceID, runnerNumber, isNumberTaken);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
}
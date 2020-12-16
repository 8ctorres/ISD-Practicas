package es.udc.ws.runfic.service.rest.json;

import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.runfic.service.dto.ClientRaceDto;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;

public class JsonToClientRaceDtoConversor {
    public static ObjectNode toObjectNode(ClientRaceDto race){
        throw new UnsupportedOperationException();
    }

    public static ClientRaceDto toClientRaceDto(InputStream jsonRace) throws ParsingException{
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonRace);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                return toClientRaceDto(rootNode);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static ClientRaceDto toClientRaceDto(JsonNode raceNode) throws ParsingException {
        if (raceNode.getNodeType() != JsonNodeType.OBJECT) {
            throw new ParsingException("Unrecognized JSON (object expected)");
        } else {
            ObjectNode raceObject = (ObjectNode) raceNode;

            JsonNode raceIdNode = raceObject.get("raceId");
            Long raceId = (raceIdNode != null) ? raceIdNode.longValue() : null;

            String city = raceObject.get("city").textValue().trim();
            String description = raceObject.get("description").textValue().trim();
            String strStartDateTime =  raceObject.get("startDateTime").textValue().trim();
            LocalDateTime startDateTime = LocalDateTime.parse(strStartDateTime);
            float price = raceObject.get("price").floatValue();
            int participants = raceObject.get("participants").intValue();
            int maxParticipants = raceObject.get("maxParticipants").intValue();

            return new ClientRaceDto(raceId, city, description, startDateTime, price,
                    participants, maxParticipants);
        }
    }

}

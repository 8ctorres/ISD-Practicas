package es.udc.ws.runfic.rest.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.runfic.rest.dto.RestRaceDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

public class JsonToRestRaceDtoConversor {

    public static ObjectNode toObjectNode(RestRaceDto race) {

        ObjectNode raceObject = JsonNodeFactory.instance.objectNode();

        if (race.getRaceID() != null) {
            raceObject.put("raceId", race.getRaceID());
        }
        raceObject.put("city", race.getCity()).
                put("startDateTime", race.getStartDateTime()).
                put("price", race.getPrice()).
                put("description", race.getDescription()).
                put("participants", race.getParticipants()).
                put("maxParticipants", race.getMaxParticipants());

        return raceObject;
    }

    public static ArrayNode toArrayNode(List<RestRaceDto> races) {

        ArrayNode racesNode = JsonNodeFactory.instance.arrayNode();
        for (int i = 0; i < races.size(); i++) {
            RestRaceDto raceDto = races.get(i);
            ObjectNode raceObject = toObjectNode(raceDto);
            racesNode.add(raceObject);
        }

        return racesNode;
    }

    public static RestRaceDto toServiceRaceDto(InputStream jsonRace) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonRace);

            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                ObjectNode raceObject = (ObjectNode) rootNode;

                JsonNode raceIdNode = raceObject.get("raceId");
                Long raceId = (raceIdNode != null) ? raceIdNode.longValue() : null;

                String city = raceObject.get("city").textValue().trim();
                String description = raceObject.get("description").textValue().trim();
                LocalDateTime startDateTime =  raceObject.get("startDateTime").;
                float price = raceObject.get("price").floatValue();
                int participants = raceObject.get("participants").intValue();
                int maxParticipants = raceObject.get("maxParticipants").intValue();

                return new RestRaceDto(raceId, city, description, startDateTime, price, participants, maxParticipants);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
}

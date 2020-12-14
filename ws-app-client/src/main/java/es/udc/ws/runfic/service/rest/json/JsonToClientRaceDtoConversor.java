package es.udc.ws.runfic.service.rest.json;

import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.runfic.service.dto.ClientRaceDto;
import es.udc.ws.util.json.exceptions.ParsingException;

public class JsonToClientRaceDtoConversor {
    public static ObjectNode toObjectNode(ClientRaceDto race){
        throw new UnsupportedOperationException();
    }

    public static ClientRaceDto toClientRaceDto(ObjectNode jsonRace) throws ParsingException{
        throw new UnsupportedOperationException();
    }
}

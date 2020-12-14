package es.udc.ws.runfic.service.rest.json;

import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.runfic.service.dto.ClientInscriptionDto;
import es.udc.ws.runfic.service.dto.ClientRaceDto;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;
import java.util.List;

public class JsonToClientInscriptionDtoConversor {
    public static ObjectNode toObjectNode(ClientInscriptionDto inscription){
        throw new UnsupportedOperationException();
    }

    public static ClientInscriptionDto toClientInscriptionDto(InputStream jsonInscription) throws ParsingException {
        throw new UnsupportedOperationException();
    }

    public static List<ClientInscriptionDto> toClientInscriptionDtos(InputStream content) {
        throw new UnsupportedOperationException();
    }
}

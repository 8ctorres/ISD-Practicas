package es.udc.ws.runfic.service.rest;

import es.udc.ws.runfic.service.ClientRunFicService;
import es.udc.ws.runfic.service.dto.ClientInscriptionDto;
import es.udc.ws.runfic.service.dto.ClientRaceDto;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public class RestClientRunFicService implements ClientRunFicService {

    @Override
    public Long addRace(ClientRaceDto race) throws InputValidationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClientRaceDto findRace(Long raceID) throws InputValidationException, InstanceNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ClientRaceDto> findByDate(LocalDateTime date, String city) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClientInscriptionDto inscribe(Long raceID, String email, String creditCardNumber) throws InputValidationException, InstanceNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ClientInscriptionDto> findAllFromUser(String email) throws InputValidationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getRunnerNumber(String email, Long inscriptionID, String creditCardNumber) throws InputValidationException, InstanceNotFoundException {
        throw new UnsupportedOperationException();
    }
}

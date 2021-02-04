package es.udc.ws.runfic.thriftservice;

import es.udc.ws.runfic.model.inscription.Inscription;
import es.udc.ws.runfic.model.runservice.RunService;
import es.udc.ws.runfic.model.runservice.RunServiceFactory;
import es.udc.ws.runfic.model.runservice.exceptions.AlreadyInscribedException;
import es.udc.ws.runfic.model.runservice.exceptions.InscriptionClosedException;
import es.udc.ws.runfic.model.runservice.exceptions.RaceFullException;
import es.udc.ws.runfic.thrift.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.apache.thrift.TException;
import java.util.List;

public class ThriftRunficServiceImpl implements ThriftRunficService.Iface{

    @Override
    public ThriftRaceDto findRace(long var1) throws ThriftInstanceNotFoundException, TException{
        return null;
    }

    @Override
    public ThriftInscriptionDto inscribe(long raceID, String email, String ccn) throws ThriftInputValidationException, ThriftInscriptionClosedException, ThriftInstanceNotFoundException, ThriftRaceFullException, ThriftAlreadyInscribedException, TException{
        try{
            Inscription returned = RunServiceFactory.getService().inscribe(raceID, email, ccn);
            return InscriptionToThriftInscriptionDtoConversor.toThriftInscriptionDto(returned);
        } catch (InscriptionClosedException | AlreadyInscribedException | RaceFullException | InputValidationException e) {
            throw new ThriftInscriptionClosedException(e.getMessage());
        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException(e.getMessage(), e.getInstanceType());
        }
    }

    @Override
    public List<ThriftInscriptionDto> findAllFromUser(String email) throws ThriftInputValidationException, TException{
        try {
            List<Inscription> found = RunServiceFactory.getService().findAllFromUser(email);
            return InscriptionToThriftInscriptionDtoConversor.toThriftInscriptionDto(found);
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }
    }

    @Override
    public ThriftInscriptionDto getRunnerNumber(long var1, String var3) throws ThriftInputValidationException, ThriftInstanceNotFoundException, ThriftNumberTakenException, ThriftInvalidUserException, TException{
        return null;
    }
}

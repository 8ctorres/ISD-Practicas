package es.udc.ws.runfic.service.thrift;

import es.udc.ws.runfic.service.ClientRunFicService;
import es.udc.ws.runfic.service.dto.ClientInscriptionDto;
import es.udc.ws.runfic.service.dto.ClientRaceDto;
import es.udc.ws.runfic.service.dto.ServerException;
import es.udc.ws.runfic.thrift.ThriftRunficService;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.time.LocalDate;
import java.util.List;

public class ThriftClientRunficService implements ClientRunFicService {
    
    private final static String ENDPOINT_ADDRESS_PARAMETER =
            "ThriftClientRunficService.endpointAddress";
    
    private final static String endpointAddress =
            ConfigurationParametersManager.getParameter(ENDPOINT_ADDRESS_PARAMETER);
    
    @Override
    public Long addRace(ClientRaceDto race) throws InputValidationException {
        ThriftRunficService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try{
            transport.open();
            //Do operation
            throw new UnsupportedOperationException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    @Override
    public ClientRaceDto findRace(Long raceID) throws InputValidationException, InstanceNotFoundException {
        ThriftRunficService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try{
            transport.open();
            //Do operation
            throw new UnsupportedOperationException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    @Override
    public List<ClientRaceDto> findByDate(LocalDate date, String city) throws InputValidationException {
        ThriftRunficService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try{
            transport.open();
            //Do operation
            throw new UnsupportedOperationException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    @Override
    public ClientInscriptionDto inscribe(Long raceID, String email, String creditCardNumber) throws InputValidationException, InstanceNotFoundException, ServerException {
        ThriftRunficService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try{
            transport.open();
            //Do operation
            throw new UnsupportedOperationException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    @Override
    public List<ClientInscriptionDto> findAllFromUser(String email) throws InputValidationException {
        ThriftRunficService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try{
            transport.open();
            //Do operation
            throw new UnsupportedOperationException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    @Override
    public int getRunnerNumber(Long inscriptionID, String creditCardNumber) throws InputValidationException, InstanceNotFoundException, ServerException {
        ThriftRunficService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try{
            transport.open();
            //Do operation
            throw new UnsupportedOperationException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }
    
    private ThriftRunficService.Client getClient(){
        try{
            TTransport transport = new THttpClient(endpointAddress);
            TProtocol protocol = new TBinaryProtocol(transport);
            
            return new ThriftRunficService.Client(protocol);
        } catch (TTransportException e){
            throw new RuntimeException(e);
        }
    }
}

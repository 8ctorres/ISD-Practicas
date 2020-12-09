package es.udc.ws.runfic.rest.servlets;

import es.udc.ws.runfic.model.inscription.Inscription;
import es.udc.ws.runfic.model.runservice.RunServiceFactory;
import es.udc.ws.runfic.model.runservice.exceptions.AlreadyInscribedException;
import es.udc.ws.runfic.model.runservice.exceptions.InscriptionClosedException;
import es.udc.ws.runfic.model.runservice.exceptions.RaceFullException;
import es.udc.ws.runfic.rest.dto.InscriptionToRestInscriptionDtoConversor;
import es.udc.ws.runfic.rest.dto.RestInscriptionDto;
import es.udc.ws.runfic.rest.json.JsonToExceptionConversor;
import es.udc.ws.runfic.rest.json.JsonToRestInscriptionDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.exceptions.ParsingException;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InscriptionServlet extends HttpServlet {
    //Carlos
    //Corresponde al Caso de Uso 5 - findAllFromUser
    //GET /inscription?user=email
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if(path != null && path.length() > 0){
            //Si hay algo tras /inscription -> Bad Request
            ServletUtils.writeServiceResponse(resp,
                    HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(new InputValidationException("Invalid arguments " + path)),
                    null);
            return;
        }
        String email = req.getParameter("user");
        List<Inscription> modelInss = null;
        try {
            modelInss = RunServiceFactory.getService().findAllFromUser(email);
        } catch (InputValidationException e) {
            ServletUtils.writeServiceResponse(resp,
                    HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(e),
                    null);
            return;
        }

        List<RestInscriptionDto> restInss = new ArrayList<>();
        for(Inscription ins: modelInss){
            restInss.add(InscriptionToRestInscriptionDtoConversor.toRestInscriptionDto(ins));
        }

        ServletUtils.writeServiceResponse(resp,
                HttpServletResponse.SC_OK,
                JsonToRestInscriptionDtoConversor.toArrayNode(restInss),
                null);
    }

    //Para CU 4 - POST a /inscription
    //Para CU 6 - POST a /inscription/id?user=email&ccn=ccn
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path != null && path.length() > 0) {
            //Un POST contra una inscripción concreta podría ser para obtener el dorsal
            doGetRunnerRunnerNumber(req, resp);
        } else {
            doCreateInscription(req, resp);
        }
    }

    //Carlos
    //Corresponde al Caso de Uso 4 - inscribe
    private void doCreateInscription(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //Si estamos en este punto, es porque la petición fue exactamente "POST /inscription", sin nada más
        RestInscriptionDto inscriptionDto;
        try {
            inscriptionDto = JsonToRestInscriptionDtoConversor.toServiceInscriptionDto(req.getInputStream());
        } catch (ParsingException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException(ex.getMessage())),
                    null);
            return;
        }
        Inscription modelIns = null;
        try {
            modelIns = RunServiceFactory.getService().inscribe(inscriptionDto.getRaceID(), inscriptionDto.getUser(), inscriptionDto.getCreditCardNumber());
        } catch (InscriptionClosedException e) {
            JsonToExceptionConversor.toInscriptionClosedException(e);
        } catch (InstanceNotFoundException e) {
            JsonToExceptionConversor.toInstanceNotFoundException(e);
        } catch (AlreadyInscribedException e) {
            JsonToExceptionConversor.toAlreadyInscribedException(e);
        } catch (InputValidationException e) {
            JsonToExceptionConversor.toInputValidationException(e);
        } catch (RaceFullException e) {
            JsonToExceptionConversor.toRaceFullException(e);
        }
        inscriptionDto = InscriptionToRestInscriptionDtoConversor.toRestInscriptionDto(modelIns);

        String inscriptionURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + inscriptionDto.getInscriptionID();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", inscriptionURL);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonToRestInscriptionDtoConversor.toObjectNode(inscriptionDto), headers);
    }

    //Isma
    //Corresponde al Caso de Uso 6 - getRunnerNumber
    private void doGetRunnerRunnerNumber(HttpServletRequest req, HttpServletResponse resp) {
    }
}

package es.udc.ws.runfic.rest.servlets;

import es.udc.ws.runfic.model.inscription.Inscription;
import es.udc.ws.runfic.model.race.Race;
import es.udc.ws.runfic.model.runservice.RunServiceFactory;
import es.udc.ws.runfic.model.runservice.exceptions.AlreadyInscribedException;
import es.udc.ws.runfic.model.runservice.exceptions.InscriptionClosedException;
import es.udc.ws.runfic.model.runservice.exceptions.RaceFullException;
import es.udc.ws.runfic.rest.dto.InscriptionToRestInscriptionDtoConversor;
import es.udc.ws.runfic.rest.dto.RestInscriptionDto;
import es.udc.ws.runfic.rest.json.ExceptionToJsonConverter;
import es.udc.ws.runfic.rest.json.JsonToRestInscriptionDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.exceptions.ParsingException;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InscriptionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path != null && path.length() > 0){
            //Si han hecho un post que no sea directamente contra el recurso colección de inscripciones, se considera que está mal
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    ExceptionToJsonConverter.from(new InputValidationException("Invalid Request: invalid path" + path)),
            null);
            return;
        }
        RestInscriptionDto inscriptionDto;
        try{
            inscriptionDto = JsonToRestInscriptionDtoConversor.toInscriptionDto(req.getInputStream());
        }catch (ParsingException ex){
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    ExceptionToJsonConverter.from(
                            new InputValidationException(ex.getMessage())),
                    null);
            return;
        }
        Inscription modelIns;
        try{
            modelIns = RunServiceFactory.getService().inscribe(inscriptionDto.getRaceID(), inscriptionDto.getUser(), inscriptionDto.getCreditCardNumber());
        } catch (InscriptionClosedException | InstanceNotFoundException | AlreadyInscribedException | InputValidationException | RaceFullException e) {
            ExceptionToJsonConverter.from(e);
        }
        inscriptionDto = InscriptionToRestInscriptionDtoConversor.toRestInscriptionDto(modelIns);

        String inscriptionURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + inscriptionDto.getInscriptionID();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", inscriptionURL);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonToRestInscriptionDtoConversor.toObjectNode(inscriptionDto), headers);
    
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}

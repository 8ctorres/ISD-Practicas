package es.udc.ws.runfic.rest.servlets;

import es.udc.ws.runfic.model.race.Race;
import es.udc.ws.runfic.model.runservice.RunServiceFactory;
import es.udc.ws.runfic.model.runservice.exceptions.AlreadyInscribedException;
import es.udc.ws.runfic.model.runservice.exceptions.InscriptionClosedException;
import es.udc.ws.runfic.rest.dto.RestRaceDto;
import es.udc.ws.runfic.rest.json.ExceptionToJsonConverter;
import es.udc.ws.runfic.rest.json.JsonToRestRaceDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.exceptions.ParsingException;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RaceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    ExceptionToJsonConverter.from(
                            new InputValidationException("Invalid Request: " + "invalid race id")),
                    null);
            return;
        }
        String raceIdAsString = path.substring(1);
        Long raceId;
        try {
            raceId = Long.valueOf(raceIdAsString);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    ExceptionToJsonConverter.from(
                            new InputValidationException("Invalid Request: " + "invalid sale id '" + raceIdAsString)),
                    null);
            return;
        }
        Race race;
        try {
            race = RunServiceFactory.getService().findRace(raceId);
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    ExceptionToJsonConverter.from(ex), null);
            return;
        }

            RestRaceDto raceDto = RestRaceDto.from(race);

            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                    raceDto.toJson(), null);


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path != null && path.length() > 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    ExceptionToJsonConverter.from(
                            new InputValidationException("Invalid Request: " + "invalid path " + path)),
                    null);
            return;
        }
        RestRaceDto raceDto;
        try {
            raceDto = JsonToRestRaceDtoConversor.toServiceRaceDto(req.getInputStream());
        } catch (ParsingException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, ExceptionToJsonConverter
                    .from(new InputValidationException(ex.getMessage())), null);
            return;
        }
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

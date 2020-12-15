package es.udc.ws.runfic.rest.servlets;

import es.udc.ws.runfic.model.race.Race;
import es.udc.ws.runfic.model.runservice.RunServiceFactory;
import es.udc.ws.runfic.rest.dto.RaceToRestRaceDtoConversor;
import es.udc.ws.runfic.rest.dto.RestRaceDto;
import es.udc.ws.runfic.rest.json.JsonToExceptionConversor;
import es.udc.ws.runfic.rest.json.JsonToRestRaceDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.exceptions.ParsingException;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaceServlet extends HttpServlet {

    //Para CU 2, es GET /race/id
    //Para CU 3, es GET /race?params
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {
            //No se pasó un ID de carrera
            doFindById(req, resp);
        } else {
            //Sí se pasó un ID de carrera
            doFindByDateAndCity(req, resp);
        }
    }

    //Isma
    //Corresponde al Caso de Uso 2 -> findRace
    private void doFindById(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        String path = ServletUtils.normalizePath(req.getPathInfo());
        String raceIdAsString = path.substring(1);
        long raceId;
        try {
            raceId = Long.parseLong(raceIdAsString);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "invalid race id '" + raceIdAsString)),
                    null);
            return;
        }
        Race race;
        try {
            race = RunServiceFactory.getService().findRace(raceId);
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    JsonToExceptionConversor.toInstanceNotFoundException(ex), null);
            return;
        }
        RestRaceDto raceDto = RaceToRestRaceDtoConversor.toRestRaceDto(race);
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                JsonToRestRaceDtoConversor.toObjectNode(raceDto), null);
    }

    //Brais
    //Corresponde al Caso de Uso 3 -> findByDate(and City)
    private void doFindByDateAndCity(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        String path = ServletUtils.normalizePath(req.getPathInfo());
        String raceCity = path.substring(2);
        String raceDateTime = path.substring(4);
        LocalDateTime dateTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTime =  LocalDateTime.parse(raceDateTime,formatter);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "invalid startDateTime '" + raceDateTime)),
                    null);
            return;
        }
        List<Race> raceList;
        try {
            raceList = RunServiceFactory.getService().findByDate(dateTime, raceCity);
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    JsonToExceptionConversor.toInstanceNotFoundException(ex), null);
            return;
        }
        List<RestRaceDto> raceDtoList = new ArrayList<>();;
        RestRaceDto raceDto;
        for (Race race : raceList) {
            raceDto = RaceToRestRaceDtoConversor.toRestRaceDto(race);
            raceDtoList.add(raceDto);
        }
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                JsonToRestRaceDtoConversor.toArrayNode(raceDtoList), null);
    }

    //Brais
    //Corresponde al Caso de Uso 1 - addRace
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path != null && path.length() > 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "invalid path " + path)),
                    null);
            return;
        }
        RestRaceDto raceDto;
        try {
            raceDto = JsonToRestRaceDtoConversor.toServiceRaceDto(req.getInputStream());
        } catch (ParsingException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                    .toInputValidationException(new InputValidationException(ex.getMessage())), null);
            return;
        }
        Race race = RaceToRestRaceDtoConversor.toRace(raceDto);
        try {
            race = RunServiceFactory.getService().addRace(race.getCity(), race.getDescription(), race.getStartDateTime(), race.getPrice(), race.getMaxParticipants());
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(ex), null);
            return;
        }
        raceDto = RaceToRestRaceDtoConversor.toRestRaceDto(race);

        String raceURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + race.getRaceID();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", raceURL);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonToRestRaceDtoConversor.toObjectNode(raceDto), headers);
    }
}

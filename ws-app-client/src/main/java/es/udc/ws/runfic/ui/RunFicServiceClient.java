package es.udc.ws.runfic.ui;

import es.udc.ws.runfic.service.ClientRunFicService;
import es.udc.ws.runfic.service.ClientRunFicServiceFactory;
import es.udc.ws.runfic.service.dto.ClientRaceDto;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;

public class RunFicServiceClient {
    public static void main(String[] args) {

        if(args.length == 0) {
            printUsageAndExit();
        }
        ClientRunFicService clientRunFicService =
                ClientRunFicServiceFactory.getService();
        if("-a".equalsIgnoreCase(args[0])) {
            validateArgs(args, 6, new int[] {2, 3, 5});

            // [addRace] RaceServiceClient -a <city> <description> <startDateTime> <price> <maxParticipants>

            try {
                Long raceID = clientRunFicService.addRace(new ClientRaceDto(null,
                        args[1], args[2], LocalDateTime.parse(args[3]),
                        Float.parseFloat(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6])));

                System.out.println("Race " + raceID + " created successfully");

            } catch (NumberFormatException | InputValidationException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-f".equalsIgnoreCase(args[0])) {
        validateArgs(args, 2, new int[] {1});

        // [findRace] RaceServiceClient -f <raceID>

        try {
            ClientRaceDto race = clientRunFicService.findRace(Long.parseLong(args[1]));

            System.out.println("Id: " + race.getRaceID() +
                    ", City: " + race.getCity() +
                    ", Description: " + race.getDescription() +
                    ", StartDateTime: " + race.getStartDateTime() +
                    ", Price: " + race.getPrice() +
                    ", Participants: " + race.getParticipants() +
                    ", MaxParticipants: " + race.getMaxParticipants());

        } catch (NumberFormatException | InstanceNotFoundException ex) {
            ex.printStackTrace(System.err);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
    }
    public static void validateArgs(String[] args, int expectedArgs, int[] numericArguments) {
        if(expectedArgs != args.length) {
            printUsageAndExit();
        }
        for (int position : numericArguments) {
            try {
                Double.parseDouble(args[position]);
            } catch (NumberFormatException n) {
                printUsageAndExit();
            }
        }
    }

    public static void printUsageAndExit() {
        printUsage();
        System.exit(-1);
    }

    public static void printUsage() {
        System.err.println("Usage:\n" +
                "    [addRace]          RaceServiceClient -a <city> <description> <startDateTime> <price> <maxParticipants>\n" +
                "    [findRace]         RaceServiceClient -f <raceID>\n" +
                "    [findByDate]       RaceServiceClient -d <date> <city>\n" +
                "    [inscribe]         RaceServiceClient -i <inscriptionID> <email> <creditCardNumber>\n" +
                "    [findAllFromUser]  RaceServiceClient -u <email>\n" +
                "    [getRunnerNumber]  RaceServiceClient -g <inscriptionID> <creditCardNumber>\n");
    }
}

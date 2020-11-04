package es.udc.ws.runfic.utils;

import es.udc.ws.util.exceptions.InputValidationException;

public class RunficPropertyValidator{
    private RunficPropertyValidator(){};

    public static void validateEmail(String input) throws InputValidationException {
        int arroba = input.indexOf('@');
        int otra_arroba = input.indexOf('@', arroba);
        int punto = input.indexOf('.', arroba);

        final String err_msg = "Email no válido. Debe ser del formato user@domain.ld";

        if (arroba == 0) //Si la arroba es el primer caracter
            throw new InputValidationException(err_msg);

        if(otra_arroba != -1) //Si la arroba está más de una vez
            throw new InputValidationException(err_msg);

        if(punto == -1) //Si no hay un punto después de la arroba
            throw new InputValidationException(err_msg);
    }
}

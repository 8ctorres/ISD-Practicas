package es.udc.ws.runfic.utils;

import es.udc.ws.util.exceptions.InputValidationException;

import java.math.BigDecimal;

public class RunficPropertyValidator{
    private RunficPropertyValidator(){}

    public static void validateEmail(String input) throws InputValidationException {
        final String err_msg = "Email no válido. Debe ser del formato user@domain.ld";
        /*
        int arroba = input.indexOf('@');
        int otra_arroba = input.indexOf('@', arroba);
        int punto = input.indexOf('.', arroba);

        if (arroba == 0) //Si la arroba es el primer caracter
            throw new InputValidationException(err_msg);

        if(otra_arroba != -1) //Si la arroba está más de una vez
            throw new InputValidationException(err_msg);

        if(punto == -1) //Si no hay un punto después de la arroba
            throw new InputValidationException(err_msg);

         */
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (input.matches(regex)){
            return;
        }
        throw new InputValidationException(err_msg);
    }

    public static void validateBigDecimal(String propertyName, BigDecimal input, double lowerValidLimit, double upperValidLimit)
            throws InputValidationException{
        BigDecimal lower = new BigDecimal(lowerValidLimit);
        BigDecimal upper = new BigDecimal(upperValidLimit);

        int cmplow = input.compareTo(lower);
        int cmpup = input.compareTo(upper);
        if ((cmplow < 0) || (cmpup > 0))
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be greater than " + lowerValidLimit +
                    " and lower than " + upperValidLimit + "): " +
                    input);
    }


}

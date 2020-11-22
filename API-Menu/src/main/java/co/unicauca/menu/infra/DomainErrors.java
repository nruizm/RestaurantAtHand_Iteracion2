package co.unicauca.menu.infra;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista de errores de validación del dominio
 *
 * @author Libardo, Julio
 *
 */
public class DomainErrors {

    private static List<Error> errors = new ArrayList<>();

    public static List<Error> getErrors() {
        return errors;
    }

    public static void setErrors(List<Error> errors) {
       DomainErrors.errors = errors;
    }

}

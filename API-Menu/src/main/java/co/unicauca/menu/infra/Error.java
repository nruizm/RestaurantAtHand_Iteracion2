package co.unicauca.menu.infra;

import co.unicauca.menu.domain.validators.ValidationError;




/**
 * Error al validar una tarea
 *
 * @author Kata
 *
 */
public class Error {

    /**
     * Codigo de error. Ejemplos: EMPTY_FIELD, INVALID_FIELD
     */
    public final ValidationError code;
    /**
     * Campo del dominio que tiene el error
     */
    public final String field;
    /**
     * Descripción del error.
     */
    public final String description;

    /**
     * Constructor parametrizado
     *
     * @param code codigo de error
     * @param field campo
     * @param description descripción
     */
    public Error(ValidationError code, String field, String description) {
        this.code = code;
        this.field = field;
        this.description = description;
    }
}

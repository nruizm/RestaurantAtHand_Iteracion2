package co.unicauca.plato.infra;

import java.util.List;

/**
 * Respuesta Json de las peticiones Rest
 *
 * @author libardo
 */
public class JsonResponse {
    public final boolean ok;
    public final String message;
    public final List<Error> errors;

    public JsonResponse(boolean ok, String message, List<Error> errors) {
        this.ok = ok;
        this.message = message;
        this.errors = errors;
    }


}

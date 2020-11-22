package co.unicauca.plato.presentation.rest;

import co.unicauca.plato.domain.service.PlatoService;
import co.unicauca.plato.domain.entity.Dish;
import co.unicauca.plato.infra.DomainErrors;
import co.unicauca.plato.infra.JsonResponse;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



/**
 ** API Plato de los servicios web. La anotación @Path indica la URL en la 
 * cual responderá los servicios. Esta anotación se puede poner a nivel de 
 * clase y método. Todos los servicios comparten el mismo Path, la acción 
 * se hacer mediante la anotació GET (consultar), POST (agregar), PUT (editar),
 * DELETE (eliminar).
 * @XIMENA 
 */

@Stateless
@Path("/plato")
public class PlatoController {
    
    /**
    * Se inyecta la única implementación que hay de ProductService
    */
    @Inject
    private PlatoService service;
    
    public PlatoController() {
        service = new PlatoService();
      
    }
    /*
        Su uso desde consola mediante client url:
        curl -X GET http://localhost:8084/API-Plato/plato-service/plato/ 

     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Dish> findAll() {
        return service.findAll();
    }
    
    /*
        Su uso desde consola mediante client url:
        curl -X GET http://localhost:8084/API-Plato/plato-service/plato/123 

     */
    @GET
    @Path("{Id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Dish findByIdDish(@PathParam("Id") String Id) {
        return service.findByIdDish(Id);
    }
    
    /*
        Su uso desde consola mediante client url:
        curl -X POST \
          http://localhost:8084/API-Plato/plato-service/plato/ 
          -H 'Content-Type: application/json' \
          -d '{"atrIdDish":"234", 
               "atrNameDish":"Carne",
               "atrPriceDish":"10000",
               "atrDescriptionDish":"Carne roja",
               "atrTypeDish":"Especial",
               "atrCategoriaDish":"Platos fuertes"    
               
        }'
    */
     @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Dish dish) {
        JsonResponse resp;
        if (service.create(dish)) {
            resp = new JsonResponse(true, "Plato creado con exito", null);
        } else {
            resp = new JsonResponse(false, "No se pudo crear el Plato", DomainErrors.getErrors());
        }
        return Response.ok().entity(resp).build();
    } 
    
     /*
        Su uso desde consola mediante client url:
        curl -X PUT \
          http://localhost:8084/API-Plato/plato-service/plato/123 
          -H 'Content-Type: application/json' \
          -d '{
               "atrNameDish":"Helado",
               "atrPriceDish":"5000",
               "atrDescriptionDish":"Tres bolas",
               "atrTypeDish":"Especial",
               "atrCategoriaDish":"Postres"
            
        }'
    */
    @PUT
    @Path("{Id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("Id") String Id, Dish dish) {
        JsonResponse resp;
        if (service.update(Id, dish)) {
            resp = new JsonResponse(true, "Plato modificado con exito", null);
        } else {
            resp = new JsonResponse(false, "No se pudo modificar el Plato", DomainErrors.getErrors());
        }
        return Response.ok().entity(resp).build();

    }
    
    /*
        Su uso desde consola mediante client url:
        curl -X DELETE http://localhost:8084/API-Plato/plato-service/plato/123 

     */
    @DELETE
    @Path("{Id}")
    public Response delete(@PathParam("Id") String Id) {
        JsonResponse resp;

        if (service.delete(Id)) {
            resp = new JsonResponse(true, "Plato eliminado con éxito", null);

        } else {
            resp = new JsonResponse(false, "No se pudo eliminar el plato", DomainErrors.getErrors());
        }
        service.delete(Id);

        return Response.ok().entity(resp).build();

    }
    

    
}

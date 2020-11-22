package co.unicauca.menu.presentation.rest;

import co.unicauca.menu.domain.entity.Menu;
import co.unicauca.menu.domain.service.MenuService;
import co.unicauca.menu.infra.DomainErrors;
import co.unicauca.menu.infra.JsonResponse;
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
 * API REST de los servicios web. La anotación @Path indica la URL en la 
 * cual responderá los servicios. Esta anotación se puede poner a nivel de 
 * clase y método. Todos los servicios comparten el mismo Path, la acción 
 * se hacer mediante la anotació GET (consultar), POST (agregar), PUT (editar),
 * DELETE (eliminar).
 * @author 
 */

@Stateless
@Path("/menu")
public class MenuController {
    /**
    * Se inyecta la única implementación que hay de ProductService
    */
    @Inject
    private MenuService service;
    
    public MenuController() {
        service = new MenuService();
    }
    
    /*
        Su uso desde consola mediante client url:
        curl -X GET http://localhost:8084/API-Menu/menu-service/menu/ 

     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Menu> findAll() {
        return service.findAll();
    }
    
    /*
        Su uso desde consola mediante client url:
        curl -X GET http://localhost:8084/API-Menu/menu-service/menu/100 

     */
    
    @GET
    @Path("{idMenu}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Menu findByMenuId(@PathParam("idMenu") String id) {
        return service.findByMenuId(id);
    }
    
    
    /*
        Su uso desde consola mediante client url:
        curl -X GET http://localhost:8084/API-Menu/menu-service/menu/findIdRest100 

     */
    
    @GET
    @Path("/findIdRest/{idMenu}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Menu> findByIdRest(@PathParam("idMenu") String id) {
        return service.findByIdRest(id);
    }
    
    /*
        Su uso desde consola mediante client url:
        curl -X POST \
          http://localhost:8084/API-Menu/menu-service/menu/createMenu 
          -H 'Content-Type: application/json' \
          -d '{"atrIdMenu":"200",
               "atrNomMenu":"Pizza",
               "atrIdRest":"10"
        }'
    */
    @POST
    @Path("/createMenu")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Menu menu) {
        JsonResponse resp;
        if (service.create(menu)) {
            resp = new JsonResponse(true, "Menu creado con éxito", null);
        } else {
            resp = new JsonResponse(false, "No se pudo crear el Menu", DomainErrors.getErrors());
        }
        return Response.ok().entity(resp).build();
    } 
    
    /*
        Su uso desde consola mediante client url:
        curl -X POST \
          http://localhost:8084/API-Menu/menu-service/menu/createMenuVisualizacion/100/sabado 
          -H 'Content-Type: application/json' \
          -d '{"atrIdMenu":"200",
               "atrNomMenu":"Pizza",
               "atrIdRest":"10"
        }'
    */
    
    @POST
    @Path("/createMenuVisualizacion/{idMenu}/{Dia}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createVisualizacion(@PathParam("idMenu") String idMenu,@PathParam("Dia") String dia ) {
        JsonResponse resp;
        if (service.createVisualizacion(idMenu, dia)) {
            resp = new JsonResponse(true, "Dia agregado", null);
        } else {
            resp = new JsonResponse(false, "No se pudo agregar el dia", DomainErrors.getErrors());
        }
        return Response.ok().entity(resp).build();
    }     
    
    
        /*
        Su uso desde consola mediante client url:
        curl -X POST \
          http://localhost:8084/API-Menu/menu-service/menu/createOfrece/100/sabado 
          -H 'Content-Type: application/json' \
          -d '{"atrIdMenu":"200",
               "atrNomMenu":"Pizza",
               "atrIdRest":"10"
        }'
    */
    
    @POST
    @Path("/createOfrece/{idMenu}/{idPlato}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createOfrece(@PathParam("idMenu") String idMenu,@PathParam("idPlato") String idplato ) {
        JsonResponse resp;
        if (service.createOfrece(idMenu, idplato)) {
            resp = new JsonResponse(true, "Plato agregado", null);
        } else {
            resp = new JsonResponse(false, "No se pudo agregar plato", DomainErrors.getErrors());
        }
        return Response.ok().entity(resp).build();
    } 
    
    /*
        Su uso desde consola mediante client url:
        curl -X PUT \
          http://localhost:8084/API-Menu/menu-service/menu/100 
          -H 'Content-Type: application/json' \
          -d '{
               "atrNomMenu":"Menu del dia",
               "atrIdRest":"10"
            
        }'
    */
    @PUT
    @Path("{idMenu}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("idMenu") String idMenu, Menu menu) {
        JsonResponse resp;
        if (service.update(idMenu, menu)) {
            resp = new JsonResponse(true, "Menu modificado con éxito", null);
        } else {
            resp = new JsonResponse(false, "No se pudo modificar el menu", DomainErrors.getErrors());
        }
        return Response.ok().entity(resp).build();

    }
    
    /*
        Su uso desde consola mediante client url:
        curl -X DELETE http://localhost:8084/API-Menu/menu-service/menu/100 

     */
    @DELETE
    @Path("{idMenu}")
    public Response deleteMenu(@PathParam("idMenu") String idMenu) {
        JsonResponse resp;

        if (service.delete(idMenu)) {
            resp = new JsonResponse(true, "Menu eliminado con éxito", null);

        } else {
            resp = new JsonResponse(false, "No se pudo eliminar el menu", DomainErrors.getErrors());
        }
        return Response.ok().entity(resp).build();

    }
    
    /*
        Su uso desde consola mediante client url:
        curl -X DELETE http://localhost:8084/API-Menu/menu-service/menu/deleteVisualizacion/100/sabado 

     */
    @DELETE
    @Path("/deleteVisualizacion/{idMenu}/{Dia}")
    public Response deleteVisualizacion(@PathParam("idMenu") String idMenu, @PathParam("Dia") String Dia ) {
        JsonResponse resp;

        if (service.deleteVisualizacion(idMenu, Dia)) {
            resp = new JsonResponse(true, "Dia eliminado con éxito", null);

        } else {
            resp = new JsonResponse(false, "No se pudo eliminar el dia ", DomainErrors.getErrors());
        }
        return Response.ok().entity(resp).build();
    }
    
    /*
        Su uso desde consola mediante client url:
        curl -X DELETE http://localhost:8084/API-Menu/menu-service/menu/deleteOfrece/100/sabado 

     */
    @DELETE
    @Path("/deleteOfrece/{idMenu}/{idPlato}")
    public Response deleteOfrece(@PathParam("idMenu") String idMenu, @PathParam("plato") String plato ) {
        JsonResponse resp;

        if (service.deleteOfrece(idMenu, plato)) {
            resp = new JsonResponse(true, "plato eliminado con éxito", null);

        } else {
            resp = new JsonResponse(false, "No se pudo eliminar el plato ", DomainErrors.getErrors());
        }
        return Response.ok().entity(resp).build();
    }
    
     /*
        Su uso desde consola mediante client url:
        curl -X GET http://localhost:8084/API-Menu/menu-service/menu/lisPlatos/100 

     */
    @GET
    @Path("/lisPlatos/{idMenu}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<String> findPlato(@PathParam("idMenu") String idMenu) {
        return service.listaPlatos(idMenu);
    }
    
     /*
        Su uso desde consola mediante client url:
        curl -X GET http://localhost:8084/API-Menu/menu-service/menu/lisDia/100 

     */
    @GET
    @Path("/lisDia/{idMenu}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<String> findDia(@PathParam("idMenu") String idMenu) {
        return service.listaDias(idMenu);
    }
}

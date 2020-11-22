package co.unicauca.restaurante.domain.service;

import co.unicauca.restaurante.access.IRestaurantRepository;
import co.unicauca.restaurante.domain.entity.Restaurant;
import co.unicauca.restaurante.domain.validators.ValidationError;
import co.unicauca.restaurante.infra.DomainErrors;
import co.unicauca.restaurante.infra.Error;
import co.unicauca.restaurante.infra.Utilities;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *  Servicio de restaurante. Es una fachada de acceso al negocio. Lo usa la capa de
 *  presentación.
 *  @author Nathalia Ruiz
 */
@RequestScoped
public class RestaurantService {
    
    /**
     * Dependencia de una abstacción No es algo concreto. No se sabe como será
     * implementado
     */
    @Inject
    private IRestaurantRepository repository;
    
     public void setRestaurantRepository(IRestaurantRepository repository){
        this.repository = repository;
    }
     
    
    /**
     * Busca todos los restaurantes
     *
     * @return lista de restaurantes
     */
    public List<Restaurant> findAll() {
        List<Restaurant> restaurants = repository.findAll();
        return restaurants;
    }
    
    
    /**
     * Busqueda de restaurantes que son de un Administrador
     *
     * @param userName nombre de usuario del administrador del restaurante 
     * @return lista de restaurantes, o null, si no hay ningun 
     */
    public List<Restaurant> findByAdmin(String userName) {
        return repository.findByAdmin(userName);
    }
    
    
    /**
     * Busca un restaurante por su nit 
     * @param nit nit del restaurante
     * @return restaurante , o null, si no hay ningun con ese nit
     */
    public Restaurant findByNit(String nit) {
        Restaurant restaurant = repository.findByNit(nit);
        return restaurant;
    }
    
    /**
     * Busca un restaurante por su nombre
     * @param name nombre del restaurante
     * @return restaurante , o null, si no hay ningun con ese nit
     */
    public Restaurant findByName(String name) {
        Restaurant restaurant = repository.findByName(name);
        return restaurant;
    }
    
    /**
     * Crea un nuevo restaurante
     *
     * @param newRestaurant Restaurante a guardar en la base de datos
     * @return true si lo crea, false si no
     */
    public boolean create(Restaurant newRestaurant) {
        List<Error> errors = validateCreate(newRestaurant);
        if (!errors.isEmpty()) {
            DomainErrors.setErrors(errors);
            return false;
        }
        
        //Si pasa las validaciones se graba en la bd
        return repository.create(newRestaurant);

    }

        /**
     * Edita o actualiza un Restaurante
     *
     * @param nit nit de restaurante 
     * @param newRestaurant restaurante a editar en el sistema
     * @return true si lo actualiza, false si no
     */
    public boolean update(String nit, Restaurant newRestaurant) {
        List<Error> errors = validateUpdate(nit, newRestaurant);
        if (!errors.isEmpty()) {
            DomainErrors.setErrors(errors);
            return false;
        }
        Restaurant restauranteAux = this.findByNit(nit);
        restauranteAux.setAtrNameRest(newRestaurant.getAtrNameRest());
        restauranteAux.setAtrAddressRest(newRestaurant.getAtrAddressRest());
        restauranteAux.setAtrMobileRest(newRestaurant.getAtrMobileRest());
        restauranteAux.setAtrEmailRest(newRestaurant.getAtrEmailRest());   
        restauranteAux.setAtrCityRest(newRestaurant.getAtrCityRest());  
        restauranteAux.setAtrAdmiRest(newRestaurant.getAtrAdmiRest());   
        
        repository.update(restauranteAux);
        return true;
    }
    
   
    /**
     * Elimina un restaurante de la base de datos
     *
     * @param nit nit de restaurante
     * @return true si lo elimina, false si no
     */
    public boolean delete(String nit) {
        //Validate usuario
        List<Error> errors = validateDelete(nit);
        if (!errors.isEmpty()) {
            DomainErrors.setErrors(errors);
            return false;
        }
        // Pasada la validación, se puede borrar de la bd
        return repository.delete(nit);
    }
    
    
    /**
     * Valida que el restaurante esté correcto antes de grabarlo
     *
     * @param newRestaurant restaurante
     * @return lista de errores de negocio
     */
    private List<Error> validateCreate(Restaurant newRestaurant) {
        List<Error> errors; 
        
        //Validar Restaurante
        errors = validarCampos(newRestaurant);
        if (newRestaurant.getAtrNitRest()== null || newRestaurant.getAtrNitRest().isEmpty()) {
            Error error = new Error(ValidationError.EMPTY_FIELD, "Nit", "El nit del restaurante es obligatorio");
            errors.add(error);
        }
     
        //Validar que no exista el restaurante
        if(newRestaurant.getAtrNitRest() != null){
            if (!newRestaurant.getAtrNitRest().isEmpty()) {

                Restaurant restaurantAux = repository.findByNit(newRestaurant.getAtrNitRest());

                if (restaurantAux != null) {
                    // El restaurante ya existe
                    Error error = new Error(ValidationError.INVALID_FIELD, "Nit", "El nit del restaurante ya existe");
                    errors.add(error);
                }
            }
        }
        return errors;
    }

    
    /**
     * Valida que los datos del restaurante no esten vacios o nulos 
     *
     * @param newRestaurant restaurante
     * @return lista de errores de negocio
     */
    private List<Error> validarCampos(Restaurant newRestaurant) {
                List<Error> errors = new ArrayList<>();
        
        //Validate restaurante
        if (newRestaurant.getAtrNameRest()== null || newRestaurant.getAtrNameRest().isEmpty()) {
            Error error = new Error(ValidationError.EMPTY_FIELD, "nameRest", "El nombre del restaurante es obligatorio");
            errors.add(error);
        }
        if (newRestaurant.getAtrAddressRest()== null || newRestaurant.getAtrAddressRest().isEmpty()) {
            Error error = new Error(ValidationError.EMPTY_FIELD, "Address", "La direccion del restaurante es obligatorio");
            errors.add(error);
        }
        if (newRestaurant.getAtrMobileRest()== null || newRestaurant.getAtrMobileRest().isEmpty()) {
            Error error = new Error(ValidationError.EMPTY_FIELD, "Mobile", "El telefono del restaurante es obligatorio");
            errors.add(error);
        }
        if (newRestaurant.getAtrEmailRest()== null || newRestaurant.getAtrEmailRest().isEmpty()) {
            Error error = new Error(ValidationError.EMPTY_FIELD, "Email", "El email del restaurante es obligatorio");
            errors.add(error);
        }
        if (newRestaurant.getAtrCityRest()== null || newRestaurant.getAtrCityRest().isEmpty()) {
            Error error = new Error(ValidationError.EMPTY_FIELD, "City", "La ciudad del restaurante es obligatorio");
            errors.add(error);
        }
        if (newRestaurant.getAtrAdmiRest()== null || newRestaurant.getAtrAdmiRest().isEmpty()) {
            Error error = new Error(ValidationError.EMPTY_FIELD, "userNameAdmin", "El nombre de usuario del administrador del restaurante es obligatorio");
            errors.add(error);
        }
        
       
        //Validar que el telefono del restaurante solo tenga numeros
        if(newRestaurant.getAtrMobileRest() !=null && !newRestaurant.getAtrMobileRest().isEmpty()){
            if(!Utilities.isNumeric(newRestaurant.getAtrMobileRest()) ){
                Error error = new Error(ValidationError.INVALID_FIELD, "Mobile", "El telefono del restaurante solo tiene que contener numeros");
                errors.add(error);
            }
        }
    
        return errors;
    }

    
    /**
     * Valida que el restaurante esté correcto antes de editarlo en la bd
     * @param nit nit del restaurante
     * @param newRestaurant restaurante
     * @return lista de errores de negocio
     */
    private List<Error> validateUpdate(String nit, Restaurant newRestaurant) {
        List<Error> errors = null ;
        //Validar restaurante
        errors = validarCampos(newRestaurant);
        
         // Validar que exista el restaurante
         Restaurant restaurantAux = repository.findByNit(nit);
        if (restaurantAux == null) {
            // El restaurante no existe
            Error error = new Error(ValidationError.INVALID_FIELD, "nit", "El nit del restaurante no existe");
            errors.add(error);
        }

        return errors;
    }

    
    /**
     * Valida el restaurante exista para poder eliminarlo de la BD
     *
     * @param nit nit del restaurante
     * @return lista de errores de negocio
     */
    private List<Error> validateDelete(String nit) {
        List<Error> errors = new ArrayList<>();
        // Validar que exista el restaurante
        Restaurant userAux = repository.findByNit(nit);

        if (userAux == null) {
            // El restaurante no existe
            Error error = new Error(ValidationError.INVALID_FIELD, "nit", "El nit del restaurante no existe");
            errors.add(error);
        }

        return errors;
    }

    

    
    
    
}

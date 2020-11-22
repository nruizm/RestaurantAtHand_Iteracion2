
package co.unicauca.plato.domain.service;



import co.unicauca.plato.access.IPlatoRepository;
import co.unicauca.plato.domain.entity.Dish;
import co.unicauca.plato.domain.validators.ValidationError;
import co.unicauca.plato.infra.DomainErrors;
import co.unicauca.plato.infra.Error;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
/**
 ** Servicio de Plato. Es una fachada de acceso al negocio. Lo usa la capa de
 * presentación.
 * @author XIMENA
 */
@RequestScoped
public class PlatoService 
{
      /**
     * Dependencia de una abstracción No es algo concreto. No se sabe como será
     * implementado
     */
    @Inject
    
   private IPlatoRepository repository;
    
    
    /**
     * Busca un plato por su Id
     *
     * @param prmIdDish id del plato
     * @return plato, o null, si no lo encuentra
     */
    public Dish findByIdDish(String prmIdDish) {
        return repository.findByIdDish(prmIdDish);
    }
    
    public void setPlatoRepository(IPlatoRepository repository){
        this.repository = repository;
    }
    
    /**
     * Busca todos los platos
     *
     * @return lista de platos
     */
    public List<Dish> findAll() {
        List<Dish> dishs = repository.findAll();
        return dishs;
    }
    
    /**
     * Crea un nuevo plato
     *
     * @param newPlato plato a guardar en la base de datos
     * @return true si lo crea, false si no
     */
    public boolean create(Dish newPlato) {
        List<Error> errors = validateCreate(newPlato);
        if (newPlato.getAtrIdDish()== null || newPlato.getAtrIdDish().isEmpty()) {
            Error error = new Error(ValidationError.EMPTY_FIELD, "Id", "La identificacion del plato es obligatorio");
            errors.add(error);
        }
        if (!errors.isEmpty()) {
            DomainErrors.setErrors(errors);
            return false;
        }
        //Si pasa las validaciones se graba en la bd
        return repository.create(newPlato);
    }
    
    
    /**
     * Edita o actualiza un plato
     *
     * @param Id id del plato 
     * @param newPlato plato a editar en el sistema
     * @return true si lo actualiza, false si no
     */
    public boolean update(String Id, Dish newPlato) {
        List<Error> errors = validateUpdate(Id, newPlato);
        if (!errors.isEmpty()) {
            DomainErrors.setErrors(errors);
            return false;
        }
        Dish dishAux = this.findByIdDish(Id);
        dishAux.setAtrNameDish(newPlato.getAtrNameDish());
        dishAux.setAtrPriceDish(newPlato.getAtrPriceDish());
        dishAux.setAtrCategoriaDish(newPlato.getAtrCategoriaDish());
        dishAux.setAtrDescriptionDish(newPlato.getAtrDescriptionDish());
        dishAux.setAtrTypeDish(newPlato.getAtrTypeDish());
        
           
        repository.update(dishAux);
        return true;
    }
    
    /**
     * Elimina un plato de la base de datos
     *
     * @param idDish identificacion del plato 
     * @return true si lo elimina, false si no
     */
    public boolean delete(String idDish) {
        //Validate plato
        List<Error> errors = validateDelete(idDish);
        if (!errors.isEmpty()) {
            DomainErrors.setErrors(errors);
            return false;
        }
        // Pasada la validación, se puede borrar de la bd
        return repository.delete(idDish);
    }
    
    /**
     * Valida que el plato esté correcto antes de grabarlo
     *
     * @param newPlato plato
     * @return lista de errores de negocio
     */
    private List<Error> validateCreate(Dish newPlato){
        List<Error> errors; 
        
        //Validar plato
        errors = validarCampos(newPlato);
        
        /*if (newPlato.getAtrIdDish()==null || newPlato.getAtrIdDish().isEmpty()) {
            Error error = new Error(ValidationError.EMPTY_FIELD, "Id", "El Id del plato es obligatorio");
            errors.add(error);
        }
        */
        //Validar que no exista el plato
        
        if(newPlato.getAtrIdDish() != null){
            if (!newPlato.getAtrIdDish().isEmpty()) {

                Dish dishAux = repository.findByIdDish(newPlato.getAtrIdDish());

                if (dishAux != null) {
                    // El usuario ya existe
                    Error error = new Error(ValidationError.INVALID_FIELD, "Id", "El Id del plato ya existe");
                    errors.add(error);
                }
            }
        }
        return errors;
    }

    /**
     * Valida que el plato esté correcto antes de editarlo en la bd
     * @param Id identificacion del plato
     * @param newPlato plato
     * @return lista de errores de negocio
     */
    private List<Error> validateUpdate(String Id, Dish newPlato) {
         List<Error> errors = new ArrayList<>();
        //Validar usuario
        errors = validarCampos(newPlato);
        
         // Validar que exista el plato
        Dish dishAux = repository.findByIdDish(Id);
        if (dishAux == null) {
            // El plato no existe
            Error error = new Error(ValidationError.INVALID_FIELD, "Id", "El identificador del plato no existe");
            errors.add(error);
        }

        return errors;
    }

    /**
     * Valida que los datos del plato no esten vacios o nulos 
     *
     * @param newPlato Dish
     * @return lista de errores de negocio
     */
    private List<Error> validarCampos (Dish newPlato){
        
        List<Error> errors = new ArrayList<>();
        
        //Validate plato
        
        if (newPlato.getAtrNameDish() == null || newPlato.getAtrNameDish().isEmpty()) {
            Error error = new Error(ValidationError.EMPTY_FIELD, "Name", "El nombre del plato es obligatorio");
            errors.add(error);
        }
        if (newPlato.getAtrPriceDish()== null || newPlato.getAtrPriceDish().isEmpty()) {
            Error error = new Error(ValidationError.EMPTY_FIELD, "Precio", "El precio del plato es obligatorio");
            errors.add(error);
        }
        if (newPlato.getAtrCategoriaDish()== null || newPlato.getAtrCategoriaDish().isEmpty()) {
            Error error = new Error(ValidationError.EMPTY_FIELD, "Categoria", "La categoria del plato es obligatorio");
            errors.add(error);
        }
        if (newPlato.getAtrDescriptionDish() == null || newPlato.getAtrDescriptionDish().isEmpty()) {
            Error error = new Error(ValidationError.EMPTY_FIELD, "Descripcion", "La descripcion del plato es obligatorio");
            errors.add(error);
        }
        if (newPlato.getAtrTypeDish()== null || newPlato.getAtrTypeDish().isEmpty()) {
            Error error = new Error(ValidationError.EMPTY_FIELD, "TipoPlato", "El tipo de plato es obligatorio");
            errors.add(error);
        }
              
        return errors;
    }

    /**
     * Valida que el plato exista para poder eliminarlo de la BD
     *
     * @param Id identificacion de usuario 
     * @return lista de errores de negocio
     */
    private List<Error> validateDelete(String Id) {
        
        List<Error> errors = new ArrayList<>();
        // Validar que exista el plato
        Dish dishAux = repository.findByIdDish(Id);

        if (dishAux == null) {
            // El plato no existe
            Error error = new Error(ValidationError.INVALID_FIELD, "id", "El id del plato no existe");
            errors.add(error);
        }

        return errors;
    }
    
    
    
}

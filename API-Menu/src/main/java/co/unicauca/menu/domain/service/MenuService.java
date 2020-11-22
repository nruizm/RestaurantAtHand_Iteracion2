package co.unicauca.menu.domain.service;

import co.unicauca.menu.access.IMenuRepository;
import co.unicauca.menu.domain.entity.Menu;
import co.unicauca.menu.domain.validators.ValidationError;
import co.unicauca.menu.infra.DomainErrors;
import co.unicauca.menu.infra.Error;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;


/**
 *
 * @author Michelle Vallejo
 */
@RequestScoped
public class MenuService 
{
    /**
     * Dependencia de una abstracción No es algo concreto. No se sabe como será
     * implementado
     */
    @Inject
    private IMenuRepository repository;
    
     /**
     * Busca un Menu por su id
     *
     * @param prmIdMenu id del menu
     * @return menu, o null, si no lo encuentra
     */
    
    public Menu findByMenuId(String prmIdMenu)
    {
        return repository.findByMenuId(prmIdMenu);
    }
    
    public List<Menu> findByIdRest(String prmIdRest){
        return repository.findByIdRest(prmIdRest);
    }
    
    public List<String> listaPlatos (String prmIdMenu){
        return repository.listarPlato(prmIdMenu);
    }
    
    public List<String> listaDias (String prmIdMenu){
        return repository.listarDia(prmIdMenu);
    }
    public void setMenuRepository(IMenuRepository repository)
    {
        this.repository = repository;
    }
    
     /**
     * Busca todos los menu
     *
     * @return lista de menu
     */
    public List<Menu> findAll() {
        List<Menu> menu = repository.findAll();
        return menu;
    }
    
    /**
     * Crea un nuevo menu
     *
     * @param newMenu usuario a guardar en la base de datos
     * @return true si lo crea, false si no
     */
    public boolean create(Menu newMenu) {
        List<Error> error = validateCreate(newMenu);
        if (!error.isEmpty()) {
            DomainErrors.setErrors(error);
            return false;
        }
        //Si pasa las validaciones se graba en la bd
        return repository.create(newMenu);
    }

     /**
     * Valida que el menu esté correcto antes de grabarlo
     *
     * @param newMenu Menu
     * @return lista de errores de negocio
     */
    private List<Error> validateCreate(Menu newMenu) 
    {
        List<Error> errors = null; 
        
        //Validar User
        errors = validarCampos(newMenu);
        //Validar que no exista el usuario
        if(newMenu.getAtrIdMenu()!= null){
            if (!newMenu.getAtrIdMenu().isEmpty()) {

                Menu menuAux = repository.findByMenuId(newMenu.getAtrIdMenu());

                if (menuAux != null) {
                    // El usuario ya existe
                    Error error = new Error(ValidationError.INVALID_FIELD, "Id Menu", "El id del menu ya existe");
                    errors.add(error);
                }
            }
        }
        return errors;
        
    }
    
    /**
     * Valida que los datos del menu no esten vacios o nulos 
     *
     * @param newMenu menu
     * @return lista de errores de negocio
     */
    private List<Error> validarCampos(Menu newMenu) {
        List<Error> errors = new ArrayList<>();
        
        //Validate user
        if (newMenu.getAtrIdRest()== null || newMenu.getAtrIdRest().isEmpty()) {
            Error error = new Error(ValidationError.EMPTY_FIELD, "Id Restaurante", "El id del restaurante es obligatorio");
            errors.add(error);
        }
        if (newMenu.getAtrNomMenu()== null || newMenu.getAtrNomMenu().isEmpty()) {
            Error error = new Error(ValidationError.EMPTY_FIELD, "Name", "El nombre del menu es obligatorio");
            errors.add(error);
        }
        
         return errors;
    }
    
     /**
     * Edita o actualiza un Usuario
     *
     * @param idMenu id de menu 
     * @param newMenu menu a editar en el sistema
     * @return true si lo actualiza, false si no
     */
    public boolean update(String idMenu, Menu newMenu) {
        List<Error> errors = validateUpdate(idMenu, newMenu);
        if (!errors.isEmpty()) {
            DomainErrors.setErrors(errors);
            return false;
        }
        Menu menuAux = this.findByMenuId(idMenu);
        menuAux.setAtrNomMenu(newMenu.getAtrNomMenu());
        menuAux.setAtrIdRest(newMenu.getAtrIdRest());
        
        return repository.update(menuAux); 
}

     /**
     * Valida que el menu esté correcto antes de editarlo en la bd
     * @param idMenu id del menu
     * @param newMenu menu
     * @return lista de errores de negocio
     */
    private List<Error> validateUpdate(String idMenu, Menu newMenu) {
        List<Error> errors = new ArrayList<>();
        //Validar usuario
        errors = validarCampos(newMenu);
        
         // Validar que exista el usuario
        Menu userAux = repository.findByMenuId(idMenu);
        if (userAux == null) {
            // El usuario no existe
            Error error = new Error(ValidationError.INVALID_FIELD, "idMenu", "El id del menu no existe");
            errors.add(error);
        }

        return errors;
    }
    
    /**
     * Elimina un menu de la base de datos
     *
     * @param idMenu id del manu
     * @return true si lo elimina, false si no
     */
     public boolean delete(String idMenu) {
        //Validate usuario
        List<Error> errors = validateDelete(idMenu);
        if (!errors.isEmpty()) {
            DomainErrors.setErrors(errors);
            return false;
        }
        // Pasada la validación, se puede borrar de la bd
         eliminarTodo(idMenu);
        return repository.delete(idMenu);
    }

     public void eliminarTodo(String idMenu){
         List<String> dias = repository.listarDia(idMenu);
         
         if(dias !=null){
             for(int i= 0;i< dias.size();i=0){
                 deleteVisualizacion(idMenu, dias.get(i));
             }
         }
         
         List<String> plato = repository.listarPlato(idMenu);
         
         if(plato !=null){
             for(int i= 0;i< plato.size();i=0){
                 deleteOfrece(idMenu, plato.get(i));
             }
         }
         
     }
     /**
     * Valida que el menu exista para poder eliminarlo de la BD
     *
     * @param idMenu id del menu
     * @return lista de errores de negocio
     */
    private List<Error> validateDelete(String idMenu) {
         List<Error> errors = new ArrayList<>();
        // Validar que exista el usuario
        Menu menurAux = repository.findByMenuId(idMenu);

        if (menurAux == null) {
            // El menu no existe
            Error error = new Error(ValidationError.INVALID_FIELD, "id", "El id del menu no existe");
            errors.add(error);
        }

        return errors;
    }
    
    /**
     * Crea una nueva visualizacion
     *
     * @param idMenu id del menu que se desea guardar
     * @param dia dia que desea visualizar el menu
     * @return true si lo crea, false si no
     */
   
    public boolean createVisualizacion(String idMenu, String dia)
    { 
        
        List<Error> errors = validarCamposVisualizacion( idMenu,  dia);
         
        //Validar que exista el ID Menu
        if(repository.findByMenuId(idMenu)==null){
            // El menu no existe
            Error error = new Error(ValidationError.INVALID_FIELD, "ID Menu", "El no existe un menu con ese ID");
            errors.add(error);
        }
        
        //Validar que no exista ya un registro previo
        if(existeVisualizacion(idMenu, dia)){
            
            Error error = new Error(ValidationError.INVALID_FIELD, "ID Menu, Dia", "Ya existe una visualizacion");
            errors.add(error);
            
        }
        
        //Si pasa las validaciones se graba en la base de datos 
        return repository.createVisualizacion(idMenu, dia);
    }
    
    
    public boolean deleteVisualizacion(String idMenu, String dia)
    {
        List<Error> errors = validarCamposVisualizacion( idMenu,  dia);
        
        //Verificar que se tiene un registro
        if(!existeVisualizacion(idMenu, dia)){
            Error error = new Error(ValidationError.INVALID_FIELD, "ID Menu, Dia", "NO existe una visualizacion");
            errors.add(error);
        }
        
       // Pasada la validación, se puede borrar de la bd
        return repository.deleteVisualizacion(idMenu,dia);
        
    }
    
    public boolean existeVisualizacion (String idMenu, String dia){
        List<String> dias = repository.listarDia(idMenu);
       
        if(dias !=null){
            for (int i=0; i < dias.size(); i++){
                if(dias.get(i).equals(dia))
                {
                    return true;
                }
            }
        }
        return false;
    } 
    /*
     *  Validar que no esten nulos el ID Menu y el dia 
    */
    private List<Error> validarCamposVisualizacion(String idMenu, String dia)
    {
        List<Error> errors = new ArrayList<>();
         
         if(idMenu == null || idMenu.isEmpty())
         {
             Error error = new Error(ValidationError.EMPTY_FIELD, "ID Menu ", "Es obligatorio que exista un ID menu");
             errors.add(error);
         }
         if( dia == null || dia.isEmpty())
         {
             Error error = new Error(ValidationError.EMPTY_FIELD, "Dia ", "Es obligatorio que el menu tenga un dia de visualizacion");
             errors.add(error);
         }
          return errors;    
    }

    
    /**
     * @param idMenu id del menu que se desea guardar
     * @param plato plato que tiene un menu
     * @return true si lo crea, false si no
     */
   
    public boolean createOfrece(String idMenu, String plato)
    { 
        
        List<Error> errors = validarCamposOfrece( idMenu, plato);
         
        //Validar que exista el ID Menu
        if(repository.findByMenuId(idMenu)==null){
            // El menu no existe
            Error error = new Error(ValidationError.INVALID_FIELD, "ID Menu", "No existe un ID con ese menu");
            errors.add(error);
        }
        
        //Validar que no exista ya un registro previo
        if(existeOfrece(idMenu, plato)){
            
            Error error = new Error(ValidationError.INVALID_FIELD, "ID Menu, Plato", "Ya existe ese plato en el menu");
            errors.add(error);
            
        }
        
        //Si pasa las validaciones se graba en la base de datos 
        return repository.createOfrece(idMenu,plato);
    }
    
    
    public boolean deleteOfrece(String idMenu, String plato)
    {
        List<Error> errors = validarCamposOfrece( idMenu,plato);
        
        //Verificar que se tiene un registro
        if(!existeOfrece(idMenu, plato)){
            Error error = new Error(ValidationError.INVALID_FIELD, "ID Menu, ID Plato", "NO existe el plato en el menu");
            errors.add(error);
        }
        
       // Pasada la validación, se puede borrar de la bd
        return repository.deleteOfrece(idMenu,plato);
        
    }
    
    public boolean existeOfrece (String idMenu, String plato){
        List<String> dias = repository.listarPlato(idMenu);
       
        if(dias !=null){
            for (int i=0; i < dias.size(); i++){
                if(dias.get(i).equals(plato))
                {
                    return true;
                }
            }
        }
        return false;
    } 
    /*
     *  Validar que no esten nulos el ID Menu y el plato 
    */
    private List<Error> validarCamposOfrece(String idMenu, String plato)
    {
        List<Error> errors = new ArrayList<>();
         
         if(idMenu == null || idMenu.isEmpty())
         {
             Error error = new Error(ValidationError.EMPTY_FIELD, "ID Menu ", "Es obligatorio el ID menu");
             errors.add(error);
         }
         if( plato == null || plato.isEmpty())
         {
             Error error = new Error(ValidationError.EMPTY_FIELD, "Plato ", "Es obligatorio el ID plato");
             errors.add(error);
         }
          return errors;    
    }        
            
}
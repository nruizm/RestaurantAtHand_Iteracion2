package co.unicauca.restaurante.access;

import co.unicauca.restaurante.domain.entity.Restaurant;
import java.util.List;

/**
 *
 *  @author Nathalia Ruiz
 */
public interface IRestaurantRepository {
    
    List<Restaurant> findAll();

    List <Restaurant> findByAdmin(String nameAdmin);
    
    Restaurant findByName (String name);
    
    Restaurant findByNit(String nit);

    boolean create(Restaurant newRestaurant);

    boolean update(Restaurant newRestaurant);

    boolean delete(String nit);
    
}

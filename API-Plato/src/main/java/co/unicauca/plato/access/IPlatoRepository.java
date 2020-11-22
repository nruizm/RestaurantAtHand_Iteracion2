
package co.unicauca.plato.access;

import co.unicauca.plato.domain.entity.Dish;
import java.util.List;

/**
 *
 * @author Mannuel Fernando Granoble
 *         Michel Andrea Gutierrez Vallejo
 *         Ximena Quijano Gutierrez
 *         Nathalia Ruiz Menses
 */
public interface IPlatoRepository 
{
    List<Dish> findAll();

    Dish findByIdDish(String prmIdDish);

    boolean create(Dish prmNewDish);

    boolean update(Dish prmNewDish);

    boolean delete(String prmIdDish);
    
}

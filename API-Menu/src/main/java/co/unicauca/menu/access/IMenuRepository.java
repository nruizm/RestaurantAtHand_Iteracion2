package co.unicauca.menu.access;

import co.unicauca.menu.domain.entity.Menu;
import java.util.List;

/**
 *
 * @author Michelle Vallejo
 */
public interface IMenuRepository
{
    List<Menu> findAll();

    Menu findByMenuId(String prmIdMenu);
    
    List<Menu> findByIdRest(String prmIdRest);

    boolean create(Menu prmNewMenu);

    boolean update(Menu prmNewMenu);

    boolean delete(String prmIdMenu);
    
    boolean createVisualizacion(String prmIdMenu, String prmDia);
    
    boolean createOfrece(String prmIdMenu, String prmIdPlato);
    
    List<String> listarDia(String prmIdMenu);
    
    List<String> listarPlato(String prmIdMenu);
    
    boolean deleteVisualizacion (String prmIdMenu, String prmDia);
    
    boolean deleteOfrece (String prmIdMenu, String prmIDPlato);
}

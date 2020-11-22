package co.unicauca.menu.domain.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *  Menu de un Restaurante 
 * 
 * @author Mannuel Fernando Granoble
 *         Michel Andrea Gutierrez Vallejo
 *         Ximena Quijano Gutierrez
 *         Nathalia Ruiz Menses
 * 
 */
public class Menu 
{
    //
    private String atrIdMenu;
    private String atrNomMenu;
    private String atrIdRest;
    private List<String> atrDiasVisualizacion;
    private List<String> atrIdPlatos;
    

    public Menu(String atrIdMenu, String atrNomMenu, String atrIdRest) 
    {
        this.atrIdMenu = atrIdMenu;
        this.atrNomMenu = atrNomMenu;
        this.atrIdRest = atrIdRest;
        atrDiasVisualizacion = new ArrayList<String>();
        atrIdPlatos = new ArrayList<String>();
    }

    public Menu() {
        atrDiasVisualizacion = new ArrayList<String>();
        atrIdPlatos = new ArrayList<String>();
    }

    public String getAtrIdMenu() {
        return atrIdMenu;
    }

    public void setAtrIdMenu(String atrIdMenu) {
        this.atrIdMenu = atrIdMenu;
    }

    public String getAtrNomMenu() {
        return atrNomMenu;
    }

    public void setAtrNomMenu(String atrNomMenu) {
        this.atrNomMenu = atrNomMenu;
    }

    public String getAtrIdRest() {
        return atrIdRest;
    }

    public void setAtrIdRest(String atrIdRest) {
        this.atrIdRest = atrIdRest;
    }
    
    public void setAtrDiasVisualizacion (String dia){
        this.atrDiasVisualizacion.add(dia);
    }
    
    public List<String> getAtrDiasVisualizacion()
    {
        return atrDiasVisualizacion;
    }
    public void setAtrIdPlatos(String plato)
    {
        this.atrIdPlatos.add(plato);
    }
    
    public List<String> getAtrIdPlatos()
    {
        return atrIdPlatos;
    }
}

package co.unicauca.restaurante.domain.entity;

/**
 *
 *  @author Mannuel Fernando Granoble
 *         Michel Andrea Gutierrez Vallejo
 *         Ximena Quijano Gutierrez
 *         Nathalia Ruiz Menses
 */
public class Restaurant 
{
    //Nit
    private String atrNitRest;
    //Nombre
    private String atrNameRest;
    //Direccion
    private String atrAddressRest;
    //Telefono
    private String atrMobileRest;
    //Correo
    private String atrEmailRest;
    //Ciudad
    private String atrCityRest;
    
    private String atrAdmiRest;
   
    public Restaurant(){
    }

    public Restaurant(String atrNitRest, String atrNameRest, String atrAddressRest, String atrMobileRest, String atrEmailRest, String atrCityRest) {
        this.atrNitRest = atrNitRest;
        this.atrNameRest = atrNameRest;
        this.atrAddressRest = atrAddressRest;
        this.atrMobileRest = atrMobileRest;
        this.atrEmailRest = atrEmailRest;
        this.atrCityRest = atrCityRest;
    }

    public Restaurant(String atrNitRest, String atrNameRest, String atrAddressRest, String atrMobileRest, String atrEmailRest, String atrCityRest, String atrAdmiRest) {
        this.atrNitRest = atrNitRest;
        this.atrNameRest = atrNameRest;
        this.atrAddressRest = atrAddressRest;
        this.atrMobileRest = atrMobileRest;
        this.atrEmailRest = atrEmailRest;
        this.atrCityRest = atrCityRest;
        this.atrAdmiRest = atrAdmiRest;
    }

    public String getAtrCityRest() {
        return atrCityRest;
    }

    public void setAtrCityRest(String atrCityRest) {
        this.atrCityRest = atrCityRest;
    }

    public String getAtrAdmiRest() {
        return atrAdmiRest;
    }

    public void setAtrAdmiRest(String atrAdmiRest) {
        this.atrAdmiRest = atrAdmiRest;
    }
    
    
    


    public String getAtrNitRest() 
    {
        return atrNitRest;
    }

    public void setAtrNitRest(String atrNitRest)
    {
        this.atrNitRest = atrNitRest;
    }

    public String getAtrNameRest() 
    {
        return atrNameRest;
    }

    public void setAtrNameRest(String atrNameRest)
    {
        this.atrNameRest = atrNameRest;
    }

    public String getAtrAddressRest() 
    {
        return atrAddressRest;
    }

    public void setAtrAddressRest(String atrAddressRest) 
    {
        this.atrAddressRest = atrAddressRest;
    }

    public String getAtrMobileRest() 
    {
        return atrMobileRest;
    }

    public void setAtrMobileRest(String atrMobileRest) 
    {
        this.atrMobileRest = atrMobileRest;
    }

    public String getAtrEmailRest()
    {
        return atrEmailRest;
    }

    public void setAtrEmailRest(String atrEmailRest) 
    {
        this.atrEmailRest = atrEmailRest;
    }
/*
    public Admin getAtrAdminRest()
    {
        return atrAdminRest;
    }

    public void setAtrAdminRest(Admin atrAdminRest) 
    {
        this.atrAdminRest = atrAdminRest;
    }
    
 */   
    
    
}

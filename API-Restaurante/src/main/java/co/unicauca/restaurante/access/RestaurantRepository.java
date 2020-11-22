package co.unicauca.restaurante.access;

import co.unicauca.restaurante.domain.entity.Restaurant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Es una implementación que tiene libertad de hacer una implementación del
 * contrato. Se utiliza postgres, importante: para que funcione la aplicación, 
 * se debe copiar manualmente la librería de maven de netbeans postgresql-42.2.8.jar, 
 * y pegarla en el directorio donde se haya instalado Payara
 * 
 * @author Nathalia Ruiz 
 */
public class RestaurantRepository implements IRestaurantRepository{

    private Connection conn;
    
    public RestaurantRepository (){
        initDatabase();
    }
    
    
    @Override
    public List<Restaurant> findAll() {
       List<Restaurant> restaurants = new ArrayList<>();
           try {

            String sql = "SELECT * FROM restaurante";
            this.connect();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Restaurant newRestaurant = new Restaurant();
                newRestaurant.setAtrNitRest(rs.getString("nit"));
                newRestaurant.setAtrNameRest(rs.getString("name"));
                newRestaurant.setAtrAddressRest(rs.getString("address"));
                newRestaurant.setAtrMobileRest(rs.getString("mobile"));
                newRestaurant.setAtrEmailRest(rs.getString("email"));
                newRestaurant.setAtrCityRest(rs.getString("city"));
                newRestaurant.setAtrAdmiRest(rs.getString("userNameAdmin"));
                
                restaurants.add(newRestaurant);
            }
            this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return restaurants;
       
    }

    
    @Override
    public Restaurant findByNit(String nit) {
        Restaurant newRestaurant = null;
        try {

            String sql = "SELECT * FROM restaurante where nit ='"+nit+"';";
            this.connect();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
           
            if (rs.next()) {
            newRestaurant = new Restaurant();
            newRestaurant.setAtrNitRest(rs.getString("nit"));
            newRestaurant.setAtrNameRest(rs.getString("name"));
            newRestaurant.setAtrAddressRest(rs.getString("address"));
            newRestaurant.setAtrMobileRest(rs.getString("mobile"));
            newRestaurant.setAtrEmailRest(rs.getString("email"));
            newRestaurant.setAtrCityRest(rs.getString("city"));
            newRestaurant.setAtrAdmiRest(rs.getString("userNameAdmin"));
            
            }  
            this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newRestaurant;
    }
    
    @Override
    public List<Restaurant> findByAdmin(String nameAdmin) {
        List<Restaurant> restaurants = new ArrayList<>();
           try {

            String sql = "SELECT * FROM restaurante where userNameAdmin='"+nameAdmin+"'";
            this.connect();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Restaurant newRestaurant = new Restaurant();
                newRestaurant.setAtrNitRest(rs.getString("nit"));
                newRestaurant.setAtrNameRest(rs.getString("name"));
                newRestaurant.setAtrAddressRest(rs.getString("address"));
                newRestaurant.setAtrMobileRest(rs.getString("mobile"));
                newRestaurant.setAtrEmailRest(rs.getString("email"));
                newRestaurant.setAtrCityRest(rs.getString("city"));
                newRestaurant.setAtrAdmiRest(rs.getString("userNameAdmin"));
                
                restaurants.add(newRestaurant);
            }
            this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return restaurants;
       
    }

    @Override
    public Restaurant findByName(String name) {
        Restaurant restaurant = null;
        try {

            String sql = "SELECT * FROM restaurante Where name='" + name +"'";
            this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                restaurant = new Restaurant();
                restaurant.setAtrNitRest(rs.getString("nit"));
                restaurant.setAtrNameRest(rs.getString("name"));
                restaurant.setAtrAddressRest(rs.getString("address"));
                restaurant.setAtrMobileRest(rs.getString("mobile"));
                restaurant.setAtrEmailRest(rs.getString("email"));
                restaurant.setAtrCityRest(rs.getString("city"));
                restaurant.setAtrAdmiRest(rs.getString("userNameAdmin"));
            }
            this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepository.class.getName()).log(Level.SEVERE, "Error al buscar el producto en la base de datos", ex);
        }
        return restaurant;
    }

    @Override
    public boolean create(Restaurant newRestaurant) {
         String sql="";
        try {
            this.connect();

            sql = "INSERT INTO restaurante ( nit, name, address,mobile,email,city,userNameAdmin ) "
                    + "VALUES ( ?, ?, ?,?, ?, ?, ? )";

            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newRestaurant.getAtrNitRest());
            pstmt.setString(2, newRestaurant.getAtrNameRest());
            pstmt.setString(3, newRestaurant.getAtrAddressRest());
            pstmt.setString(4, newRestaurant.getAtrMobileRest());
            pstmt.setString(5, newRestaurant.getAtrEmailRest());
            pstmt.setString(6, newRestaurant.getAtrCityRest());
            pstmt.setString(7, newRestaurant.getAtrAdmiRest());
           
            pstmt.executeUpdate();
            this.disconnect();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepository.class.getName()).log(Level.SEVERE, "Error en el insert into: " + sql, ex);
        }
        return false;
    }

    @Override
    public boolean update(Restaurant newRestaurant) {
        try {
            this.connect();

            String sql = "UPDATE restaurante "
                    + "SET "
                    + "name          = ?,"
                    + "address       = ?,"
                    + "mobile        = ?,"
                    + "email         = ?,"
                    + "city          = ?,"
                    + "userNameAdmin = ? "
                    + "WHERE nit = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newRestaurant.getAtrNameRest());
            pstmt.setString(2, newRestaurant.getAtrAddressRest());
            pstmt.setString(3, newRestaurant.getAtrMobileRest());
            pstmt.setString(4, newRestaurant.getAtrEmailRest());
            pstmt.setString(5, newRestaurant.getAtrCityRest());
            pstmt.setString(6, newRestaurant.getAtrAdmiRest());
            pstmt.setString(7, newRestaurant.getAtrNitRest());
            pstmt.executeUpdate();
            this.disconnect();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepository.class.getName()).log(Level.SEVERE, "Error al actualizar el producto", ex);
        }
        return false;
    }

    @Override
    public boolean delete(String nit) {
        try {

            String sql = "DELETE FROM restaurante "
                    + "WHERE nit ='"+nit+"'";

            this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            this.disconnect();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepository.class.getName()).log(Level.SEVERE, "Error al eliminar producto", ex);
        }
        return false;
    }

    /**
     *  Crea la tabla Restaurant si no existe, se inicializa la tabla con un registro
     */
    private void initDatabase() {
        //SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS restaurante ("
                        + "nit varchar(60) PRIMARY KEY," 
                        + "name        varchar(60) NOT NULL,"
                        + "address      varchar(60) not null," 
                        + "mobile  varchar(60) not null," 
                        + "email  varchar(60) not null," 
                        + "city     varchar(60) not null," 
                        + "userNameAdmin   varchar(60) not null" 
                        + "); ";
        
        try {
            this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            // Datos de inicialización
            
            if(this.findByNit("111")==null){
             stmt.execute("INSERT INTO restaurante(nit, name, address,mobile,email,city,userNameAdmin) "
                    + "values('111','Cosecha','Norte','8212341','cosecha@hotmail.com','popayan','mfgranoble');");
                
            }
            this.disconnect();
        } catch (SQLException ex){
            Logger.getLogger(RestaurantRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Conectar a la bd
     */
    public void connect(){
        try {
            
            String url = "jdbc:postgresql://localhost:5432/restauranteBD";
            String usuario = "postgres";
            String contrasenia = "system";
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, usuario, contrasenia);
           
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RestaurantRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Desconecta de la base de datos
     */
    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepository.class.getName()).log(Level.SEVERE, "Error al cerrar conexión de la base de datos", ex);
        }

    }

    
}

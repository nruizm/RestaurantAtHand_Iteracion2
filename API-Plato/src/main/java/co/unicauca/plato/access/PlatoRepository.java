package co.unicauca.plato.access;

import co.unicauca.plato.domain.entity.Dish;
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
import javax.enterprise.inject.Default;
/**
 * Es una implementación que tiene libertad de hacer una implementación del
 * contrato. Se utiliza postgres, importante: para que funcione la aplicación, 
 * se debe copiar manualmente la librería de maven de netbeans postgresql-42.2.8.jar, 
 * y pegarla en el directorio donde se haya instalado Payara
 * 
 * @author MANNUEL
 */
@Default
public class PlatoRepository implements IPlatoRepository
{
    private Connection conn;
    
    public PlatoRepository()
    {
        initDatabase();
    }

    @Override
    public List<Dish> findAll()
    {
      List<Dish> dishs = new ArrayList<>();
      try{
          String sql = "SELECT * FROM plato";
            this.connect();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Dish newDish = new Dish();
                newDish.setAtrIdDish(rs.getString("ID"));
                newDish.setAtrNameDish(rs.getString("NAME"));
                newDish.setAtrPriceDish(rs.getString("PRECIO"));
                newDish.setAtrCategoriaDish(rs.getString("CATEGORIA"));
                newDish.setAtrDescriptionDish(rs.getString("DESCRIPCION"));
                newDish.setAtrTypeDish(rs.getString("TIPOPLATO"));
                
                dishs.add(newDish);
            }
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(PlatoRepository.class.getName()).log(Level.SEVERE,null,ex);
        }
        return dishs;
      
    }

    @Override
    public Dish findByIdDish(String prmIdDish) {
       
        Dish dish = null;
        try {

            String sql = "SELECT * FROM plato Where ID='" + prmIdDish+"'";
            this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                dish = new Dish();
                dish.setAtrIdDish(rs.getString("ID"));
                dish.setAtrNameDish(rs.getString("NAME"));
                dish.setAtrPriceDish(rs.getString("PRECIO"));
                dish.setAtrCategoriaDish(rs.getString("CATEGORIA"));
                dish.setAtrDescriptionDish(rs.getString("DESCRIPCION"));
                dish.setAtrTypeDish(rs.getString("TIPOPLATO"));
            }
            this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(PlatoRepository.class.getName()).log(Level.SEVERE,"Error al buscar el producto en la base de datos",ex);
            }
        return dish;
    }

    @Override
    public boolean create(Dish prmNewDish) {
                String sql="";
        try {
            this.connect();

            sql = "INSERT INTO plato ( ID,NAME,PRECIO,CATEGORIA,DESCRIPCION,TIPOPLATO) "
                    + "VALUES ( ?, ?, ?, ?, ?, ? )";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, prmNewDish.getAtrIdDish());
            pstmt.setString(2, prmNewDish.getAtrNameDish());
            pstmt.setString(3, prmNewDish.getAtrPriceDish());
            pstmt.setString(4, prmNewDish.getAtrCategoriaDish());
            pstmt.setString(5, prmNewDish.getAtrDescriptionDish());
            pstmt.setString(6, prmNewDish.getAtrTypeDish());
            pstmt.executeUpdate();
            this.disconnect();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PlatoRepository.class.getName()).log(Level.SEVERE,"Error en el insert into: " + sql, ex);
            
        }
        return false;
    }


    @Override
    public boolean update(Dish prmNewDish){
        
         try {
            this.connect();

            String sql = "UPDATE plato "
                    + "SET NAME = ? ,"
                    + "PRECIO = ? ,"
                    + "CATEGORIA = ? ,"
                    + "DESCRIPCION = ? ,"
                    + "TIPOPLATO = ? "
                    + "WHERE ID = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, prmNewDish.getAtrNameDish());
            pstmt.setString(2, prmNewDish.getAtrPriceDish());
            pstmt.setString(3, prmNewDish.getAtrCategoriaDish());
            pstmt.setString(4, prmNewDish.getAtrDescriptionDish());
            pstmt.setString(5, prmNewDish.getAtrTypeDish());
            pstmt.setString(6, prmNewDish.getAtrIdDish());
            pstmt.executeUpdate();
            this.disconnect();
            return true;
        } catch (SQLException ex) 
        {
            
            Logger.getLogger(PlatoRepository.class.getName()).log(Level.SEVERE, "Error al actualizar el producto", ex);
        }
        return false;
    
    }

    @Override
    public boolean delete(String prmIdDish) {
         try {

            String sql = "DELETE FROM plato "
                    + "WHERE ID = ?";
            this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, prmIdDish);
            pstmt.executeUpdate();
            this.disconnect();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PlatoRepository.class.getName()).log(Level.SEVERE, "Error al eliminar producto", ex);
        }
        return false;
    }
    
        
 private void initDatabase()
{
        //SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS plato ("
                        + "ID        varchar(60) PRIMARY KEY,"
                        + "NAME      varchar(60) not null," 
                        + "PRECIO  varchar(60) not null," 
                        + "CATEGORIA  varchar(60) not null," 
                        + "DESCRIPCION      varchar(60) not null," 
                        + "TIPOPLATO   varchar(60) not null"  
                        + "); ";
        
        try {
            this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);// Datos de inicialización
            if(this.findByIdDish("123")==null){
            stmt.execute("INSERT INTO plato(ID, NAME,PRECIO,CATEGORIA,DESCRIPCION,TIPOPLATO) "
                    + "values('123','Pizza','20000','Platos fuertes','Tres carnes','Especial');");
            }
            this.disconnect();
        } catch (SQLException ex){
            Logger.getLogger(PlatoRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Conectar a la bd
     */
    public void connect(){
        try {
            
            String url = "jdbc:postgresql://localhost:5432/platoBD";
            String usuario = "postgres";
            String contrasenia = "system";
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, usuario, contrasenia);
           
        } catch (SQLException | ClassNotFoundException ex) {
           
            Logger.getLogger(PlatoRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlatoRepository.class.getName()).log(Level.SEVERE, "Error al cerrar conexión de la base de datos", ex);
        }

    }

}

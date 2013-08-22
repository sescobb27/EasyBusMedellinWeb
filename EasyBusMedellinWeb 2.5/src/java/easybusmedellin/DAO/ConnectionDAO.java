package easybusmedellin.DAO;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConnectionDAO {
    
    private static Connection con;
    
    
    /**
     * Retorna la conexion a base de datos.
     * @return Connection
     */
    public static Connection connectDataBase(){
        if (con == null){
            return getConexion();
        }else{
            return con;
        }
    }
    /**
     * Crea una insancia de la conexion de la base de datos.
     * @return Connection
     */
    private static Connection getConexion(){
        try{
            String CLASSFORNAME = "com.mysql.jdbc.Driver";
            String JDBC = "jdbc:mysql://localhost/easybusmedellin";
            String PASSWORD = "1128443312";
            String USER = "root";
            Class.forName(CLASSFORNAME);
            con = DriverManager.getConnection(JDBC, USER, PASSWORD);
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException cl_ex){
            System.out.println(cl_ex.getMessage());
        } finally {
            return con;
        }
            
    }
    
    /**
     * Cierra la conexion a la base de datos.
     */
    public static void closeConexion(){
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package easybusmedellinadministrador.DAO;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author simon
 */
public class ConnectionDao {
    
    private static Connection con = null;
    
    public static Connection connectDataBase(){
        if (con == null){
             ConnectionDao conexion = new ConnectionDao();
        }
        return con;
    }
    
    private ConnectionDao(){
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
        }   
    }
    
    public static void closeConexion(){
        try {
            con.close();
            con=null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
}

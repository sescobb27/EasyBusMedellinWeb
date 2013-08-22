/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package easybusmedellinadministrador.DAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Edgardo
 */
public class TipoDAO {
    
    private Connection con;
    private CallableStatement cs;
    
    public void agregarTipo(String tipo) throws SQLException{
        try {
            con = ConnectionDao.connectDataBase();
            cs = con.prepareCall("CALL `easybusmedellin`.`agregarTipo`(?)");
            cs.setString(1,tipo);
            cs.execute();
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
        } finally {
            try {
                cs.close();
                ConnectionDao.closeConexion();
                con.close();
            } catch (SQLException sqlex) {
                System.out.println(sqlex.getMessage());
            }
        }
    }
    
    public LinkedList<String> tiposTransporte() throws SQLException
    {
        LinkedList<String> tipos = new LinkedList<String>();
        try {
            con = ConnectionDao.connectDataBase();
            cs = con.prepareCall("CALL `easybusmedellin`.`tipos`()");
            cs.execute();
            ResultSet rs = cs.getResultSet();
            while(rs.next()){
                tipos.add(rs.getString(1));
            }        
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            return tipos;
        } finally {
            try {
                cs.close();
                ConnectionDao.closeConexion();
                con.close();
                return tipos;
            } catch (SQLException sqlex) {
                System.out.println(sqlex.getMessage());
                return tipos;
            }
        }
    }
    
    public String getTipo(int id)
    {
        String tipo = "";
         try{
            con = ConnectionDao.connectDataBase();
            cs = con.prepareCall("CALL `easybusmedellin`.`consultarTipo`(?)");
            cs.setInt(1,id);
            cs.execute();
            ResultSet rs = cs.getResultSet();
            while(rs.next())
            {
               tipo = rs.getString(1); 
            }
            return tipo;
        }catch(SQLException sqlex){
            System.out.println(sqlex.getMessage());
            return null;
        }finally {
            try {
                cs.close();
                return tipo;
            } catch (SQLException sqlex) {
                System.out.println(sqlex.getMessage());
            }
        } 
        
    }
}

package easybusmedellin.DAO;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.util.LinkedList;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RutaDAO {
    
    private Connection con;
    private CallableStatement cs;
    
    /**
     * Trae de la base de datos el Id de los transportes que pasen dentro del rango.
     * @param latitud
     * @param longitud
     * @param rango
     * @param sector
     * @return the id_transporte
     */
    public LinkedList <Integer> rutasEnRango(double latitud, double longitud, double rango,int sector){
        LinkedList<Integer> id_transporte = new LinkedList<Integer>();
        try{
            con = ConnectionDAO.connectDataBase();
            cs = con.prepareCall("CALL `EasyBusMedellin`.`encontrarRuta`(?,?,?,?,?)");
            cs.setDouble(1, latitud + rango);
            cs.setDouble(2, longitud +rango);
            cs.setDouble(3, latitud - rango);
            cs.setDouble(4, longitud -rango);
            cs.setInt(5, sector);
            cs.execute();
            ResultSet rs = cs.getResultSet();
                
            while(rs.next()){
                id_transporte.add(rs.getInt(1));
            }
            
        }catch(SQLException sqlex){
            System.out.println(sqlex.getMessage());
            
        }finally {
            try {
                cs.close();
                return id_transporte;
            } catch (SQLException sqlex) {
                System.out.println(sqlex.getMessage());
                return id_transporte;
            }
        }
    }
     
    /**
     * Trae de la base de datos los sectores por los que pasa una ruta.
     * @param id
     * @return the sector
     */
    public LinkedList <Integer> consultarSectoresRuta(int id){
        LinkedList<Integer> sector = new LinkedList<Integer>();
        try{
            con = ConnectionDAO.connectDataBase();
            cs = con.prepareCall("CALL `EasyBusMedellin`.`sectoresRuta`(?)");
            cs.setDouble(1,id);
            cs.execute();
            ResultSet rs = cs.getResultSet();
            while(rs.next()){
                sector.add(rs.getInt(1));
            }
        }catch(SQLException sqlex){
            System.out.println(sqlex.getMessage());
        }finally {
            try {
                cs.close();
                return sector;
            } catch (SQLException sqlex) {
                System.out.println(sqlex.getMessage());
                return sector;
            }
        }
    }   
}

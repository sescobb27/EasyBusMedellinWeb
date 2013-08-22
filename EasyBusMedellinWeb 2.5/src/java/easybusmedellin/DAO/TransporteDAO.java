
package easybusmedellin.DAO;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.util.LinkedList;
import easybusmedellin.Model.Transporte;
import easybusmedellin.Model.Ruta;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransporteDAO {
    
    private Connection con;
    private RutaDAO rutaDao;
    private CallableStatement cs;
    
    public TransporteDAO()
    {
        rutaDao = new RutaDAO();
    }
    
    /**
     * Trae de la base de datos las coordenadas y sectores de un Transporte.
     * @param id_transporte
     * @return the Transporte
     */ 
    public Transporte seleccionarTransporte(int id_transporte)
    {
        LinkedList<Integer> sectores = new LinkedList<Integer>();
        LinkedList<LinkedList<Double>> coordenadas = new LinkedList<LinkedList<Double>>();
        coordenadas.add(new LinkedList<Double>());
        coordenadas.add(new LinkedList<Double>());
        coordenadas.add(new LinkedList<Double>());     
        
        try{
            con = ConnectionDAO.connectDataBase();
            cs = con.prepareCall("CALL `EasyBusMedellin`.`seleccionarRuta`(?)");
            cs.setDouble(1,id_transporte);
            cs.execute();
            ResultSet rs = cs.getResultSet();
            while(rs.next())
            {
                coordenadas.get(0).add(rs.getDouble(1));
                coordenadas.get(1).add(rs.getDouble(2));
                coordenadas.get(2).add(rs.getDouble(3));  
                sectores.add(rs.getInt(4));
            }
            Transporte transporte = crearTransporte(id_transporte);
            if(transporte.getTipo().equals("Metro")){
                transporte.setRuta(new Ruta(coordenadas,sectores));
                transporte.getRuta().setEstaciones(consultarEstaciones(id_transporte));
            }else{
                transporte.setRuta(new Ruta(coordenadas,sectores));
            }
            return transporte; 
        }catch(SQLException sqlex){
            System.out.println(sqlex.getMessage());
            return null;
        }finally {
            try {
                cs.close();
            } catch (SQLException sqlex) {
                System.out.println(sqlex.getMessage());
            }
        }
        
    }
    
    /**
     * Trae de la base de datos los nombres de las estaciones de un Transporte tipo Metro.
     * @param id
     * @return the Estaciones
     */
    public LinkedList<String> consultarEstaciones(int id)
    {
        LinkedList<String> Estaciones = new LinkedList<String>();
        try{
            con = ConnectionDAO.connectDataBase();
            cs = con.prepareCall("CALL `EasyBusMedellin`.`consultarEstaciones`(?)");
            cs.setDouble(1,id);
            cs.execute();
            ResultSet rs = cs.getResultSet();
            while(rs.next())
            {
                Estaciones.add(rs.getString(1));
            }
            return Estaciones;
        }catch(SQLException sqlex){
            System.out.println(sqlex.getMessage());
            return null;
        }finally {
            try {
                cs.close();
            } catch (SQLException sqlex) {
                System.out.println(sqlex.getMessage());
            }
        }
    }
    
    /**
     * Trae delabase de datos la informacion basica de un transporte como
     * nombre,id,precio,tipo.
     * @param id
     * @return the Transporte
     */
    public Transporte crearTransporte(int id)
    {
        try{
            con = ConnectionDAO.connectDataBase();
            cs = con.prepareCall("CALL `EasyBusMedellin`.`seleccionarBus`(?)");
            cs.setDouble(1,id);
            cs.execute();
            ResultSet rs = cs.getResultSet();
            Transporte transporte = null;
            while(rs.next())
            {
               transporte = new Transporte(rs.getString(1),rs.getInt(2),id,getTipo(rs.getInt(3))); 
            }
            return transporte; 
        }catch(SQLException sqlex){
            System.out.println(sqlex.getMessage());
            return null;
        }finally {
            try {
                cs.close();
            } catch (SQLException sqlex) {
                System.out.println(sqlex.getMessage());
            }
        } 
    }
    
    /**
     * Devuelve el nombre de un tipo.
     * @param id
     * @return the tipo.
     */
    public String getTipo(int id)
    {
        String tipo = "";
         try{
            con = ConnectionDAO.connectDataBase();
            cs = con.prepareCall("CALL `EasyBusMedellin`.`consultarTipo`(?)");
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
    /**
     * Retorna el id el tipo de un transporte.
     * @param id
     * @return tipo
     */
    public int getIdTipo(int id)
    {
        int tipo = 0;
         try{
            con = ConnectionDAO.connectDataBase();
            cs = con.prepareCall("CALL `EasyBusMedellin`.`consultarIdTipo`(?)");
            cs.setInt(1,id);
            cs.execute();
            ResultSet rs = cs.getResultSet();
            while(rs.next())
            {
               tipo = rs.getInt(1); 
            }
            return tipo;
        }catch(SQLException sqlex){
            System.out.println(sqlex.getMessage());
            return tipo;
        }finally {
            try {
                cs.close();
                return tipo;
            } catch (SQLException sqlex) {
                System.out.println(sqlex.getMessage());
            }
        } 
    }
    
    /**
     * Trae de la base de datos los sectores por donde pasa un Transporte.
     * @param id_transporte
     * @return the sectores
     */
    public LinkedList <Integer> consultarSectoresRuta(int id_transporte){
        LinkedList<Integer> sectores = rutaDao.consultarSectoresRuta(id_transporte);
        return sectores;   
    }
    
    
    /**
     * Trae de la base de datos el Id de los transportes que pasen dentro del rango.
     * @param latitud
     * @param longitud
     * @param rango
     * @param sector
     * @return the id_transporte
     */
    public LinkedList <Integer> rutasEnRango(double latitud, double longitud, double rango,int sector){
        LinkedList id_transporte = rutaDao.rutasEnRango(latitud,longitud ,rango,sector);
        return id_transporte;     
    }
    /**
     * Retorna el nombre de un Transporte.
     * @param id_transporte
     * @return nombre
     */
    public String nombreTransporte(int id_transporte)
    {
       String nombre = "";
         try{
            con = ConnectionDAO.connectDataBase();
            cs = con.prepareCall("CALL `EasyBusMedellin`.`consultarNombreTransporte`(?)");
            cs.setInt(1,id_transporte);
            cs.execute();
            ResultSet rs = cs.getResultSet();
            while(rs.next())
            {
               nombre = rs.getString(1); 
            }
            return nombre;
        }catch(SQLException sqlex){
            System.out.println(sqlex.getMessage());
            return null;
        }finally{
            try{
                cs.close();
                return nombre;
            }catch (SQLException sqlex) {
                System.out.println(sqlex.getMessage());
            }
        } 
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package easybusmedellinadministrador.DAO;
import easybusmedellinadministrador.Model.TransporteModel;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author simon
 */
public class TransporteDao{
    
    private Connection con;
    private CallableStatement cs;
    private final int LONGITUD = 1;
    private final int LATITUD = 0;
    private final int COORDENADAS = 1;
    
    public void agregarBus(TransporteModel bus) throws SQLException{
        try {
            con = ConnectionDao.connectDataBase();
            cs = con.prepareCall("CALL `easybusmedellin`.`agregarBuses`(?,?,?,?)");
            cs.setString(1, bus.getName());
            cs.setDouble(2, bus.getPrecio());
            cs.setInt(4, bus.getTipo());
            cs.execute();
                bus.getRuta().setId_bus(cs.getInt(3));
                agregarRuta(bus);
            if(!bus.getRuta().getEstaciones().isEmpty()){
                agregarEstaciones(bus);
            }
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
    
    public void agregarEstaciones(TransporteModel bus)
    {
        try {
            cs = con.prepareCall("CALL `easybusmedellin`.`agregarEstaciones`(?,?)");
            for (int indice = 0; indice < bus.getRuta().getEstaciones().size(); ++indice) 
            {
                cs.setInt(1,bus.getRuta().getId_bus());
                cs.setString(2, bus.getRuta().getEstaciones().get(indice));
                cs.execute();
            }
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
        }
    }
    
    private void agregarRuta(TransporteModel bus)throws SQLException{
        try {
            cs = con.prepareCall("CALL `easybusmedellin`.`agregarRuta`(?,?,?,?,?)");
            for (int indice = 0; indice < bus.getRuta().getCoordenadas().get(0).size(); ++indice) 
            {
                cs.setInt(1,bus.getRuta().getId_bus());
                cs.setDouble(2, bus.getRuta().getCoordenadas().get(LATITUD).get(indice));
                cs.setDouble(3, bus.getRuta().getCoordenadas().get(LONGITUD).get(indice));
                cs.setInt(4, indice);
                cs.setInt(5, bus.getRuta().getSectores().get(indice));
                cs.execute();
            }
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
        }
    }
}

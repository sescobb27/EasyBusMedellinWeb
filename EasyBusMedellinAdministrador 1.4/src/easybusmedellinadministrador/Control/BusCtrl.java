/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package easybusmedellinadministrador.Control;

import easybusmedellinadministrador.DAO.TransporteDao;
import easybusmedellinadministrador.Model.TransporteModel;
import java.sql.SQLException;

/**
 *
 * @author Edgardo
 */
public class BusCtrl {
    
    private TransporteDao busDB;
    private TransporteModel bus;
    
    public BusCtrl(TransporteModel bus)
    {
        this.bus = bus;
        busDB = new TransporteDao();
        enviarBus();
    }
    
    private void enviarBus()
    {
        try{
            busDB.agregarBus(bus);
        }catch(SQLException ex){
            escribirExcepciones(ex.getMessage());
        }
    }
    
    private void escribirExcepciones(String ex){
        System.out.println(ex);
    } 
}

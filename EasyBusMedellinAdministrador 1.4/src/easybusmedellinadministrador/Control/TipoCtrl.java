/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package easybusmedellinadministrador.Control;

import easybusmedellinadministrador.DAO.TipoDAO;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Edgardo
 */
public class TipoCtrl {
    
    private TipoDAO tipo;

    public TipoCtrl()
    {
        tipo = new TipoDAO();
    }
    
    public void insertarTipo(String name)throws Exception
    {
        try{
            tipo.agregarTipo(name);
        }catch(SQLException ex){
            escribirExcepciones(ex.getMessage());
        }
    }
    
    public LinkedList<String> tipos()
    {
        try {
            return tipo.tiposTransporte();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public String getTipo(int id)
    {
        return tipo.getTipo(id);
    }
    
    private void escribirExcepciones(String ex){
        System.out.println(ex);
    } 
}

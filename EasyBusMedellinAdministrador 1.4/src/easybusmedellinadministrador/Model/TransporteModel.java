/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package easybusmedellinadministrador.Model;

/**
 *
 * @author simon
 */
public class TransporteModel{
    private int precio;
    private int tipo;
    private String name;
    private RutaModel ruta;

    public TransporteModel(int precio, String name,int tipo) {
        this.precio = precio;
        this.name = name;
        this.tipo = tipo;
    }

    /**
     * @return the precio
     */
    public int getPrecio() {
        return precio;
    }
    
    public int getTipo() {
        return tipo;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }
    
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the ruta
     */
    public RutaModel getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(RutaModel ruta) {
        this.ruta = ruta;
    }
}

package easybusmedellin.Model;

import java.util.LinkedList;


public class RutaFinal{
    
    private LinkedList<Transporte> rutaFinal;
    
    
    public RutaFinal()
    {
        rutaFinal = new LinkedList<Transporte>(); 
    }
    
    /**
     * agrega un Transporte a la lista de Transportes
     * @param transporte 
     */
    public void agregarTransporteTramo(Transporte transporte)
    {
        rutaFinal.add(transporte);
    } 
    
    /**
     * @return the rutafinal
     */
    public LinkedList<Transporte> getRutaFinal()
    {
        return rutaFinal;
    }
    
    /**
     * @param Ruta the Ruta to set
     */
    public void setRutafinal(LinkedList<Transporte> Ruta)
    {
        rutaFinal = Ruta;
    }
}

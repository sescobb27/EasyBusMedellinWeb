
package easybusmedellin.Model;

import java.util.LinkedList;


public class RutasSolucion {
    
    private LinkedList<RutaFinal> rutasRespuesta;
    
    public RutasSolucion() 
    {
        rutasRespuesta = new LinkedList<RutaFinal>();
    }
    
    /**
     * Agrega una RutaFinal a la lista de RutaFinal
     * @param ruta 
     */
    public void agregarRutaFinal(RutaFinal ruta)
    {
        rutasRespuesta.add(ruta);
    }
    
    /**
     * @return the rutasRespuesta
     */
    public LinkedList<RutaFinal> getRutaSolucion()
    {
        return rutasRespuesta;
    }
    
    /**
     * @param rutasRespuesta the rutasRespuesta to set
     */ 
    public void setRutaSolucion(LinkedList<RutaFinal> rutasRespuesta)
    {
        this.rutasRespuesta = rutasRespuesta;
    }
}

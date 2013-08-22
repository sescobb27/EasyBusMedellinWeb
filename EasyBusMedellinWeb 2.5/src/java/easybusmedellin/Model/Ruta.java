package easybusmedellin.Model;

import java.util.LinkedList;

public class Ruta {
    
    private LinkedList<LinkedList<Double>> coordenadas;
    private LinkedList<Integer> sectores;
    private LinkedList<String> estaciones;

    public Ruta(LinkedList<LinkedList<Double>> coordenadas,LinkedList<Integer> sectores) {
        this.coordenadas = coordenadas;     
        this.sectores = sectores;
        estaciones = new LinkedList<String>();
    }

    /**
     * @return the coordenadas
     */
    public LinkedList<LinkedList<Double>> getCoordenadas() {
        return coordenadas;
    }
    
    /**
     * @param coordenadas the coordenadas to set
     */
    public void setCoordenadas(LinkedList<LinkedList<Double>> coordenadas) {
        this.coordenadas = coordenadas;
    }
    
    /**
     * @return the sectores
     */
    public LinkedList<Integer> getSectores() {
        return sectores;
    } 
    
    /**
     * @param sectores the sectores to set
     */
    public void setSectores(LinkedList<Integer> sectores) {
        this.sectores = sectores;
    } 
    /**
     * 
     * @return the estaciones
     */
    public LinkedList<String> getEstaciones() {
        return estaciones;
    } 
    
    /**
     * 
     * @param estaciones 
     */
    public void setEstaciones(LinkedList<String> estaciones) {
        this.estaciones = estaciones;
    } 
}

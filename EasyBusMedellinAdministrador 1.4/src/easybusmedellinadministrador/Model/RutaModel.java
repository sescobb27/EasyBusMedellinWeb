/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package easybusmedellinadministrador.Model;

import java.util.LinkedList;

/**
 *
 * @author simon
 */
public class RutaModel{
    private int id_bus;
    private LinkedList<LinkedList<Double>> coordenadas;
    private LinkedList<Integer> sectores;
    private LinkedList<String> estaciones;

    public RutaModel(LinkedList<LinkedList<Double>> coordenadas,LinkedList<Integer> sectores) {
        this.coordenadas = coordenadas;     
        this.sectores = sectores;
        this.estaciones = new LinkedList<String>();
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
    
    public LinkedList<Integer> getSectores() {
        return sectores;
    } 
    
    public void setSectores(LinkedList<Integer> sectores) {
        this.sectores = sectores;
    } 
    /**
     * @return the id_bus
     */
    public int getId_bus() {
        return id_bus;
    }

    /**
     * @param id_bus the id_bus to set
     */
    public void setId_bus(int id_bus) {
        this.id_bus = id_bus;
    }
    
    /**
     * @return the estaciones
     */
    public LinkedList<String> getEstaciones() {
        return estaciones;
    } 
    
    /**
     * @param estaciones 
     */
    public void setEstaciones(LinkedList<String> estaciones) {
        this.estaciones = estaciones;
    } 
}

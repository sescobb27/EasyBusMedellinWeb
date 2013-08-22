/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package easybusmedellinadministrador.ScanersCtrl;
import easybusmedellinadministrador.Control.BusCtrl;
import easybusmedellinadministrador.Control.TipoCtrl;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import easybusmedellinadministrador.Model.TransporteModel;
import easybusmedellinadministrador.Model.RutaModel;
import java.io.File;
import java.util.LinkedList;
/**
 *
 * @author Edgardo
 */
public class LectorArchivo {
    private BufferedReader br,brcoor;
    private BufferedWriter wr;
    private String linea;
    private TransporteModel bus;
    
    public LectorArchivo(String coordenadas, TransporteModel bus,String estaciones)
    {
        this.bus = bus;
        agregar(coordenadas,estaciones);
    }
    
    private void agregar(String coordenadas,String estaciones)
    {
        try {
            TipoCtrl tipo = new TipoCtrl();
            br = new BufferedReader(new FileReader(coordenadas));
            wr = new BufferedWriter(new FileWriter("CoordenadasTemp.txt"));
            bus.setRuta(adaptarTexto("CoordenadasTemp.txt"));
            if(tipo.getTipo(bus.getTipo()).equals("Metro")){
                bus.getRuta().setEstaciones(agregarEstaciones(estaciones));
            }
            BusCtrl busCtrl = new BusCtrl(bus); 
        }catch(FileNotFoundException fnf){
            escribirExcepciones(fnf.getMessage());
        }catch(IOException io){
            escribirExcepciones(io.getMessage());
        }
    }
    
    private LinkedList<String> agregarEstaciones(String archivo)
    {
        LinkedList<String> estaciones = new LinkedList<String>();
        String name = "";
        try{
            brcoor = new BufferedReader(new FileReader(new File(archivo).getAbsolutePath()));
            while((name = brcoor.readLine()) != null)
            {
                if(name.trim().isEmpty())
                    continue;
                estaciones.add(name.trim());
            }
            return estaciones;
        }catch(Exception e){
            return null;
        }
    }
    
    private RutaModel adaptarTexto(String name)
    {
        String texto;
        StringTokenizer temp;
        try{
        while((linea = br.readLine()) != null)
        {
            temp = new StringTokenizer(linea);
            while(temp.hasMoreTokens()){
                texto = temp.nextToken("//");
                texto = texto.trim();
                wr.write(texto);
                wr.newLine();
            }         
        }
            
        } catch (FileNotFoundException fnf) {
            escribirExcepciones(fnf.getMessage());
            return null;
        } catch (IOException io){
            escribirExcepciones(io.getMessage());
            return null;
        } finally {
            try {
                br.close();
                wr.close();
                return leerCoordenadas(name);
            } catch (IOException io) {
                escribirExcepciones(io.getMessage());
                return null;
            }
        }
    }

    public RutaModel leerCoordenadas(String name)
    {
        LinkedList<LinkedList<Double>> tempList = new LinkedList<LinkedList<Double>>();
        LinkedList<Integer> sectores = new LinkedList<Integer>();
        tempList.add(new LinkedList<Double>());
        tempList.add(new LinkedList<Double>());
        String coordenada,temp;
        double latitud = 0,longitud = 0;
        int sector = 0;
        StringTokenizer tokens;
        try{
            brcoor = new BufferedReader(new FileReader(new File(name).getAbsolutePath()));
            while((coordenada = brcoor.readLine()) != null){
                if(coordenada.isEmpty())
                    continue;
                tokens = new StringTokenizer(coordenada);
                temp = tokens.nextToken(",");
                longitud = Double.parseDouble(temp.trim());
                temp = tokens.nextToken(",");
                latitud = Double.parseDouble(temp.trim());   
                sector = calcularSector(6.244401, -75.590310,latitud,longitud);
                switch(sector){
                    case 1:
                        sector = calcularSector(6.301542, -75.565360,latitud, longitud);
                        switch(sector){
                            case 1: 
                                sector = calcularSector(6.328735, -75.552936,latitud, longitud);
                                switch(sector){
                                    case 1: 
                                        sector = 1;
                                        break;
                                    case 2:
                                        sector = 2;
                                        break;
                                    case 3:
                                        sector = 11;
                                        break;
                                    case 4:   
                                        sector = 12;
                                        break;
                                }
                                break;
                            case 2:
                                sector = calcularSector(6.329417, -75.577354,latitud, longitud);
                                switch(sector){
                                    case 1: 
                                        sector = 3;
                                        break;
                                    case 2:
                                        sector = 4;
                                        break;
                                    case 3:
                                        sector = 13;
                                        break;
                                    case 4:   
                                        sector = 14;
                                        break;
                                }
                                break;
                            case 3:
                                sector = calcularSector(6.272983, -75.552549,latitud, longitud);
                                switch(sector){
                                    case 1: 
                                        sector = 21;
                                        break;
                                    case 2:
                                        sector = 22;
                                        break;
                                    case 3:
                                        sector = 31;
                                        break;
                                    case 4:   
                                        sector = 32;
                                        break;
                                }
                                break;
                            case 4:   
                                sector = calcularSector(6.273026, -75.577826,latitud, longitud);
                                switch(sector){
                                    case 1: 
                                        sector = 23;
                                        break;
                                    case 2:
                                        sector = 24;
                                        break;
                                    case 3:
                                        sector = 33;
                                        break;
                                    case 4:   
                                        sector = 34;
                                        break;
                                }
                                break;
                        }
                        break;
                    case 2:
                        sector = calcularSector(6.302118, -75.614004,latitud, longitud);
                        switch(sector){
                            case 1: 
                                sector = calcularSector(6.329119, -75.601645,latitud, longitud);
                                switch(sector){
                                    case 1: 
                                        sector = 5;
                                        break;
                                    case 2:
                                        sector = 6;
                                        break;
                                    case 3:
                                        sector = 15;
                                        break;
                                    case 4:   
                                        sector = 16;
                                        break;
                                }
                                break;
                            case 2:
                                sector = calcularSector(6.329886, -75.626378,latitud, longitud);
                                switch(sector){
                                    case 1: 
                                        sector = 7;
                                        break;
                                    case 2:
                                        sector = 8;
                                        break;
                                    case 3:
                                        sector = 17;
                                        break;
                                    case 4:   
                                        sector = 18;
                                        break;
                                }
                                break;
                            case 3:
                                sector = calcularSector(6.274263, -75.604219,latitud, longitud);
                                switch(sector){
                                    case 1: 
                                        sector = 25;
                                        break;
                                    case 2:
                                        sector = 26;
                                        break;
                                    case 3:
                                        sector = 35;
                                        break;
                                    case 4:   
                                        sector = 36;
                                        break;
                                }
                                break;
                            case 4:   
                                sector = calcularSector(6.274178, -75.627651,latitud, longitud);
                                switch(sector){
                                    case 1: 
                                        sector = 27;
                                        break;
                                    case 2:
                                        sector = 28;
                                        break;
                                    case 3:
                                        sector = 37;
                                        break;
                                    case 4:   
                                        sector = 38;
                                        break;
                                }
                                break;
                        }
                        break;
                    case 3:
                        sector = calcularSector(6.187446, -75.565900,latitud, longitud);
                        switch(sector){
                            case 1: 
                                sector = calcularSector(6.215818, -75.553765,latitud, longitud);
                                switch(sector){
                                    case 1: 
                                        sector = 41;
                                        break;
                                    case 2:
                                        sector = 42;
                                        break;
                                    case 3:
                                        sector = 51;
                                        break;
                                    case 4:   
                                        sector = 52;
                                        break;
                                }
                                break;
                            case 2:
                                sector = calcularSector(6.215775, -75.578563,latitud, longitud);
                                switch(sector){
                                    case 1: 
                                        sector = 43;
                                        break;
                                    case 2:
                                        sector = 44;
                                        break;
                                    case 3:
                                        sector = 53;
                                        break;
                                    case 4:   
                                        sector = 54;
                                        break;
                                }
                                break;
                            case 3:
                                sector = calcularSector(6.159585, -75.553765,latitud, longitud);
                                switch(sector){
                                    case 1: 
                                        sector = 61;
                                        break;
                                    case 2:
                                        sector = 62;
                                        break;
                                    case 3:
                                        sector = 71;
                                        break;
                                    case 4:   
                                        sector = 72;
                                        break;
                                }
                                break;
                            case 4:   
                                sector = calcularSector(6.159500, -75.579085,latitud, longitud);
                                switch(sector){
                                    case 1: 
                                        sector = 63;
                                        break;
                                    case 2:
                                        sector = 64;
                                        break;
                                    case 3:
                                        sector = 73;
                                        break;
                                    case 4:   
                                        sector = 74;
                                        break;
                                }
                                break;
                        }
                        break;
                    case 4:    
                        sector = calcularSector(6.188214, -75.616322,latitud, longitud);
                        switch(sector){
                            case 1: 
                                sector = calcularSector(6.216756, -75.603890,latitud, longitud);
                                switch(sector){
                                    case 1: 
                                        sector = 45;
                                        break;
                                    case 2:
                                        sector = 46;
                                        break;
                                    case 3:
                                        sector = 55;
                                        break;
                                    case 4:   
                                        sector = 56;
                                        break;
                                }
                                break;
                            case 2:
                                sector = calcularSector(6.217780, -75.628853,latitud, longitud);
                                switch(sector){
                                    case 1: 
                                        sector = 47;
                                        break;
                                    case 2:
                                        sector = 48;
                                        break;
                                    case 3:
                                        sector = 57;
                                        break;
                                    case 4:   
                                        sector = 58;
                                        break;
                                }
                                break;
                            case 3:
                                sector = calcularSector(6.159841, -75.603962,latitud, longitud);
                                switch(sector){
                                    case 1: 
                                        sector = 65;
                                        break;
                                    case 2:
                                        sector = 66;
                                        break;
                                    case 3:
                                        sector = 75;
                                        break;
                                    case 4:   
                                        sector = 76;
                                        break;
                                }
                                break;
                            case 4:   
                                sector = calcularSector(6.159244, -75.628939,latitud, longitud);
                                switch(sector){
                                    case 1: 
                                        sector = 67;
                                        break;
                                    case 2:
                                        sector = 68;
                                        break;
                                    case 3:
                                        sector = 77;
                                        break;
                                    case 4:   
                                        sector = 78;
                                        break;
                                }
                                break;
                        }
                        break;
                }
                tempList.get(0).add(longitud);
                tempList.get(1).add(latitud);
                sectores.add(sector);
            }              
            return new RutaModel(tempList,sectores);
        } catch (FileNotFoundException fnf) {
            escribirExcepciones(fnf.getMessage());
            return null;
        } catch (IOException io){
            escribirExcepciones(io.getMessage());
            return null;
        } finally {
            try {
                brcoor.close();
                return new RutaModel(tempList,sectores);
            } catch (IOException io) {
                escribirExcepciones(io.getMessage());    
                return null;
            }
        }
    }
    
    public int calcularSector(double y,double x,double latitud,double longitud)
    {
        int s = 0;
        if(latitud >= y){
            if(longitud >= x){
                s = 1;
            }else{
                s = 2;
            }
        }else{
            if(longitud >= x){
                s = 3;
            }else{
                s = 4;
            }
        }
        return s;
    }
    
    private void escribirExcepciones(String ex){
        System.out.println(ex);
    }   
}
package easybusmedellin.controller;

public class Sectores {
    
    private int sector;
    private final int SECTOR_1 = 1;
    private final int SECTOR_2 = 2;
    private final int SECTOR_3 = 3;
    private final int SECTOR_4 = 4;
    
    public Sectores(){
        sector = 0;
    }
    
    /**
     * Le pasa los parametros a un metodo que calculael sector.
     * @param latitud
     * @param longitud 
     */
    public void encontrarSector(double latitud, double longitud){
       calculo(latitud, longitud);
    }
    
    /**
     * Calcula el sector de una coordenada.
     * @param latitud
     * @param longitud 
     */
    private void calculo(double latitud,double longitud){
        sector = calcularSector(6.244401, -75.590310,latitud,longitud);
                switch(sector){
                    case SECTOR_1:
                        sector = calcularSector(6.301542, -75.565360,latitud, longitud);
                        switch(sector){
                            case SECTOR_1: 
                                sector = calcularSector(6.328735, -75.552936,latitud, longitud);
                                switch(sector){
                                    case SECTOR_1: 
                                        sector = 1;
                                        break;
                                    case SECTOR_2:
                                        sector = 2;
                                        break;
                                    case SECTOR_3:
                                        sector = 11;
                                        break;
                                    case SECTOR_4:   
                                        sector = 12;
                                        break;
                                }
                                break;
                            case SECTOR_2:
                                sector = calcularSector(6.329417, -75.577354,latitud, longitud);
                                switch(sector){
                                    case SECTOR_1: 
                                        sector = 3;
                                        break;
                                    case SECTOR_2:
                                        sector = 4;
                                        break;
                                    case SECTOR_3:
                                        sector = 13;
                                        break;
                                    case SECTOR_4:   
                                        sector = 14;
                                        break;
                                }
                                break;
                            case SECTOR_3:
                                sector = calcularSector(6.272983, -75.552549,latitud, longitud);
                                switch(sector){
                                    case SECTOR_1: 
                                        sector = 21;
                                        break;
                                    case SECTOR_2:
                                        sector = 22;
                                        break;
                                    case SECTOR_3:
                                        sector = 31;
                                        break;
                                    case SECTOR_4:   
                                        sector = 32;
                                        break;
                                }
                                break;
                            case SECTOR_4:   
                                sector = calcularSector(6.273026, -75.577826,latitud, longitud);
                                switch(sector){
                                    case SECTOR_1: 
                                        sector = 23;
                                        break;
                                    case SECTOR_2:
                                        sector = 24;
                                        break;
                                    case SECTOR_3:
                                        sector = 33;
                                        break;
                                    case SECTOR_4:   
                                        sector = 34;
                                        break;
                                }
                                break;
                        }
                        break;
                    case SECTOR_2:
                        sector = calcularSector(6.302118, -75.614004,latitud, longitud);
                        switch(sector){
                            case SECTOR_1: 
                                sector = calcularSector(6.329119, -75.601645,latitud, longitud);
                                switch(sector){
                                    case SECTOR_1: 
                                        sector = 5;
                                        break;
                                    case SECTOR_2:
                                        sector = 6;
                                        break;
                                    case SECTOR_3:
                                        sector = 15;
                                        break;
                                    case SECTOR_4:   
                                        sector = 16;
                                        break;
                                }
                                break;
                            case SECTOR_2:
                                sector = calcularSector(6.329886, -75.626378,latitud, longitud);
                                switch(sector){
                                    case SECTOR_1: 
                                        sector = 7;
                                        break;
                                    case SECTOR_2:
                                        sector = 8;
                                        break;
                                    case SECTOR_3:
                                        sector = 17;
                                        break;
                                    case SECTOR_4:   
                                        sector = 18;
                                        break;
                                }
                                break;
                            case SECTOR_3:
                                sector = calcularSector(6.274263, -75.604219,latitud, longitud);
                                switch(sector){
                                    case SECTOR_1: 
                                        sector = 25;
                                        break;
                                    case SECTOR_2:
                                        sector = 26;
                                        break;
                                    case SECTOR_3:
                                        sector = 35;
                                        break;
                                    case SECTOR_4:   
                                        sector = 36;
                                        break;
                                }
                                break;
                            case SECTOR_4:   
                                sector = calcularSector(6.274178, -75.627651,latitud, longitud);
                                switch(sector){
                                    case SECTOR_1: 
                                        sector = 27;
                                        break;
                                    case SECTOR_2:
                                        sector = 28;
                                        break;
                                    case SECTOR_3:
                                        sector = 37;
                                        break;
                                    case SECTOR_4:   
                                        sector = 38;
                                        break;
                                }
                                break;
                        }
                        break;
                    case SECTOR_3:
                        sector = calcularSector(6.187446, -75.565900,latitud, longitud);
                        switch(sector){
                            case SECTOR_1: 
                                sector = calcularSector(6.215818, -75.553765,latitud, longitud);
                                switch(sector){
                                    case SECTOR_1: 
                                        sector = 41;
                                        break;
                                    case SECTOR_2:
                                        sector = 42;
                                        break;
                                    case SECTOR_3:
                                        sector = 51;
                                        break;
                                    case SECTOR_4:   
                                        sector = 52;
                                        break;
                                }
                                break;
                            case SECTOR_2:
                                sector = calcularSector(6.215775, -75.578563,latitud, longitud);
                                switch(sector){
                                    case SECTOR_1: 
                                        sector = 43;
                                        break;
                                    case SECTOR_2:
                                        sector = 44;
                                        break;
                                    case SECTOR_3:
                                        sector = 53;
                                        break;
                                    case SECTOR_4:   
                                        sector = 54;
                                        break;
                                }
                                break;
                            case SECTOR_3:
                                sector = calcularSector(6.159585, -75.553765,latitud, longitud);
                                switch(sector){
                                    case SECTOR_1: 
                                        sector = 61;
                                        break;
                                    case SECTOR_2:
                                        sector = 62;
                                        break;
                                    case SECTOR_3:
                                        sector = 71;
                                        break;
                                    case SECTOR_4:   
                                        sector = 72;
                                        break;
                                }
                                break;
                            case SECTOR_4:   
                                sector = calcularSector(6.159500, -75.579085,latitud, longitud);
                                switch(sector){
                                    case SECTOR_1: 
                                        sector = 63;
                                        break;
                                    case SECTOR_2:
                                        sector = 64;
                                        break;
                                    case SECTOR_3:
                                        sector = 73;
                                        break;
                                    case SECTOR_4:   
                                        sector = 74;
                                        break;
                                }
                                break;
                        }
                        break;
                    case SECTOR_4:    
                        sector = calcularSector(6.188214, -75.616322,latitud, longitud);
                        switch(sector){
                            case SECTOR_1: 
                                sector = calcularSector(6.216756, -75.603890,latitud, longitud);
                                switch(sector){
                                    case SECTOR_1: 
                                        sector = 45;
                                        break;
                                    case SECTOR_2:
                                        sector = 46;
                                        break;
                                    case SECTOR_3:
                                        sector = 55;
                                        break;
                                    case SECTOR_4:   
                                        sector = 56;
                                        break;
                                }
                                break;
                            case SECTOR_2:
                                sector = calcularSector(6.217780, -75.628853,latitud, longitud);
                                switch(sector){
                                    case SECTOR_1: 
                                        sector = 47;
                                        break;
                                    case SECTOR_2:
                                        sector = 48;
                                        break;
                                    case SECTOR_3:
                                        sector = 57;
                                        break;
                                    case SECTOR_4:   
                                        sector = 58;
                                        break;
                                }
                                break;
                            case SECTOR_3:
                                sector = calcularSector(6.159841, -75.603962,latitud, longitud);
                                switch(sector){
                                    case SECTOR_1: 
                                        sector = 65;
                                        break;
                                    case SECTOR_2:
                                        sector = 66;
                                        break;
                                    case SECTOR_3:
                                        sector = 75;
                                        break;
                                    case SECTOR_4:   
                                        sector = 76;
                                        break;
                                }
                                break;
                            case SECTOR_4:   
                                sector = calcularSector(6.159244, -75.628939,latitud, longitud);
                                switch(sector){
                                    case SECTOR_1: 
                                        sector = 67;
                                        break;
                                    case SECTOR_2:
                                        sector = 68;
                                        break;
                                    case SECTOR_3:
                                        sector = 77;
                                        break;
                                    case SECTOR_4:   
                                        sector = 78;
                                        break;
                                }
                                break;
                        }
                        break;
                }
    } 
    
    /**
     * Calcula el cuadrante en el cual se encuentra una coordenada.
     * @param y
     * @param x
     * @param latitud
     * @param longitud
     * @return cuadrante
     */
    private int calcularSector(double y,double x,double latitud,double longitud)
    {
        int cuadrante = 0;
        if(latitud >= y){
            if(longitud >= x){
                cuadrante = 1;
            }else{
                cuadrante = 2;
            }
        }else{
            if(longitud >= x){
                cuadrante = 3;
            }else{
                cuadrante = 4;
            }
        }
        return cuadrante;
    }
    
    /**
     * @return the sector
     */
    public int getSector()
    {
        return sector;
    }
}

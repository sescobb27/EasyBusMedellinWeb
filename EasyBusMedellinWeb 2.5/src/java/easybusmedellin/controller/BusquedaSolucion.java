package easybusmedellin.controller;

import easybusmedellin.Model.Transporte;
import java.util.LinkedList;
import easybusmedellin.DAO.TransporteDAO;
import easybusmedellin.Model.Ruta;
import easybusmedellin.Model.RutaFinal;
import easybusmedellin.Model.RutasSolucion;


public class BusquedaSolucion {

    
    private RutaFinal rutaFinal;
    private TransporteDAO transporteDao;
    private Sectores sector;
    private double rangoPuntoInicio;
    private double rangoPuntoDestino;
    private double rangoDispuesto;
    private final double RANGOCONSTANTE = 0.001;//100 metros mas o menos
    private LinkedList<Integer> transportesPtoInicio; 
    private LinkedList<Integer> transportesPtoDestino; 
    private LinkedList<LinkedList<Integer>> posiblestransportes;
    private LinkedList<LinkedList<Integer>> sectoresRutaDestino;  
    private LinkedList<LinkedList<Integer>> TransportesUsados;
    private RutasSolucion rutasSolucion;
    private LinkedList<Integer> transportesAnulados;
    
    public BusquedaSolucion()
    {
        iniciar();
    }
    
    /**
     * Inicializa los objetos necesarios para la ejecucion.
     */
    private void iniciar()
    {
        transporteDao =  new TransporteDAO();
        sector = new Sectores();
        rutasSolucion = new RutasSolucion();
        TransportesUsados = new LinkedList<LinkedList<Integer>>();
        transportesAnulados = new LinkedList<Integer>();
    }
    
    /**
     * Organiza las rutas de la mas corta a la mas larga.
     */
    public void ordenarPorDistancia()
    {
        int distancias[] = new int[rutasSolucion.getRutaSolucion().size()],ruta[] = new int[rutasSolucion.getRutaSolucion().size()],temp;
        for(int i = 0; i < ruta.length ; i++)
            ruta[i] = i;
        RutaFinal rutaFinalTemp;
        Transporte transporte;
        for(int i = 0; i < rutasSolucion.getRutaSolucion().size(); i++)
        {
            rutaFinalTemp = rutasSolucion.getRutaSolucion().get(i);
            for(int j = 0; j < rutaFinalTemp.getRutaFinal().size(); j++)
            {
                transporte = rutaFinalTemp.getRutaFinal().get(j);
                distancias[i] += transporte.getRuta().getCoordenadas().get(0).size();
            }
        }
        for(int i = 0; i < distancias.length; i++)
        {
            for(int j = 0;j < distancias.length; j++)
            {
                if(distancias[i] < distancias[j]){
                    temp = distancias[i];
                    distancias[i] = distancias[j];
                    distancias[j] = temp;
                    temp = ruta[i];
                    ruta[i] = ruta[j];
                    ruta[j] = temp;
                }
            }
        }
        RutasSolucion solve = new RutasSolucion();
        for(int i = 0; i < rutasSolucion.getRutaSolucion().size(); i++)
        {
            solve.agregarRutaFinal(rutasSolucion.getRutaSolucion().get(ruta[i]));
        }
        rutasSolucion = solve;
    }
    
    /**
     * Pide que se hallen tres soluciones diferentes si es posible.
     * @param latIni
     * @param longIni
     * @param latDest
     * @param longDest
     * @return the rutasSolucion
     */
    public RutasSolucion hallarRutas(double latIni,double longIni,double latDest,double longDest,int rango)
    {
        rangoDispuesto = rango / 100000.0;
        if(Math.sqrt((Math.pow((latDest-latIni), 2))+(Math.pow((longDest - longIni),2))) <= rangoDispuesto){
            LinkedList<LinkedList<Double>> temp = new LinkedList<LinkedList<Double>>();
            temp.add(new LinkedList<Double>());
            temp.add(new LinkedList<Double>());
            temp.get(0).add(longIni);
            temp.get(0).add(longDest);
            temp.get(1).add(latIni);
            temp.get(1).add(latDest);
            Transporte transporte = new Transporte("Caminar", 0 , 0, "A Pie");
            transporte.setRuta(new Ruta(temp, null));
            rutaFinal = new RutaFinal();
            rutaFinal.agregarTransporteTramo(transporte);
            rutasSolucion.agregarRutaFinal(rutaFinal);
            return rutasSolucion;
        }
        for(int i = 0; i < 3; i++){
            rutaFinal = new RutaFinal();
            tipoDeCaso(latIni, longIni, latDest, longDest);
            if(rutaFinal.getRutaFinal().isEmpty()){
                ordenarPorDistancia();
                return rutasSolucion;
            }
        }
        ordenarPorDistancia();
        return rutasSolucion;
    }
    
    /**
     * Determina el caso con el que se resuelve el problema.
     * @param latIni
     * @param longIni
     * @param latDest
     * @param longDest 
     */
    private void tipoDeCaso(double latIni,double longIni,double latDest,double longDest)
    {
        Transporte transporte;
        iniciarRango();
        transporte = rutaDirecta(latIni,longIni,latDest,longDest);
        volver:
        if(transporte != null){
            rutaFinal.agregarTransporteTramo(transporte);
            for(int i = 0; i < rutasSolucion.getRutaSolucion().size();i++){
                if(rutaFinal.getRutaFinal().size() == rutasSolucion.getRutaSolucion().get(i).getRutaFinal().size()){
                    int l = 0;
                    for(int j = 0; j < rutaFinal.getRutaFinal().size();j++){
                        if(rutaFinal.getRutaFinal().get(j).getId() == rutasSolucion.getRutaSolucion().get(i).getRutaFinal().get(j).getId())
                            l++;
                    }
                    if(l == rutaFinal.getRutaFinal().size()){
                        rutaFinal = new RutaFinal();
                        transporte = null;
                        break volver;
                    }
                }
            }
            rutasSolucion.agregarRutaFinal(rutaFinal);
            TransportesUsados.add(new LinkedList<Integer>());
            TransportesUsados.getLast().add(transporte.getId()); 
        }else{
            rutaConConexionInicio(latIni,longIni,latDest,longDest,-1,0,0,0);
            if(!rutaFinal.getRutaFinal().isEmpty()){
                rutaAUsar(latIni, longIni, latDest, longDest);
                for(int i = 0; i < rutasSolucion.getRutaSolucion().size();i++){
                    if(rutaFinal.getRutaFinal().size() == rutasSolucion.getRutaSolucion().get(i).getRutaFinal().size()){
                        int l = 0;
                        for(int j = 0; j < rutaFinal.getRutaFinal().size();j++){
                            if(rutaFinal.getRutaFinal().get(j).getId() == rutasSolucion.getRutaSolucion().get(i).getRutaFinal().get(j).getId())
                                l++;
                        }
                        if(l == rutaFinal.getRutaFinal().size()){
                            return;
                        }
                    }
                }
                rutasSolucion.agregarRutaFinal(rutaFinal);                
                TransportesUsados.add(new LinkedList<Integer>());
                for(int i = 0; i < rutaFinal.getRutaFinal().size();i++){
                    TransportesUsados.getLast().add(rutaFinal.getRutaFinal().get(i).getId());
                }
            }
        }
        inhabilitarTransporte();
    }
     
    /**
     * Calcula si es posible hacer el recorrido en un solo transporte.
     * @param latIni
     * @param longIni
     * @param latDest
     * @param longDest
     * @return the transporte
     */
    private Transporte rutaDirecta(double latIni,double longIni,double latDest,double longDest)
    {
        transportesPtoInicio = new LinkedList<Integer>();
        transportesPtoDestino = new LinkedList<Integer>();
        buscarRutasCercanas(latIni, longIni,true);
        buscarRutasCercanas(latDest, longDest, false);
        if(transportesPtoDestino.isEmpty() || transportesPtoInicio.isEmpty())
            return null;
        for(int i = 0; i < transportesPtoInicio.size();i++){
            for(int j = 0; j< transportesPtoDestino.size(); j++){
                if(transportesPtoInicio.get(i) == transportesPtoDestino.get(j)){
                    if(getTipo(transportesPtoInicio.get(i)).equals("Metro")){
                        Transporte transporte = seleccionBus(transportesPtoInicio.get(i));
                        tramoRuta(latIni, longIni, latDest, longDest, transporte,0);
                        if(transporte.getRuta().getEstaciones().size() < 2)
                            return null;
                        return transporte;
                    }
                }     
            }  
        }
        for(int i = 0; i < transportesPtoInicio.size();i++){
            for(int j = 0; j< transportesPtoDestino.size(); j++){
                if(transportesPtoInicio.get(i) == transportesPtoDestino.get(j)){
                    Transporte transporte = seleccionBus(transportesPtoInicio.get(i));
                    tramoRuta(latIni, longIni, latDest, longDest, transporte,0);
                    return transporte;
                }     
            }  
        }
        return null;
    }

    /**
     * Inicializa la ejecucion de la busqueda de una ruta con conexiones.
     * @param latIni
     * @param longIni
     * @param latDest
     * @param longDest
     * @param rutaUsada
     * @param conexionID
     * @param coincidencia
     * @param numeroConexiones 
     */
    private void rutaConConexionInicio(double latIni,double longIni,double latDest,double longDest,int rutaUsada,int conexionID,int coincidencia,int numeroConexiones)
    {
        transportesPtoDestino = new LinkedList<Integer>();
        sectoresRutaDestino = new LinkedList<LinkedList<Integer>>();
        iniciarRango();
        buscarRutasCercanas(latDest, longDest, false);
        sectoresRutaDestino = sectoresDeRuta(transportesPtoDestino);
        rutaConConexion(latIni, longIni, latDest, longDest,rutaUsada,conexionID,coincidencia,numeroConexiones); 
    }
    
    /**
     * Busca dos rutas que se puedan conectar para llegar a un destino.
     * @param latIni
     * @param longIni
     * @param latDest
     * @param longDest
     * @param rutaUsada
     * @param conexionId
     * @param coincidencia
     * @param numeroConexiones 
     */
    private void rutaConConexion(double latIni,double longIni,double latDest,double longDest,int rutaUsada,int conexionId,int coincidencia,int numeroConexiones)
    {        
        sector.encontrarSector(latIni, longIni);
        int sectorPuntoInicio = sector.getSector();
        int[] sectorCercano;
        transportesPtoInicio = new LinkedList<Integer>();
        LinkedList<LinkedList<Integer>> sectoresInicio;
        buscarRutasCercanas(latIni, longIni, true);
        if(transportesPtoDestino.isEmpty() || transportesPtoInicio.isEmpty())
            return; 
        sectoresInicio = sectoresDeRuta(transportesPtoInicio);
        posiblesTransportes(sectoresInicio); 
        if(posiblestransportes.get(0).size() == 0){
            int sectorAlternativo[] = sectorCercanoAlternativo(sectoresInicio, sectoresRutaDestino);
            Transporte transporte = seleccionBus(sectorAlternativo[1]);  
            int minimo = puntoMasCercano(latDest, longDest,transporte.getRuta().getCoordenadas(),sectorAlternativo[0] ,transporte.getRuta().getSectores());
            rutaConConexionAlternativa(transporte,transporte.getRuta().getCoordenadas().get(0).get(minimo), transporte.getRuta().getCoordenadas().get(1).get(minimo),latDest,longDest,rutaUsada,conexionId,coincidencia,numeroConexiones);
            return;
        }
        sectorCercano = sectorMasCercano(sectorPuntoInicio, posiblestransportes);
        if(sectorCercano[1] == sectorCercano[2])
            return;
        Transporte transporte = seleccionBus(sectorCercano[1]);
        rutaFinal.agregarTransporteTramo(transporte); 
        transporte = seleccionBus(sectorCercano[2]);
        rutaFinal.agregarTransporteTramo(transporte);
    }
    
    /**
     * Determina posibles transportes para conexion, con su respectivo id 
     * y sector en el cual coinciden.
     * @param sectorInicial 
     */
    private void posiblesTransportes(LinkedList<LinkedList<Integer>> sectorInicial)
    { 
        LinkedList<LinkedList<Integer>> temporal= new LinkedList<LinkedList<Integer>>();
        temporal.add(new LinkedList<Integer>());
        temporal.add(new LinkedList<Integer>());
        temporal.add(new LinkedList<Integer>());
        for(int i = 0; i < sectorInicial.get(0).size();i++)
        {
            for(int j = 0; j < sectoresRutaDestino.get(0).size();j++)
            {
                if(sectorInicial.get(0).get(i) == sectoresRutaDestino.get(0).get(j)){
                    temporal.get(0).add(sectorInicial.get(0).get(i));
                    temporal.get(1).add(sectorInicial.get(1).get(i));
                    temporal.get(2).add(sectoresRutaDestino.get(1).get(j));
                }
            }
        }
        posiblestransportes = temporal;
    }
    
    /**
     * Se encarga de llamar al metodo ,que realiza busqueda de conexiones, solo
     * cuando se neesita mas de una conexion.
     * @param transporte
     * @param latitud
     * @param longitud
     * @param latDest
     * @param longDest
     * @param rutaUsada
     * @param conexionId
     * @param coincidencia
     * @param numeroConexiones 
     */
    private void rutaConConexionAlternativa(Transporte transporte,double latitud, double longitud,double latDest,double longDest,int rutaUsada,int conexionId,int coincidencia,int numeroConexiones)
    {
        rutaFinal.agregarTransporteTramo(transporte);
        numeroConexiones++;
        rutaConConexion(latitud, longitud, latDest,longDest,rutaUsada,conexionId,coincidencia,numeroConexiones);
    }
    
    /**
     * Manda cada transporte a analisis de tramos de su ruta a usar en la solucion.
     * @param latIni
     * @param longIni
     * @param latDest
     * @param longDest 
     */
    private void rutaAUsar(double latIni,double longIni,double latDest,double longDest)
    {
        double latTemporalInicial = latIni, longTemporalInicial = longIni;
        int indiceConexion[] = {0,0}; 
        int inicio = 0; 
        for(int i = 0; i < rutaFinal.getRutaFinal().size();i++){ 
            if((rutaFinal.getRutaFinal().size() - i) >= 2){
                sector.encontrarSector(latTemporalInicial, longTemporalInicial);
                inicio = puntoMasCercano(latTemporalInicial, longTemporalInicial,rutaFinal.getRutaFinal().get(i).getRuta().getCoordenadas(),sector.getSector(), rutaFinal.getRutaFinal().get(i).getRuta().getSectores());
                indiceConexion = distanciaMinimaEntreRutas(rutaFinal.getRutaFinal().get(i).getRuta().getCoordenadas(), rutaFinal.getRutaFinal().get(i+1).getRuta().getCoordenadas(),inicio);
                tramoRuta(latTemporalInicial, longTemporalInicial, rutaFinal.getRutaFinal().get(i).getRuta().getCoordenadas().get(1).get(indiceConexion[0]) , rutaFinal.getRutaFinal().get(i).getRuta().getCoordenadas().get(0).get(indiceConexion[0]), rutaFinal.getRutaFinal().get(i),i);
                latTemporalInicial = rutaFinal.getRutaFinal().get(i).getRuta().getCoordenadas().get(0).getLast();
                longTemporalInicial = rutaFinal.getRutaFinal().get(i).getRuta().getCoordenadas().get(1).getLast();
            }else{
                tramoRuta(rutaFinal.getRutaFinal().get(i).getRuta().getCoordenadas().get(1).get(indiceConexion[1]) , rutaFinal.getRutaFinal().get(i).getRuta().getCoordenadas().get(0).get(indiceConexion[1]),latDest, longDest, rutaFinal.getRutaFinal().get(i),i);
            }
        }      
    }
    
    /**
     * Determina el Transporte y el sector que esta mas cerca a 
     * uno de los sectores de un Transporte.
     * @param sectoresInicial
     * @param sectoresDestino
     * @return the sectoresTemporal
     */
    private int[] sectorCercanoAlternativo(LinkedList<LinkedList<Integer>> sectoresInicial,LinkedList<LinkedList<Integer>> sectoresDestino)
    {
        int distancia = 999,diferencia,x,y;
        int sectoresTemporal[] = {0,0};
        boolean analizar = true;
        for(int i = 0; i < sectoresInicial.get(0).size();i++){
            for(int j = 0; j < sectoresDestino.get(0).size();j++){
                for(int k = 0; k < transportesAnulados.size();k++){
                    if(transportesAnulados.get(k) == sectoresInicial.get(1).get(i) || transportesAnulados.get(k) == sectoresDestino.get(1).get(j)){
                        analizar = false;
                    }
                }
                if(analizar){ 
                    x = Math.abs(sectoresInicial.get(0).get(i)/10 - sectoresDestino.get(0).get(j)/10);
                    y = Math.abs(sectoresInicial.get(0).get(i)%10 - sectoresDestino.get(0).get(j)%10);
                    diferencia = x+y;
                    if(distancia > diferencia){
                        distancia = diferencia;
                        sectoresTemporal[0] = sectoresInicial.get(0).get(i);
                        sectoresTemporal[1] = sectoresInicial.get(1).get(i);
                        if(getTipo(sectoresInicial.get(1).get(i)).equals("Metro"))
                            return sectoresTemporal;
                    }
                }
                analizar = true;
            }
        }
        return sectoresTemporal;
    }

    /**
     * Determina las rutas con conexion mas cercana ,en cuanto a sector,
     * que puedan servir en la busqueda de la solucion.
     * @param sector
     * @param posiblesSectores
     * @return sectorTemporal
     */
    private int[] sectorMasCercano(int sector,LinkedList<LinkedList<Integer>> posiblesSectores)
    {
        int distancia = 999,diferencia,x,y;
        int []sectorTemporal = {0,0,0};
        boolean analizar = true, lineaA = false,lineaB = false;
        for(int i = 0; i < posiblesSectores.get(0).size();i++)
        {
            lineaA = false;lineaB = false;
            for(int k = 0; k < transportesAnulados.size();k++)
            {
                if(transportesAnulados.get(k) == posiblesSectores.get(1).get(i) || transportesAnulados.get(k) == posiblesSectores.get(2).get(i)){
                    analizar = false;
                }
            }
            if(analizar){
                if(transporteDao.nombreTransporte(posiblesSectores.get(1).get(i)).equals("Metro Linea A")||transporteDao.nombreTransporte(posiblesSectores.get(1).get(i)).equals("Metro Linea B")){
                    lineaB = true;
                }
                if(transporteDao.nombreTransporte(posiblesSectores.get(2).get(i)).equals("Metro Linea B")||transporteDao.nombreTransporte(posiblesSectores.get(2).get(i)).equals("Metro Linea A")){
                    lineaA = true;
                }
                if(lineaA && lineaB){
                    sectorTemporal[0] = posiblesSectores.get(0).get(i);
                    sectorTemporal[1] = posiblesSectores.get(1).get(i);
                    sectorTemporal[2] = posiblesSectores.get(2).get(i);
                    return sectorTemporal;
                }
            }
            analizar = true;
        }
        analizar = true;
        for(int i = 0; i < posiblesSectores.get(0).size();i++)
        {
            for(int k = 0; k < transportesAnulados.size();k++)
            {
                if(transportesAnulados.get(k) == posiblesSectores.get(1).get(i) || transportesAnulados.get(k) == posiblesSectores.get(2).get(i)){
                    analizar = false;
                }
            }
            if(analizar){
                x = Math.abs(posiblesSectores.get(0).get(i)/10 - sector/10);
                y = Math.abs(posiblesSectores.get(0).get(i)%10 - sector%10);
                diferencia = x+y;
                if(distancia > diferencia){
                    distancia = diferencia;
                    sectorTemporal[0] = posiblesSectores.get(0).get(i);
                    sectorTemporal[1] = posiblesSectores.get(1).get(i);
                    sectorTemporal[2] = posiblesSectores.get(2).get(i);
                    if(getTipo(posiblesSectores.get(1).get(i)).equals("Metro"))
                            return sectorTemporal;
                    if(getTipo(posiblesSectores.get(2).get(i)).equals("Metro"))
                            return sectorTemporal;
                                    
                }
            }
            analizar = true;
        }
        return sectorTemporal;
    }
    
    /**
     * Agrega los sectores por los que pasa cada uno de los transportes
     * ,que le llegan como parametros, a una lista.
     * @param rutas
     * @return the sectoresRutas
     */
    private LinkedList<LinkedList<Integer>> sectoresDeRuta(LinkedList<Integer> rutas)
    {
        LinkedList<LinkedList<Integer>> sectoresRutas = new LinkedList<LinkedList<Integer>>();
        LinkedList<Integer> temporal;
        sectoresRutas.add(new LinkedList<Integer>());
        sectoresRutas.add(new LinkedList<Integer>());
        for(int i = 0; i < rutas.size();i++){
            temporal = buscarSectoresRuta(rutas.get(i));
            for(int j = 0; j < temporal.size();j++){
                sectoresRutas.get(0).add(temporal.get(j));
                sectoresRutas.get(1).add(rutas.get(i));
            }
        }
        return sectoresRutas;
    }
    
    /**
     * Hace la peticion de busqueda de los sectores de un Transporte.
     * @param id_transporte
     * @return the sectores
     */
    private LinkedList<Integer> buscarSectoresRuta(int id_transporte) 
    {
        LinkedList<Integer> sectores = transporteDao.consultarSectoresRuta(id_transporte);
        return sectores;   
    }
    
    /**
     * Hacepeticion de busqueda de rutas en un rango y lo aumenta
     * gradualmente hasta que encuentre una o mas.
     * @param latidud
     * @param longitud
     * @param seleccion 
     */
    private void buscarRutasCercanas(double latidud,double longitud, boolean seleccion)
    {
        iniciarRango();
        int sectorT = 0;
        int metro = 0;
        String tipo = "";
        if(seleccion){
            sector.encontrarSector(latidud, longitud);
            sectorT = sector.getSector(); 
            for(int j = 0; RANGOCONSTANTE*(j+1)<= rangoDispuesto ; j++)
            {
                transportesPtoInicio = buscarRuta(latidud, longitud,RANGOCONSTANTE*(j+1),sectorT);
                for(int i = 0; i < transportesPtoInicio.size(); i++)
                {
                    tipo = getTipo(transportesPtoInicio.get(i));
                    if(tipo.equals("Metro")){
                        metro = transportesPtoInicio.get(i);
                        break;
                    }
                }
                if(metro != 0)
                    break;
            }
            transportesPtoInicio = new LinkedList<Integer>(); 
            while(transportesPtoInicio.isEmpty() && rangoPuntoInicio < rangoDispuesto)
            {
                aumentarRangoInicio(1);
                transportesPtoInicio = buscarRuta(latidud, longitud,rangoPuntoInicio,sectorT);
            }
            if(metro != 0)
                transportesPtoInicio.add(metro); 
            for(int i = 0; i < transportesPtoInicio.size();i++)
            {
                for(int j = 0; j < transportesAnulados.size();j++)
                {
                    if(transportesPtoInicio.get(i) == transportesAnulados.get(j)){
                        transportesPtoInicio.remove(i);
                        break;
                    }
                }
            }
        }else{
            sector.encontrarSector(latidud, longitud);
            sectorT = sector.getSector();
            for(int j = 0; RANGOCONSTANTE*(j+1)<= rangoDispuesto; j++)
            {
                transportesPtoDestino = buscarRuta(latidud, longitud,RANGOCONSTANTE*(j+1),sectorT);
                for(int i = 0; i < transportesPtoDestino.size(); i++)
                {
                    tipo = getTipo(transportesPtoDestino.get(i));
                    if(tipo.equals("Metro")){
                        metro = transportesPtoDestino.get(i);
                        break;
                    }
                }
                if(metro != 0)
                    break;
            }
            while(transportesPtoDestino.isEmpty() && rangoPuntoDestino < rangoDispuesto)
            {
                aumentarRangoDestino(1);
                transportesPtoDestino = buscarRuta(latidud, longitud,rangoPuntoDestino,sectorT);
            }
            if(metro != 0)
                transportesPtoDestino.add(metro); 
            for(int i = 0; i < transportesPtoDestino.size();i++)
            {
                for(int j = 0; j < transportesAnulados.size();j++)
                {
                    if(transportesPtoDestino.get(i) == transportesAnulados.get(j)){
                        transportesPtoDestino.remove(i);
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * Determina la parte de la ruta de un transporteque es realmente util para resolver el problema.
     * @param latIni
     * @param longIni
     * @param latDest
     * @param longDest
     * @param transporte
     * @param conexion 
     */
    private void tramoRuta(double latIni,double longIni,double latDest,double longDest,Transporte transporte,int conexion)
    {
        if(transporte.getTipo().equals("Metro")){
            rutaUsableMetro(latIni,longIni,latDest,longDest,transporte,conexion);
            return;
        }
        int indiceIni,indiceFin,distancia,indice,sectorInicial,sectorDestino;
        sector.encontrarSector(latIni, longIni);
        sectorInicial = sector.getSector();
        sector.encontrarSector(latDest, longDest);
        sectorDestino = sector.getSector();
        indiceIni = puntoMasCercano(latIni, longIni,transporte.getRuta().getCoordenadas(),sectorInicial,transporte.getRuta().getSectores());
        indiceFin = puntoMasCercano(latDest, longDest,transporte.getRuta().getCoordenadas(),sectorDestino,transporte.getRuta().getSectores());
        LinkedList<Integer> sectorTemporal = transporte.getRuta().getSectores();
        LinkedList<LinkedList<Double>> coordenadasTemporal = transporte.getRuta().getCoordenadas();
        LinkedList<LinkedList<Double>> coordenadas = new LinkedList<LinkedList<Double>>();
        LinkedList<Integer> sectores = new LinkedList<Integer>();
        coordenadas.add(new LinkedList<Double>());
        coordenadas.add(new LinkedList<Double>());
        coordenadas.add(new LinkedList<Double>());  
        if(conexion != 0)
        indiceIni = puntoDeSubida(indiceFin, transporte, latIni, longIni, sectorInicial, indiceIni);
        indice = indiceIni;
        if(indiceIni > indiceFin){
            distancia = ((coordenadasTemporal.get(1).size())- indiceIni)+indiceFin+1;
        }else{
            distancia = 1+(indiceFin - indiceIni);
        }
        for(int i = 0;i < distancia;i++){
            if(indice >= coordenadasTemporal.get(1).size())
                indice = 0;
            coordenadas.get(0).add(coordenadasTemporal.get(0).get(indice));
            coordenadas.get(1).add(coordenadasTemporal.get(1).get(indice));
            coordenadas.get(2).add(coordenadasTemporal.get(2).get(indice));
            sectores.add(sectorTemporal.get(indice));
            indice++;
        }
        transporte.setRuta(new Ruta(coordenadas,sectores)); 
    }
    
    /**
     * Determina la ruta util ,para resolver el problema, de los transportes tipo Metro.
     * @param latIni
     * @param longIni
     * @param latDest
     * @param longDest
     * @param transporte
     * @param conexion 
     */
    private void rutaUsableMetro(double latIni,double longIni,double latDest,double longDest,Transporte transporte,int conexion)
    {
        int indiceIni,indiceFin,distancia,indice,sectorInicial,sectorDestino,temp; 
        sector.encontrarSector(latIni, longIni);
        sectorInicial = sector.getSector();
        sector.encontrarSector(latDest, longDest);
        sectorDestino = sector.getSector(); 
        indiceIni = puntoMasCercano(latIni, longIni,transporte.getRuta().getCoordenadas(),sectorInicial,transporte.getRuta().getSectores());
        indiceFin = puntoMasCercano(latDest, longDest,transporte.getRuta().getCoordenadas(),sectorDestino,transporte.getRuta().getSectores());
        LinkedList<Integer> sectorTemporal = transporte.getRuta().getSectores();
        LinkedList<String> estaciones = new LinkedList<String>();
        LinkedList<LinkedList<Double>> coordenadasTemporal = transporte.getRuta().getCoordenadas();
        LinkedList<LinkedList<Double>> coordenadas = new LinkedList<LinkedList<Double>>();
        LinkedList<Integer> sectores = new LinkedList<Integer>();
        coordenadas.add(new LinkedList<Double>());
        coordenadas.add(new LinkedList<Double>());
        coordenadas.add(new LinkedList<Double>());  
        if(conexion != 0)
            indiceIni = puntoDeSubida(indiceFin, transporte, latIni, longIni, sectorInicial, indiceIni);
        if(indiceIni > indiceFin){
            temp = indiceIni;
            indiceIni = indiceFin;
            indiceFin = temp;
        }
        indice = indiceIni;
        distancia = (indiceFin - indiceIni)+1;
        for(int i = 0;i < distancia;i++){
            if(indice >= coordenadasTemporal.get(1).size())
                indice = 0;
            coordenadas.get(0).add(coordenadasTemporal.get(0).get(indice));
            coordenadas.get(1).add(coordenadasTemporal.get(1).get(indice));
            coordenadas.get(2).add(coordenadasTemporal.get(2).get(indice));
            sectores.add(sectorTemporal.get(indice));
            estaciones.add(transporte.getRuta().getEstaciones().get(indice)); 
            indice++;
        }
        transporte.setRuta(new Ruta(coordenadas,sectores));
        transporte.getRuta().setEstaciones(estaciones); 
    }
    
    /**
     * Determina el mejor lugar para subir a un transporte.
     * @param indiceD
     * @param transporte
     * @param latIni
     * @param longIni
     * @param sectorDestino
     * @param indiceI
     * @return 
     */
    private int puntoDeSubida(int indiceD,Transporte transporte,double latIni,double longIni,int sectorDestino,int indiceI)
    {
        int indiceInicial = 0,distancia = 0,temporal; 
        if(indiceI > indiceD){
            distancia = ((transporte.getRuta().getCoordenadas().get(1).size())- indiceI)+indiceD+1;
        }else{
            distancia = 1+(indiceD - indiceI);
        }
        LinkedList<Integer> subida = posiblesSubidas(transporte,latIni,longIni,sectorDestino);
        for(int i = 0; i < subida.size() ;i++){
            indiceInicial = subida.get(i);
            if(indiceInicial > indiceD){
            temporal = ((transporte.getRuta().getCoordenadas().get(1).size())- indiceInicial)+indiceD+1;
            }else{
                temporal = 1+(indiceD - indiceInicial);
            }
            if(Math.abs(distancia - temporal) > 10 && temporal < distancia){
                return indiceInicial;
            }
        }
        return indiceI;
    }
    
    /**
     * Determina los posibles lugares en los cuales se podria abordar un transporte.
     * @param transporte
     * @param latDest
     * @param longDest
     * @param sectorDestino
     * @return the puntoSubida
     */
    private LinkedList<Integer> posiblesSubidas(Transporte transporte,double latDest,double longDest,int sectorDestino)
    {
        LinkedList<Integer> puntosSubida = new LinkedList<Integer>();
        double xi = latDest + 0.001, xf = latDest - 0.001, yi = longDest + 0.001, yf = longDest - 0.001; 
        int distancia,x,y;
        for(int i = 0;i < transporte.getRuta().getCoordenadas().get(0).size();i++){
            
            x = Math.abs(transporte.getRuta().getSectores().get(i)/10 - sectorDestino/10);
            y = Math.abs(transporte.getRuta().getSectores().get(i)%10 - sectorDestino%10);
            distancia = x+y;
            if(distancia <= 2){    
                if((transporte.getRuta().getCoordenadas().get(1).get(i)<= xi && transporte.getRuta().getCoordenadas().get(1).get(i)>= xf) && (transporte.getRuta().getCoordenadas().get(0).get(i)<= yi && transporte.getRuta().getCoordenadas().get(0).get(i)>= yf) ){
                    puntosSubida.add(i);
                }
            }
        }
        return puntosSubida;
    }
    
    /**
     * Determina el punto mas cercano entre una coordenada y una lista de coordenadas.
     * @param latitud
     * @param longitud
     * @param coordenadas
     * @param sector
     * @param sectores
     * @return the minimo
     */
    private int puntoMasCercano(double latitud,double longitud,LinkedList<LinkedList<Double>> coordenadas,int sector,LinkedList<Integer> sectores)
    {
        double distancia = 999999999,formula;
        int minimo = 0;
        for(int i = 0;i < coordenadas.get(0).size();i++){
            if(sector == sectores.get(i)){
                formula = Math.sqrt((Math.pow((coordenadas.get(0).get(i)-longitud), 2))+(Math.pow((coordenadas.get(1).get(i)-latitud),2)));
                if(formula < distancia ){
                    distancia = formula;
                    minimo = i;
                }
            }else{
                int distancia_sector = 9999,x,y;            
                x = Math.abs(sector/10 - sectores.get(i)/10);
                y = Math.abs(sector%10 - sectores.get(i)%10);
                distancia_sector = x+y;
                if(distancia_sector <= 1){
                    formula = Math.sqrt((Math.pow((coordenadas.get(0).get(i)-longitud), 2))+(Math.pow((coordenadas.get(1).get(i)-latitud),2)));
                    if(formula < distancia ){
                        distancia = formula;
                        minimo = i;
                    }
                }
            }
        }
        return minimo;
    }

    /**
     * Determina el punto mas cercano entre dos rutas a nivel de coordenadas.
     * @param coordenadasUno
     * @param coordenadasDos
     * @param indiceI
     * @return the minimo
     */
    private int[] distanciaMinimaEntreRutas(LinkedList<LinkedList<Double>> coordenadasUno,LinkedList<LinkedList<Double>> coordenadasDos,int indiceI)
    {
        double distancia = 999999999,formula;
        int []minimo = {0,0};
        for(int i = 0;i < coordenadasUno.get(0).size();i++)
        {
                if(indiceI >= coordenadasUno.get(0).size()){
                    indiceI = 0;
                }
                for(int j = 0; j < coordenadasDos.get(0).size();j++)
                {
                        formula = Math.sqrt((Math.pow((coordenadasDos.get(0).get(j)-coordenadasUno.get(0).get(indiceI)), 2))+(Math.pow((coordenadasDos.get(1).get(j)-coordenadasUno.get(1).get(indiceI)),2)));
                        if(distancia > formula ){
                            distancia = formula;
                            minimo[0] = indiceI;
                            minimo[1] = j;
                            if(distancia <= 0.0002)
                                return minimo;
                        } 
                }
                indiceI++;
        }
        return minimo;
    }
         
    /**
     * Solicita a las clases DAO la busqueda de Transportes que pasen dentro de un rango.
     * @param latitud
     * @param longitud
     * @param rango
     * @param sec
     * @return the rutas
     */
    private LinkedList <Integer> buscarRuta(double latitud, double longitud,double rango,int sec)
    {
        LinkedList rutas = transporteDao.rutasEnRango(latitud,longitud ,rango,sec);
        return rutas;    
    }
    
    /**
     * Solicita a las clases DAO la busqueda de todos los datos de un Transporte.
     * @param index
     * @return the transporte
     */
    private Transporte seleccionBus(int index)
    {
        Transporte transporte = transporteDao.seleccionarTransporte(index);
        return transporte;
    }
    
    /**
     * Aumenta el rangoPuntoInicio.
     * @param times 
     */
    private void aumentarRangoInicio(int times)
    {
        rangoPuntoInicio += (RANGOCONSTANTE*times);
    }
    
    /**
     * Aumenta el rangoPuntoDestino.
     * @param times 
     */
    private void aumentarRangoDestino(int times) 
    {
        rangoPuntoDestino += (RANGOCONSTANTE*times);
    }
    
    /**
     * Inicia los rangos en 0.
     */
    private void iniciarRango()
    {
        rangoPuntoDestino = 0;
        rangoPuntoInicio = 0;
    }

    /**
     * Inhabilita un transporte usado para evitar soluciones iguales.
     */
    private void inhabilitarTransporte() 
    {
        Transporte transporte;
        for(int i = 0; i < rutaFinal.getRutaFinal().size();i++)
        {
            transporte = rutaFinal.getRutaFinal().get(i);
            if(transportesPtoInicio.contains(transporte.getId())){
                if(transportesPtoInicio.size() >= transportesPtoDestino.size()){
                    transportesAnulados.add(transporte.getId());
                    return;
                }else{
                    if(rutaFinal.getRutaFinal().size() == 1){
                        transportesAnulados.add(transporte.getId());
                        return;
                    }
                }
            }else if(transportesPtoDestino.contains(transporte.getId())){
                if(transportesPtoInicio.size() <= transportesPtoDestino.size()){
                    transportesAnulados.add(transporte.getId());
                    return;
                }else{
                    if(rutaFinal.getRutaFinal().size() == 1){
                        transportesAnulados.add(transporte.getId());
                        return;
                    }
                }
            }
            
        }
    }
    /**
     * Retorna el tipo de transporte.
     * @param id_Transporte
     * @return Tipo
     */
    private String getTipo(int id_Transporte)
    {
        return transporteDao.getTipo(transporteDao.getIdTipo(id_Transporte)); 
    }
} 
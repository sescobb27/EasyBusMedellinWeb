package easybusmedellin.Model;


public class Transporte {
    private String nombre;
    private int precio;
    private Ruta ruta;
    private int id;
    private String tipo;

    public Transporte(String Nombre, int precio,int id,String tipo) {
        this.nombre = Nombre;
        this.precio = precio;
        this.id= id;
        this.tipo = tipo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the Tipo
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the precio
     */
    public int getPrecio() {
        return precio;
    }
    
    /**
     * @param precio the precio to set
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }
    
    /**
     * @param nombre the nombre to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
     * @param ruta the ruta to set
     */
    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }
    
    /**
     * @return the ruta
     */
    public Ruta getRuta() {
        return ruta;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * @return the idd
     */
    public int getId() 
    {
        return id;
    }
}

package edu.eci.arsw.coronavirus.model;

/**
 * Ciudad
 * POJO que representa una ciudad dentro del sistema.
 */
public class Ciudad {

    private String nombre;
    private long muertos;
    private long infectados;
    private long curados;
    private String pais;
    private String provincia;

    public long getMuertos() {
        return muertos;
    }

    public void setMuertos(long muertos) {
        this.muertos = muertos;
    }

    public long getInfectados() {
        return infectados;
    }

    public void setInfectados(long infectados) {
        this.infectados = infectados;
    }

    public long getCurados() {
        return curados;
    }

    public void setCurados(long curados) {
        this.curados = curados;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Ciudad(String nombre, long muertos, long infectados, long curados, String pais, String provincia) {
        this.nombre = nombre;
        this.muertos = muertos;
        this.infectados = infectados;
        this.curados = curados;
        this.pais = pais;
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return "Ciudad [curados=" + curados + ", infectados=" + infectados + ", muertos=" + muertos + ", nombre="
                + nombre + ", pais=" + pais + ", provincia=" + provincia + "]";
    }
    
}
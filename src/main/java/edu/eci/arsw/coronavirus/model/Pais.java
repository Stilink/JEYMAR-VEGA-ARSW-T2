package edu.eci.arsw.coronavirus.model;

import java.util.Map;

/**
 * Pais
 * POJO que representa a un Pais dentro del sistema
 */
public class Pais {

    private String nombre;
    private Map<String, Provincia> provincias;
    private long infectados;
    private long muertos;
    private long curados;
    private double latitud;
    private double longitud;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Map<String, Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(Map<String, Provincia> provincias) {
        this.provincias = provincias;
    }

    public Pais(String nombre, Map<String, Provincia> provincias, long infectados, long muertos, long curados) {
        this.nombre = nombre;
        this.provincias = provincias;
        this.infectados = infectados;
        this.muertos = muertos;
        this.curados = curados;
    }

    public long getInfectados() {
        return infectados;
    }

    public void setInfectados(long infectados) {
        this.infectados = infectados;
    }

    public long getMuertos() {
        return muertos;
    }

    public void setMuertos(long muertos) {
        this.muertos = muertos;
    }

    public long getCurados() {
        return curados;
    }

    public void setCurados(long curados) {
        this.curados = curados;
    }

    public void increaseCurados(long curados){
        this.curados+= curados;
    }

    public void increaseInfectados(long infectados){
        this.infectados+= infectados;
    }

    public void increaseMuertos(long muertos){
        this.muertos+= muertos;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Pais [curados=" + curados + ", infectados=" + infectados + ", latitud=" + latitud + ", longitud="
                + longitud + ", muertos=" + muertos + ", nombre=" + nombre + ", provincias=" + provincias + "]";
    }
    
}
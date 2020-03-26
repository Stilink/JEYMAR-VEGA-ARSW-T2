package edu.eci.arsw.coronavirus.model;

import java.util.Map;

/**
 * Provincia
 * POJO que represente una provincia dentro del sistema.
 */
public class Provincia {

    private String nombre;
    private Map<String, Ciudad> ciudades;
    private long infectados;
    private long curados;
    private long muertos;

    public Map<String, Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(Map<String, Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Provincia(String nombre, Map<String, Ciudad> ciudades, long infectados, long curados, long muertos) {
        this.nombre = nombre;
        this.ciudades = ciudades;
        this.infectados = infectados;
        this.curados = curados;
        this.muertos = muertos;
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

    @Override
    public String toString() {
        return "Provincia [ciudades=" + ciudades + ", curados=" + curados + ", infectados=" + infectados + ", muertos="
                + muertos + ", nombre=" + nombre + "]";
    }
}
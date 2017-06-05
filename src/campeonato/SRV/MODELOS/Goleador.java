/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV.MODELOS;

/**
 *
 * @author lara
 */
public class Goleador implements Comparable<Goleador>{
    
    private String nombre;
    private String nombreEquipo;
    private int goles;

    public Goleador() {
    }

    public Goleador(String nombre, String nombreEquipo, int goles) {
        this.nombre = nombre;
        this.nombreEquipo = nombreEquipo;
        this.goles = goles;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the nombreEquipo
     */
    public String getNombreEquipo() {
        return nombreEquipo;
    }

    /**
     * @param nombreEquipo the nombreEquipo to set
     */
    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    /**
     * @return the goles
     */
    public int getGoles() {
        return goles;
    }

    /**
     * @param goles the goles to set
     */
    public void setGoles(int goles) {
        this.goles = goles;
    }

    @Override
    public int compareTo(Goleador t) {
        int estado = -1;
        if(this.getGoles() > t.getGoles()){ estado = -1;}
        else{estado = 1;}
        return estado;
    }
    
    
}

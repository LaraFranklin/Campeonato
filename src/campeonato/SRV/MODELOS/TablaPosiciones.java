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
public class TablaPosiciones {
    
    private String nombre;
    private int partidosGanados;
    private int partidosPerdidos;
    private int partidosEmpatados;
    private int golesFavor;
    private int golesContra;
    private int golesDiferencia;
    private int partidosJugados;
    private int puntos;

    public TablaPosiciones() {
    }

    public TablaPosiciones(String nombre, int partidosGanados, int partidosPerdidos, int partidosEmpatados, int golesFavor, int golesContra) {
        this.nombre = nombre;
        this.partidosGanados = partidosGanados;
        this.partidosPerdidos = partidosPerdidos;
        this.partidosEmpatados = partidosEmpatados;
        this.golesFavor = golesFavor;
        this.golesContra = golesContra;
        this.golesDiferencia = this.golesFavor - this.golesContra;
        this.partidosJugados = this.partidosEmpatados + this.partidosGanados + this.partidosPerdidos;
        this.puntos = (this.partidosEmpatados * 1) + (this.partidosGanados * 3);
    }
    public TablaPosiciones(String nombre) {
        this.nombre = nombre;
        this.partidosGanados = 0;
        this.partidosPerdidos = 0;
        this.partidosEmpatados = 0;
        this.golesFavor = 0;
        this.golesContra = 0;
        this.golesDiferencia = this.golesFavor - this.golesContra;
        this.partidosJugados = this.partidosEmpatados + this.partidosGanados + this.partidosPerdidos;
        this.puntos = (this.partidosEmpatados * 1) + (this.partidosGanados * 3);
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
     * @return the partidosGanados
     */
    public int getPartidosGanados() {
        return partidosGanados;
    }

    /**
     * @param partidosGanados the partidosGanados to set
     */
    public void setPartidosGanados(int partidosGanados) {
        this.partidosGanados = partidosGanados;
    }

    /**
     * @return the partidosPerdidos
     */
    public int getPartidosPerdidos() {
        return partidosPerdidos;
    }

    /**
     * @param partidosPerdidos the partidosPerdidos to set
     */
    public void setPartidosPerdidos(int partidosPerdidos) {
        this.partidosPerdidos = partidosPerdidos;
    }

    /**
     * @return the partidosEmpatados
     */
    public int getPartidosEmpatados() {
        return partidosEmpatados;
    }

    /**
     * @param partidosEmpatados the partidosEmpatados to set
     */
    public void setPartidosEmpatados(int partidosEmpatados) {
        this.partidosEmpatados = partidosEmpatados;
    }

    /**
     * @return the golesFavor
     */
    public int getGolesFavor() {
        return golesFavor;
    }

    /**
     * @param golesFavor the golesFavor to set
     */
    public void setGolesFavor(int golesFavor) {
        this.golesFavor = golesFavor;
    }

    /**
     * @return the golesContra
     */
    public int getGolesContra() {
        return golesContra;
    }

    /**
     * @param golesContra the golesContra to set
     */
    public void setGolesContra(int golesContra) {
        this.golesContra = golesContra;
    }

    /**
     * @return the golesDiferencia
     */
    public int getGolesDiferencia() {
        return golesDiferencia;
    }

    /**
     * @param golesDiferencia the golesDiferencia to set
     */
    public void setGolesDiferencia(int golesDiferencia) {
        this.golesDiferencia = golesDiferencia;
    }

    /**
     * @return the partidosJugados
     */
    public int getPartidosJugados() {
        return partidosJugados;
    }

    /**
     * @param partidosJugados the partidosJugados to set
     */
    public void setPartidosJugados(int partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    /**
     * @return the puntos
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * @param puntos the puntos to set
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    
    public void addPG(){
        this.partidosGanados+=1;
        calcularPuntos();
    }
    
    public void addPP(){
        this.partidosPerdidos+=1;
        this.partidosJugados = this.partidosEmpatados + this.partidosGanados + this.partidosPerdidos;
    }
    
    public void addPE(){
        this.partidosEmpatados+=1;
        calcularPuntos();
    }
    
    private void calcularPuntos(){
        this.puntos = (this.partidosEmpatados * 1) + (this.partidosGanados * 3);
        this.partidosJugados = this.partidosEmpatados + this.partidosGanados + this.partidosPerdidos;
    }
    
    private void calcularGoles(){
        this.golesDiferencia = this.golesFavor - this.golesContra;
    }
    
    public void addGolesFavor(int goles){
        this.golesFavor += goles;
        calcularGoles();
    }
    
    public void addGolesContra(int goles){
        this.golesContra += goles;
        calcularGoles();
    }

    @Override
    public String toString() {
        return this.getNombre()+" "+this.getPartidosJugados() + " " +this.getPartidosGanados()
                + " " +this.getPartidosEmpatados() + " " +this.getPartidosPerdidos() + " " +this.getGolesFavor()
                + " " +this.getGolesContra() + " " + this.getPuntos()+ " " +this.getGolesDiferencia(); //To change body of generated methods, choose Tools | Templates.
    }

   
    
    
    
    
}

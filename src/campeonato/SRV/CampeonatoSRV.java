/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV;

import campeonato.DAO.CampeonatoJpaController;
import campeonato.DAO.EquipoJpaController;
import campeonato.DAO.GrupoEquipoJpaController;
import campeonato.DAO.GrupoJpaController;
import campeonato.DAO.JugadorJpaController;
import campeonato.DAO.PartidoJpaController;
import campeonato.SRV.MODELOS.Campeonato;
import campeonato.SRV.MODELOS.Equipo;
import campeonato.SRV.MODELOS.Grupo;
import campeonato.SRV.MODELOS.GrupoEquipo;
import campeonato.SRV.MODELOS.GrupoEquipoPK;
import campeonato.SRV.MODELOS.GrupoPK;
import campeonato.SRV.MODELOS.Jugador;
import campeonato.SRV.MODELOS.Partido;
import campeonato.SRV.MODELOS.PartidoPK;
import campeonato.SRV.MODELOS.TablaPosiciones;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

/**
 *
 * @author lara
 */
public class CampeonatoSRV {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    JugadorJpaController jjc;
    EquipoJpaController ejc;
    GrupoEquipoJpaController gejc;
    GrupoJpaController gjc;
    CampeonatoJpaController cjc;
    PartidoJpaController pjc;

    public CampeonatoSRV() {
        emf = Persistence.createEntityManagerFactory("campeonatoPU");
        em = emf.createEntityManager();
    }
    private void cerrar(){
        em.close();
        emf.close();
    }
    
    public Campeonato get(int id){
        cjc = new CampeonatoJpaController(emf);
        return cjc.findCampeonato(id);
    }
            
    public void AsignarJugadores(int numero) throws Exception{
        jjc = new JugadorJpaController(emf);
        ejc = new EquipoJpaController(emf);
        
        List<Jugador> jugadores = new ArrayList<Jugador>();
        List<Equipo> equipos = new ArrayList<Equipo>();
        jugadores = jjc.findJugadorEntities();
        equipos = ejc.findEquipoEntities();
        int contador = 0;
        Collection<Equipo> equiposCollection = new ArrayList<Equipo>();
        Iterator<Jugador> itJ = jugadores.iterator();
        for(Equipo equipo : equipos){
            contador=0;
            equiposCollection.removeAll(equipos);
            equiposCollection.add(equipo);
            while(contador<numero){
                Jugador jugador = itJ.next();
                jugador.setEquipoCollection(equiposCollection);
                em.getTransaction().begin();
                jjc.edit(jugador);
                em.getTransaction().commit();
                contador+=1;
            }
        }
        cerrar();
        
    }
    
    public void AgregarEquipos(String idEtapa, int grupos, int equiposGrupo, int idCampeonato) throws Exception{
        
        ejc = new EquipoJpaController(emf);
        gejc = new GrupoEquipoJpaController(emf);
        gjc = new GrupoJpaController(emf);
        
        List<Equipo> equipos = ejc.findEquipoEntities();
        Iterator<Equipo> itg = equipos.iterator();
        for (int i = 1; i <= grupos; i++) {
            for (int j = 1; j <= equiposGrupo; j++) {
                Equipo equipo = itg.next();
                Date date = new Date();
                GrupoPK grupoPK = new GrupoPK(i, idEtapa, idCampeonato);
                Grupo grupo = gjc.findGrupo(grupoPK);
                //Grupo g = new Grupo(i, idEtapa, idCampeonato);
                GrupoEquipoPK gpk = new GrupoEquipoPK(equipo.getIdEquipo(), i, idEtapa, idCampeonato);
                GrupoEquipo ge = new GrupoEquipo(gpk, j, date, grupo, equipo);
                em.getTransaction().begin();
                gejc.create(ge);
                em.getTransaction().commit();
            }
        }
        cerrar();
    }
    
    private int obtenerEquipo(int idCampeonato, int idGrupo, int posicion, String idEtapa){
        TypedQuery<GrupoEquipo> query = em.createNamedQuery("GrupoEquipo.findByIdGrupo", GrupoEquipo.class);
            query.setParameter("idGrupo", idGrupo); 
            query.setParameter("idCampeonato", idCampeonato);
            query.setParameter("idEtapa", idEtapa);
            query.setParameter("posicion", posicion);
            List<GrupoEquipo> lista = new ArrayList<>();
            lista = query.getResultList();
            int equipo = 0;
            Iterator<GrupoEquipo> it = lista.iterator();
        while(it.hasNext()){
            equipo = it.next().getEquipo().getIdEquipo();
        }
        return equipo;

    }
    public void JugarRondaInicial(int LP1, int VP1, int LP2, int VP2, int idCampeonato) throws Exception{
        
        int EquipoLocal = 0;
        int EquipoVisita = 0;
        
        for (int i = 1; i <= 4; i++) {
            EquipoLocal = obtenerEquipo(idCampeonato, i, LP1, "Primera Ronda");
            EquipoVisita = obtenerEquipo(idCampeonato, i, VP1, "Primera Ronda");
            jugarPartido(EquipoLocal, EquipoVisita, idCampeonato, "Primera Ronda", i);

            EquipoLocal = obtenerEquipo(idCampeonato, i, LP2, "Primera Ronda");
            EquipoVisita = obtenerEquipo(idCampeonato, i, VP2, "Primera Ronda");
            jugarPartido(EquipoLocal, EquipoVisita, idCampeonato, "Primera Ronda", i);
        }
    } 
    
    public void jugarCuartos(int idCampeonato) throws Exception{
        int Equipo1 = obtenerEquipo(idCampeonato, 1, 1, "Cuartos");
        int Equipo2 = obtenerEquipo(idCampeonato, 1, 2, "Cuartos");
        int Equipo3 = obtenerEquipo(idCampeonato, 1, 3, "Cuartos");
        int Equipo4 = obtenerEquipo(idCampeonato, 1, 4, "Cuartos");
        int Equipo5 = obtenerEquipo(idCampeonato, 1, 5, "Cuartos");
        int Equipo6 = obtenerEquipo(idCampeonato, 1, 6, "Cuartos");
        int Equipo7 = obtenerEquipo(idCampeonato, 1, 7, "Cuartos");
        int Equipo8 = obtenerEquipo(idCampeonato, 1, 8, "Cuartos");
        
        jugarPartido(Equipo1, Equipo8, idCampeonato, "Cuartos", 1);
        jugarPartido(Equipo2, Equipo7, idCampeonato, "Cuartos", 1);
        jugarPartido(Equipo3, Equipo6, idCampeonato, "Cuartos", 1);
        jugarPartido(Equipo4, Equipo5, idCampeonato, "Cuartos", 1);
        cerrar();
    }
    
    private void jugarPartido(int EL, int EV, int idCampeonato, String idEtapa, int idGrupo) throws Exception{
        
        int ML = (int) (Math.random()*6);
        int MV = (int) (Math.random()*6);
        while(!idEtapa.equalsIgnoreCase("Primera Ronda") && ML == MV){
            ML = (int) (Math.random()*6);
            MV = (int) (Math.random()*6);
        }
        gjc = new GrupoJpaController(emf);
        ejc = new EquipoJpaController(emf);
        pjc = new PartidoJpaController(emf);
        
        Grupo grupo = gjc.findGrupo(new GrupoPK(idGrupo, idEtapa, idCampeonato));
        PartidoPK ppk = new PartidoPK(0, idGrupo, idEtapa, idCampeonato);
        Partido partido = new Partido(ppk, ML,MV, grupo, ejc.findEquipo(EL), ejc.findEquipo(EV));
        em.getTransaction().begin();
        pjc.create(partido);
        em.getTransaction().commit();
    }
    
    public ArrayList<TablaPosiciones> clasificados(int idCampeonato){
        ArrayList<TablaPosiciones> clasificados = new ArrayList<>();
        TablaPosicionesSRV posicionesSRV = new TablaPosicionesSRV();
        List<TablaPosiciones> tps = null;
        for (int i = 1; i < 5; i++) {
            tps = posicionesSRV.get(i,idCampeonato , "Primera Ronda");
            clasificados.add(tps.get(0));
            clasificados.add(tps.get(1));
        }
        Collections.sort(clasificados,new OrdenarTablaPosiciones());
        return clasificados;
    }
    
    public void cuartos(int idCampeonato, ArrayList<TablaPosiciones> clasificados) throws Exception{
  
        gjc = new GrupoJpaController(emf);
        gejc = new GrupoEquipoJpaController(emf);
        Equipo equipo = null;
        String idEtapa = "Cuartos";
        ArrayList<Equipo> equipos = new ArrayList<>();
        List<Equipo> resul = new ArrayList<>();
        TypedQuery<Equipo> query = null;
        for (TablaPosiciones clasificado : clasificados) {
            query = em.createNamedQuery("Equipo.findByNombre", Equipo.class);
            query.setParameter("nombre", clasificado.getNombre());
            resul = query.getResultList();
            Iterator it = resul.iterator();
            while(it.hasNext()){
                equipo = (Equipo) it.next();
            }
            equipos.add(equipo);
        }
        EtapaSRV etapaSRV = new EtapaSRV();
        GrupoSRV grupoSRV = new GrupoSRV();
        etapaSRV.add(idEtapa, idCampeonato,"Cuartos de final");
        grupoSRV.add(1, idEtapa , idCampeonato);
        Date date = new Date();
        GrupoPK grupoPK = new GrupoPK(1, idEtapa, idCampeonato);
        Grupo grupo = gjc.findGrupo(grupoPK);
        int posicion = 1;
        for (Equipo equipo1 : equipos) {
            GrupoEquipoPK equipoPK = new GrupoEquipoPK(equipo1.getIdEquipo(), 1, idEtapa, idCampeonato);
            GrupoEquipo grupoEquipo = new GrupoEquipo(equipoPK, posicion, date, grupo, equipo1);
            em.getTransaction().begin();
            gejc.create(grupoEquipo);
            em.getTransaction().commit();
            posicion++;
        }
    }
    
    public ArrayList<TablaPosiciones> clasificadosSemifinal(int idCampeonato){
        ArrayList<TablaPosiciones> clasificados = new ArrayList<>();
        TablaPosicionesSRV posicionesSRV = new TablaPosicionesSRV();
        ArrayList<TablaPosiciones> tps = posicionesSRV.get(1,idCampeonato , "Cuartos");
        Collections.sort(tps,new OrdenarTablaPosiciones());
        clasificados.add(tps.get(0));
        clasificados.add(tps.get(1));
        clasificados.add(tps.get(2));
        clasificados.add(tps.get(3));
        return clasificados;
    }
    
    public void semifinal(int idCampeonato, ArrayList<TablaPosiciones> clasificados) throws Exception{
        
        gjc = new GrupoJpaController(emf);
        gejc = new GrupoEquipoJpaController(emf);
        
        Equipo equipo = null;
        String idEtapa = "Semifinal";
        ArrayList<Equipo> equipos = new ArrayList<>();
        List<Equipo> resul = new ArrayList<>();
        TypedQuery<Equipo> query = null;
        for (TablaPosiciones clasificado : clasificados) {
            query = em.createNamedQuery("Equipo.findByNombre", Equipo.class);
            query.setParameter("nombre", clasificado.getNombre());
            resul = query.getResultList();
            Iterator it = resul.iterator();
            while(it.hasNext()){
                equipo = (Equipo) it.next();
            }
            equipos.add(equipo);
        }
        EtapaSRV etapaSRV = new EtapaSRV();
        GrupoSRV grupoSRV = new GrupoSRV();
        etapaSRV.add(idEtapa, idCampeonato, "Semifinal");
        grupoSRV.add(1, idEtapa , idCampeonato);
        Date date = new Date();
        GrupoPK grupoPK = new GrupoPK(1, idEtapa, idCampeonato);
        Grupo grupo = gjc.findGrupo(grupoPK);
        int posicion = 1;
        for (Equipo equipo1 : equipos) {
            GrupoEquipoPK equipoPK = new GrupoEquipoPK(equipo1.getIdEquipo(), 1, idEtapa, idCampeonato);
            GrupoEquipo grupoEquipo = new GrupoEquipo(equipoPK, posicion, date, grupo, equipo1);
            em.getTransaction().begin();
            gejc.create(grupoEquipo);
            em.getTransaction().commit();
            posicion++;
        }
    }
    
    public void jugarSemifinal(int idCampeonato) throws Exception{
        
        List<Integer> solucion = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            solucion.add(i);
        }
        Collections.shuffle(solucion);

        int E1 = solucion.get(0);
        int E2 = solucion.get(1);
        int E3 = solucion.get(2);
        int E4 = solucion.get(3);
        
        int Equipo1 = obtenerEquipo(idCampeonato, 1, E1, "Semifinal");
        int Equipo2 = obtenerEquipo(idCampeonato, 1, E2, "Semifinal");
        int Equipo3 = obtenerEquipo(idCampeonato, 1, E3, "Semifinal");
        int Equipo4 = obtenerEquipo(idCampeonato, 1, E4, "Semifinal");
        
        jugarPartido(Equipo1, Equipo2, idCampeonato, "Semifinal", 1);
        jugarPartido(Equipo3, Equipo4, idCampeonato, "Semifinal", 1);
        cerrar();
    }
        
    public ArrayList<TablaPosiciones> clasificadosFinal(int idCampeonato){
        ArrayList<TablaPosiciones> clasificados = new ArrayList<>();
        TablaPosicionesSRV posicionesSRV = new TablaPosicionesSRV();
        ArrayList<TablaPosiciones> tps = posicionesSRV.get(1,idCampeonato , "Semifinal");
        Collections.sort(tps,new OrdenarTablaPosiciones());
        clasificados.add(tps.get(0));
        clasificados.add(tps.get(1));
        return clasificados;
    }
    
    
    public void Final(int idCampeonato, ArrayList<TablaPosiciones> clasificados) throws Exception{
        
        gjc = new GrupoJpaController(emf);
        gejc = new GrupoEquipoJpaController(emf);
        
        Equipo equipo = null;
        String idEtapa = "Final";
        ArrayList<Equipo> equipos = new ArrayList<>();
        List<Equipo> resul = new ArrayList<>();
        TypedQuery<Equipo> query = null;
        for (TablaPosiciones clasificado : clasificados) {
            query = em.createNamedQuery("Equipo.findByNombre", Equipo.class);
            query.setParameter("nombre", clasificado.getNombre());
            resul = query.getResultList();
            Iterator it = resul.iterator();
            while(it.hasNext()){
                equipo = (Equipo) it.next();
            }
            equipos.add(equipo);
        }
        EtapaSRV etapaSRV = new EtapaSRV();
        GrupoSRV grupoSRV = new GrupoSRV();
        etapaSRV.add(idEtapa, idCampeonato, "final del campeonato");
        grupoSRV.add(1, idEtapa , idCampeonato);
        Date date = new Date();
        GrupoPK grupoPK = new GrupoPK(1, idEtapa, idCampeonato);
        Grupo grupo = gjc.findGrupo(grupoPK);
        int posicion = 1;
        for (Equipo equipo1 : equipos) {
            GrupoEquipoPK equipoPK = new GrupoEquipoPK(equipo1.getIdEquipo(), 1, idEtapa, idCampeonato);
            GrupoEquipo grupoEquipo = new GrupoEquipo(equipoPK, posicion, date, grupo, equipo1);
            em.getTransaction().begin();
            gejc.create(grupoEquipo);
            em.getTransaction().commit();
            posicion++;
        }
    }
    
    public void JugarFinal(int idCampeonato) throws Exception{
        
        int Equipo1 = obtenerEquipo(idCampeonato, 1, 1, "Final");
        int Equipo2 = obtenerEquipo(idCampeonato, 1, 2, "Final");
        jugarPartido(Equipo1, Equipo2, idCampeonato, "Final", 1);
        cerrar();
    }
    
    public TablaPosiciones getCampeon(int idCampeonato){
        TablaPosiciones clasificados = null;
        TablaPosicionesSRV posicionesSRV = new TablaPosicionesSRV();
        ArrayList<TablaPosiciones> tps = posicionesSRV.get(1,idCampeonato , "Final");
        Collections.sort(tps,new OrdenarTablaPosiciones());
        clasificados = tps.get(0);
        return clasificados;
    }
}

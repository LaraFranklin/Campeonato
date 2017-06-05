/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato;

import campeonato.DAO.CampeonatoJpaController;
import campeonato.DAO.EquipoJpaController;
import campeonato.DAO.GrupoEquipoJpaController;
import campeonato.DAO.GrupoJpaController;
import campeonato.DAO.JugadorJpaController;
import campeonato.GUI.PrincipalGUI;
import campeonato.SRV.CampeonatoSRV;
import campeonato.SRV.EquipoSRV;
import campeonato.SRV.GoleadoresSRV;
import campeonato.SRV.JugadorSRV;
import campeonato.SRV.MODELOS.Campeonato;
import campeonato.SRV.MODELOS.Equipo;
import campeonato.SRV.MODELOS.Goleador;
import campeonato.SRV.MODELOS.Grupo;
import campeonato.SRV.MODELOS.GrupoEquipo;
import campeonato.SRV.MODELOS.GrupoPK;
import campeonato.SRV.MODELOS.Jugador;
import campeonato.SRV.MODELOS.Partido;
import campeonato.SRV.MODELOS.TablaPosiciones;
import campeonato.SRV.OrdenarTablaPosiciones;
import campeonato.SRV.PartidosSRV;
import campeonato.SRV.TablaPosicionesSRV;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author lara
 */
public class CampeonatoPrincipal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        //PrincipalGUI principalGUI = new PrincipalGUI();
        //principalGUI.setVisible(true);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("campeonatoPU");
        EntityManager em = emf.createEntityManager();
        //TablaPosicionesSRV posicionesSRV = new TablaPosicionesSRV();
       // List<TablaPosiciones> lista = posicionesSRV.get(1, 13, "Primera Ronda");
        //Collections.sort(lista, new OrdenarTablaPosiciones()); 
        //for(TablaPosiciones tb : lista){
        //    System.out.println(tb.getNombre()+" " + tb.getPuntos()+" "+ tb.getGolesDiferencia());
        //}
        //JugadorSRV jugadorSRV = new JugadorSRV();
        //Jugador jugador = jugadorSRV.get("100221472");
        //System.out.println(jugador.getNombre()+" "+jugador.getApellidos());
        //Equipo e = jugador.getEquipo();
        //System.out.println(e.getNombre());
        //CampeonatoSRV campeonatoSRV = new CampeonatoSRV();
        /*System.out.println("aaa");
        for (TablaPosiciones tb : campeonatoSRV.clasificadosSemifinal(13)) {
        System.out.println(tb.getNombre()+ " " + tb.getGolesDiferencia()+ " " + tb.getPuntos());
    *///}
    /*int E1 = (int) (Math.random()*3+1);
    int E2 = (int) (Math.random()*3+1);
    
    while(E1 == E2 ){
    E1 = (int) (Math.random()*3+1);
    E2 = (int) (Math.random()*3+1);
    }
    
    int N1 = (int) (Math.random()*3+1);
    int N2 = (int) (Math.random()*3+1);
    
    while(N1 == E1 || N1 == E2){
    N1 = (int) (Math.random()*3+1);
    }
    
    while(N2 == E1 || N2 == E2 || N2 == N1){
    N2 = (int) (Math.random()*3+1);
    }
    
    System.out.println("E1: "+E1);
    System.out.println("E2: "+E2);
    System.out.println("N1: "+N1);
    System.out.println("N2: "+N2);*/
    
        PartidosSRV partidosSRV = new PartidosSRV();
        for (Partido p : partidosSRV.get(13, "Primera Ronda")) {
            System.out.println(p.getIdEquipoL()+" "+p.getIdEquipoV()+" "+p.getMarcadorL()+" "+p.getMarcadorV());
        }
    
        /* CampeonatoSRV campeonatoSRV = new CampeonatoSRV();
        campeonatoSRV.clasificados(13);
        campeonatoSRV.cuartos(13, campeonatoSRV.clasificados(13));
        //System.out.println(posicionesSRV.getEquipo(2, 13, "Primera Ronda", 1).getPartidosGanados());
//em.getTransaction().begin();
        /*TypedQuery<Partido> query = em.createNamedQuery("Partido.findForTabla", Partido.class);
        query.setParameter("idCampeonato", 13);
        query.setParameter("idGrupo", 1);
        query.setParameter("idEtapa", "Primera Ronda");
        // query.setParameter("idEquipoL", 2);
        //query.setParameter("idEquipoV", 2);
        List<Partido> lista = new ArrayList<>();
        lista = query.getResultList();
        int equipo = 0;
        Iterator<Partido> it = lista.iterator();
        while(it.hasNext()){
        System.out.println(it.next().getIdEquipoV());
        }*/
        //campeonatoSRV.AsignarJugadores(11);
        //campeonatoSRV.AgregarEquipos("Primera Ronda", 4, 4, 13);
        
        //Scalar function
        /*Query q = em.createNativeQuery("SELECT nombre FROM campeonato.Jugador");
        List<Object[]> authors = q.getResultList();
        
        for (Object[] a : authors) {
        System.out.println("Author "
        + a[0]
        /*+ " "
        + a[1]);
        }*/
        
        /*Query query = em.createQuery("SELECT nombre FROM campeonato.Jugador");
        List<String> list= query.getResultList();
        
        for(String e: list)
        {
        System.out.println("Employee NAME :"+e);
        }
        GrupoJpaController gejc = new GrupoJpaController(emf);
        GrupoPK gpk = new GrupoPK(1, "Primera Ronda", 13);
        Grupo grupo = gejc.findGrupo(gpk);
        for (GrupoEquipo e : grupo.getGrupoEquipoCollection()) {
            System.out.println(e.getEquipo().getNombre());
        }
        /*EquipoSRV equipoSRV = new EquipoSRV();
        Equipo equipo = equipoSRV.get(2);
        List<Jugador> lista = (List<Jugador>) equipo.getJugadorCollection();
        for (Jugador jugador : lista) {
        System.out.println(jugador.getNombre());
        }
        /
        em.close();
        emf.close();*/
    }
    
}

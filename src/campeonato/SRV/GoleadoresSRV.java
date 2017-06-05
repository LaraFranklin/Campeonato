/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV;

import campeonato.SRV.MODELOS.Gol;
import campeonato.SRV.MODELOS.Goleador;
import campeonato.SRV.MODELOS.Jugador;
import campeonato.SRV.MODELOS.Partido;
import campeonato.SRV.MODELOS.TablaPosiciones;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author lara
 */
public class GoleadoresSRV {
    private EntityManagerFactory emf ;
    private EntityManager em ;

    public GoleadoresSRV() {
        emf = Persistence.createEntityManagerFactory("campeonatoPU");
        em = emf.createEntityManager();
    }
    
    public ArrayList<Goleador> getGoleadores(int idCampeonato){
        //String consulta = "SELECT g.idJugador,count(idGol) goles FROM gol g where g.idCampeonato = :idCampeonato GROUP BY (g.idJugador) ORDER BY g.goles DESC";
        ArrayList<Goleador> goleadores = new ArrayList<>();
        ArrayList<String> goles = new ArrayList<>();
        TreeSet<String> jugadores = new TreeSet<>();
        TypedQuery<Gol> query = em.createNamedQuery("Gol.findByIdCampeonato", Gol.class);
            query.setParameter("idCampeonato", idCampeonato);
        List<Gol> lista = new ArrayList<>();
        lista = query.getResultList();
        int golesHechos = 0;
        Iterator<Gol> it = lista.iterator();
        while(it.hasNext()){
            Gol gol = it.next();
            jugadores.add(gol.getGolPK().getIdJugador());
            goles.add(gol.getGolPK().getIdJugador());
        }
        JugadorSRV jugadorSRV = new JugadorSRV();
        for (String jugador : jugadores) {
            golesHechos = Collections.frequency(goles, jugador);
            Jugador j = jugadorSRV.get(jugador);
            Goleador goleador = new Goleador(j.getNombre()+" "+j.getApellidos(), j.getEquipo().getNombre(), golesHechos);
            goleadores.add(goleador);
        }
        System.out.println("as");
        Collections.sort(goleadores, new OrdenarGoleadores());
        return goleadores;
    }
    
}

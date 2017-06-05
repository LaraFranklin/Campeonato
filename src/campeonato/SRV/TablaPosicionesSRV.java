/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV;

import campeonato.DAO.EquipoJpaController;
import campeonato.DAO.EtapaJpaController;
import campeonato.SRV.MODELOS.GrupoEquipo;
import campeonato.SRV.MODELOS.Partido;
import campeonato.SRV.MODELOS.TablaPosiciones;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author lara
 */
public class TablaPosicionesSRV {
    
    private EntityManagerFactory emf ;
    private EntityManager em ;
    EquipoJpaController ejc;

    public TablaPosicionesSRV() {
        emf = Persistence.createEntityManagerFactory("campeonatoPU");
        em = emf.createEntityManager();
    }
    
    public ArrayList<TablaPosiciones> get(int idGrupo, int idCampeonato, String idEtapa){
        TypedQuery<GrupoEquipo> query = em.createNamedQuery("GrupoEquipo.findForTabla", GrupoEquipo.class);
        query.setParameter("idGrupo", idGrupo); 
        query.setParameter("idCampeonato", idCampeonato);
        query.setParameter("idEtapa", idEtapa);
        
        ArrayList<TablaPosiciones> tabla = new ArrayList<>();
        List<GrupoEquipo> lista = new ArrayList<>();
        lista = query.getResultList();
        int equipo = 0;
        Iterator<GrupoEquipo> it = lista.iterator();
        while(it.hasNext()){
            equipo = it.next().getEquipo().getIdEquipo();
            tabla.add(getEquipo(equipo, idCampeonato, idEtapa, idGrupo));
            //manipular el retorno
        }
        Collections.sort(tabla, new OrdenarTablaPosiciones());
        return tabla;
    }
    
    public TablaPosiciones  getEquipo(int idEquipo, int idCampeonato, String idEtapa,int idGrupo){
        
        ejc = new EquipoJpaController(emf);
        EquipoSRV equipoSRV = new EquipoSRV();
        TypedQuery<Partido> query = em.createNamedQuery("Partido.findForTabla", Partido.class);
            query.setParameter("idCampeonato", idCampeonato);
            query.setParameter("idGrupo", idGrupo); 
            query.setParameter("idEtapa", idEtapa);
           // query.setParameter("idEquipoL", 2);
            //query.setParameter("idEquipoV", 2);
        List<Partido> lista = new ArrayList<>();
        lista = query.getResultList();
        Partido  partido = new Partido();
        TablaPosiciones tp = new TablaPosiciones(equipoSRV.get(idEquipo).getNombre());
        Iterator<Partido> it = lista.iterator();
        while(it.hasNext()){
            partido = it.next();
            if(partido.getIdEquipoL().getIdEquipo() == idEquipo){
                if(partido.getMarcadorL() > partido.getMarcadorV()){
                    tp.addPG();
                }else if(partido.getMarcadorL() == partido.getMarcadorV()){
                    tp.addPE();
                }else{
                    tp.addPP();
                }
                tp.addGolesFavor(partido.getMarcadorL());
                tp.addGolesContra(partido.getMarcadorV());
            }else if(partido.getIdEquipoV().getIdEquipo() == idEquipo){
                if(partido.getMarcadorV() > partido.getMarcadorL()){
                    tp.addPG();
                }else if(partido.getMarcadorV() == partido.getMarcadorL()){
                    tp.addPE();
                }else{
                    tp.addPP();
                }
            }
        }
        return tp;
    }

    
}

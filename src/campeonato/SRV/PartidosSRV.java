/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV;

import campeonato.DAO.EquipoJpaController;
import campeonato.DAO.PartidoJpaController;
import campeonato.SRV.MODELOS.Equipo;
import campeonato.SRV.MODELOS.Partido;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author lara
 */
public class PartidosSRV {
    private EntityManagerFactory emf ;
    private EntityManager em ;
    private PartidoJpaController service;

    public PartidosSRV() {
        emf = Persistence.createEntityManagerFactory("campeonatoPU");
        em = emf.createEntityManager();
        service = new PartidoJpaController(emf);
    }
    
    public List<Partido> get(int idCampeonato, String idEtapa){
        
        em.getTransaction().begin();
        TypedQuery<Partido> query = em.createNamedQuery("Partido.findByIdEtapa", Partido.class);
        query.setParameter("idCampeonato", idCampeonato); 
        query.setParameter("idEtapa", idEtapa); 
        List<Partido> partidos = query.getResultList();
       
        return  partidos;
    }
    
    
}

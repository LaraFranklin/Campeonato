/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV;

import campeonato.DAO.EquipoJpaController;
import campeonato.DAO.EtapaJpaController;
import campeonato.SRV.MODELOS.Etapa;
import campeonato.SRV.MODELOS.EtapaPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author lara
 */
public class EtapaSRV {
    private EntityManagerFactory emf ;
    private EntityManager em ;
    private EtapaJpaController service;

    public EtapaSRV() {
        emf = Persistence.createEntityManagerFactory("campeonatoPU");
        em = emf.createEntityManager();
        service = new EtapaJpaController(emf);
    }
    
    private void cerrar(){
        em.close();
        emf.close();
    }
    
    public void add(String idEtapa, int idCampeonato, String descripcion) throws Exception{
        if(idEtapa.trim().length() == 0 || idEtapa == null){
            throw new IllegalArgumentException("No puede estar vacio el nombre de la etapa");
        }
        CampeonatoSRV campeonatoSRV = new CampeonatoSRV();
        EtapaPK epk = new EtapaPK(idEtapa, idCampeonato);
        Etapa etapa = new Etapa(epk, descripcion, campeonatoSRV.get(idCampeonato));
        em.getTransaction().begin();
        service.create(etapa);
        em.getTransaction().commit();
        cerrar();
    }
    
    public Etapa get(EtapaPK id){
        return service.findEtapa(id);
    }
    
    public List<Etapa> getAll(){
        return service.findEtapaEntities();
    }
    
}

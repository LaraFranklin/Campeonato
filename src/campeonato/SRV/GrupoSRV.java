/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV;

import campeonato.DAO.GrupoJpaController;
import campeonato.SRV.MODELOS.Etapa;
import campeonato.SRV.MODELOS.EtapaPK;
import campeonato.SRV.MODELOS.Grupo;
import campeonato.SRV.MODELOS.GrupoPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author lara
 */
public class GrupoSRV {
    private EntityManagerFactory emf ;
    private EntityManager em ;
    private GrupoJpaController service;

    public GrupoSRV() {
        emf = Persistence.createEntityManagerFactory("campeonatoPU");
        em = emf.createEntityManager();
        service = new GrupoJpaController(emf);
    }
    
    private void cerrar(){
        em.close();
        emf.close();
    }
    public void add(int idGrupo, String idEtapa, int idCampeonato) throws Exception{
        GrupoPK gpk = new GrupoPK(idGrupo, idEtapa, idCampeonato);
        EtapaSRV etapaSRV = new EtapaSRV();
        EtapaPK epk = new EtapaPK(idEtapa, idCampeonato);
        Grupo grupo = new Grupo(gpk, etapaSRV.get(epk));
        em.getTransaction().begin();
        service.create(grupo);
        em.getTransaction().commit();
        cerrar();
    }
    
    public Grupo get(GrupoPK gpk){
        return service.findGrupo(gpk);
    }
    
    public List<Grupo> getAll(){
        return service.findGrupoEntities();
    }
    
    
}

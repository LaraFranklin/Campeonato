/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.DAO;

import campeonato.DAO.exceptions.IllegalOrphanException;
import campeonato.DAO.exceptions.NonexistentEntityException;
import campeonato.DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import campeonato.SRV.MODELOS.Campeonato;
import campeonato.SRV.MODELOS.Etapa;
import campeonato.SRV.MODELOS.EtapaPK;
import campeonato.SRV.MODELOS.Grupo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lara
 */
public class EtapaJpaController implements Serializable {

    public EtapaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Etapa etapa) throws PreexistingEntityException, Exception {
        if (etapa.getEtapaPK() == null) {
            etapa.setEtapaPK(new EtapaPK());
        }
        if (etapa.getGrupoCollection() == null) {
            etapa.setGrupoCollection(new ArrayList<Grupo>());
        }
        etapa.getEtapaPK().setIdCampeonato(etapa.getCampeonato().getIdCampeonato());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Campeonato campeonato = etapa.getCampeonato();
            if (campeonato != null) {
                campeonato = em.getReference(campeonato.getClass(), campeonato.getIdCampeonato());
                etapa.setCampeonato(campeonato);
            }
            Collection<Grupo> attachedGrupoCollection = new ArrayList<Grupo>();
            for (Grupo grupoCollectionGrupoToAttach : etapa.getGrupoCollection()) {
                grupoCollectionGrupoToAttach = em.getReference(grupoCollectionGrupoToAttach.getClass(), grupoCollectionGrupoToAttach.getGrupoPK());
                attachedGrupoCollection.add(grupoCollectionGrupoToAttach);
            }
            etapa.setGrupoCollection(attachedGrupoCollection);
            em.persist(etapa);
            if (campeonato != null) {
                campeonato.getEtapaCollection().add(etapa);
                campeonato = em.merge(campeonato);
            }
            for (Grupo grupoCollectionGrupo : etapa.getGrupoCollection()) {
                Etapa oldEtapaOfGrupoCollectionGrupo = grupoCollectionGrupo.getEtapa();
                grupoCollectionGrupo.setEtapa(etapa);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
                if (oldEtapaOfGrupoCollectionGrupo != null) {
                    oldEtapaOfGrupoCollectionGrupo.getGrupoCollection().remove(grupoCollectionGrupo);
                    oldEtapaOfGrupoCollectionGrupo = em.merge(oldEtapaOfGrupoCollectionGrupo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEtapa(etapa.getEtapaPK()) != null) {
                throw new PreexistingEntityException("Etapa " + etapa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Etapa etapa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        etapa.getEtapaPK().setIdCampeonato(etapa.getCampeonato().getIdCampeonato());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Etapa persistentEtapa = em.find(Etapa.class, etapa.getEtapaPK());
            Campeonato campeonatoOld = persistentEtapa.getCampeonato();
            Campeonato campeonatoNew = etapa.getCampeonato();
            Collection<Grupo> grupoCollectionOld = persistentEtapa.getGrupoCollection();
            Collection<Grupo> grupoCollectionNew = etapa.getGrupoCollection();
            List<String> illegalOrphanMessages = null;
            for (Grupo grupoCollectionOldGrupo : grupoCollectionOld) {
                if (!grupoCollectionNew.contains(grupoCollectionOldGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Grupo " + grupoCollectionOldGrupo + " since its etapa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (campeonatoNew != null) {
                campeonatoNew = em.getReference(campeonatoNew.getClass(), campeonatoNew.getIdCampeonato());
                etapa.setCampeonato(campeonatoNew);
            }
            Collection<Grupo> attachedGrupoCollectionNew = new ArrayList<Grupo>();
            for (Grupo grupoCollectionNewGrupoToAttach : grupoCollectionNew) {
                grupoCollectionNewGrupoToAttach = em.getReference(grupoCollectionNewGrupoToAttach.getClass(), grupoCollectionNewGrupoToAttach.getGrupoPK());
                attachedGrupoCollectionNew.add(grupoCollectionNewGrupoToAttach);
            }
            grupoCollectionNew = attachedGrupoCollectionNew;
            etapa.setGrupoCollection(grupoCollectionNew);
            etapa = em.merge(etapa);
            if (campeonatoOld != null && !campeonatoOld.equals(campeonatoNew)) {
                campeonatoOld.getEtapaCollection().remove(etapa);
                campeonatoOld = em.merge(campeonatoOld);
            }
            if (campeonatoNew != null && !campeonatoNew.equals(campeonatoOld)) {
                campeonatoNew.getEtapaCollection().add(etapa);
                campeonatoNew = em.merge(campeonatoNew);
            }
            for (Grupo grupoCollectionNewGrupo : grupoCollectionNew) {
                if (!grupoCollectionOld.contains(grupoCollectionNewGrupo)) {
                    Etapa oldEtapaOfGrupoCollectionNewGrupo = grupoCollectionNewGrupo.getEtapa();
                    grupoCollectionNewGrupo.setEtapa(etapa);
                    grupoCollectionNewGrupo = em.merge(grupoCollectionNewGrupo);
                    if (oldEtapaOfGrupoCollectionNewGrupo != null && !oldEtapaOfGrupoCollectionNewGrupo.equals(etapa)) {
                        oldEtapaOfGrupoCollectionNewGrupo.getGrupoCollection().remove(grupoCollectionNewGrupo);
                        oldEtapaOfGrupoCollectionNewGrupo = em.merge(oldEtapaOfGrupoCollectionNewGrupo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EtapaPK id = etapa.getEtapaPK();
                if (findEtapa(id) == null) {
                    throw new NonexistentEntityException("The etapa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EtapaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Etapa etapa;
            try {
                etapa = em.getReference(Etapa.class, id);
                etapa.getEtapaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The etapa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Grupo> grupoCollectionOrphanCheck = etapa.getGrupoCollection();
            for (Grupo grupoCollectionOrphanCheckGrupo : grupoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Etapa (" + etapa + ") cannot be destroyed since the Grupo " + grupoCollectionOrphanCheckGrupo + " in its grupoCollection field has a non-nullable etapa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Campeonato campeonato = etapa.getCampeonato();
            if (campeonato != null) {
                campeonato.getEtapaCollection().remove(etapa);
                campeonato = em.merge(campeonato);
            }
            em.remove(etapa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Etapa> findEtapaEntities() {
        return findEtapaEntities(true, -1, -1);
    }

    public List<Etapa> findEtapaEntities(int maxResults, int firstResult) {
        return findEtapaEntities(false, maxResults, firstResult);
    }

    private List<Etapa> findEtapaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Etapa.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Etapa findEtapa(EtapaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Etapa.class, id);
        } finally {
            em.close();
        }
    }

    public int getEtapaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Etapa> rt = cq.from(Etapa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

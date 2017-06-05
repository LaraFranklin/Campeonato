/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.DAO;

import campeonato.DAO.exceptions.IllegalOrphanException;
import campeonato.DAO.exceptions.NonexistentEntityException;
import campeonato.SRV.MODELOS.Campeonato;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import campeonato.SRV.MODELOS.Etapa;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lara
 */
public class CampeonatoJpaController implements Serializable {

    public CampeonatoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Campeonato campeonato) {
        if (campeonato.getEtapaCollection() == null) {
            campeonato.setEtapaCollection(new ArrayList<Etapa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Etapa> attachedEtapaCollection = new ArrayList<Etapa>();
            for (Etapa etapaCollectionEtapaToAttach : campeonato.getEtapaCollection()) {
                etapaCollectionEtapaToAttach = em.getReference(etapaCollectionEtapaToAttach.getClass(), etapaCollectionEtapaToAttach.getEtapaPK());
                attachedEtapaCollection.add(etapaCollectionEtapaToAttach);
            }
            campeonato.setEtapaCollection(attachedEtapaCollection);
            em.persist(campeonato);
            for (Etapa etapaCollectionEtapa : campeonato.getEtapaCollection()) {
                Campeonato oldCampeonatoOfEtapaCollectionEtapa = etapaCollectionEtapa.getCampeonato();
                etapaCollectionEtapa.setCampeonato(campeonato);
                etapaCollectionEtapa = em.merge(etapaCollectionEtapa);
                if (oldCampeonatoOfEtapaCollectionEtapa != null) {
                    oldCampeonatoOfEtapaCollectionEtapa.getEtapaCollection().remove(etapaCollectionEtapa);
                    oldCampeonatoOfEtapaCollectionEtapa = em.merge(oldCampeonatoOfEtapaCollectionEtapa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Campeonato campeonato) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Campeonato persistentCampeonato = em.find(Campeonato.class, campeonato.getIdCampeonato());
            Collection<Etapa> etapaCollectionOld = persistentCampeonato.getEtapaCollection();
            Collection<Etapa> etapaCollectionNew = campeonato.getEtapaCollection();
            List<String> illegalOrphanMessages = null;
            for (Etapa etapaCollectionOldEtapa : etapaCollectionOld) {
                if (!etapaCollectionNew.contains(etapaCollectionOldEtapa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Etapa " + etapaCollectionOldEtapa + " since its campeonato field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Etapa> attachedEtapaCollectionNew = new ArrayList<Etapa>();
            for (Etapa etapaCollectionNewEtapaToAttach : etapaCollectionNew) {
                etapaCollectionNewEtapaToAttach = em.getReference(etapaCollectionNewEtapaToAttach.getClass(), etapaCollectionNewEtapaToAttach.getEtapaPK());
                attachedEtapaCollectionNew.add(etapaCollectionNewEtapaToAttach);
            }
            etapaCollectionNew = attachedEtapaCollectionNew;
            campeonato.setEtapaCollection(etapaCollectionNew);
            campeonato = em.merge(campeonato);
            for (Etapa etapaCollectionNewEtapa : etapaCollectionNew) {
                if (!etapaCollectionOld.contains(etapaCollectionNewEtapa)) {
                    Campeonato oldCampeonatoOfEtapaCollectionNewEtapa = etapaCollectionNewEtapa.getCampeonato();
                    etapaCollectionNewEtapa.setCampeonato(campeonato);
                    etapaCollectionNewEtapa = em.merge(etapaCollectionNewEtapa);
                    if (oldCampeonatoOfEtapaCollectionNewEtapa != null && !oldCampeonatoOfEtapaCollectionNewEtapa.equals(campeonato)) {
                        oldCampeonatoOfEtapaCollectionNewEtapa.getEtapaCollection().remove(etapaCollectionNewEtapa);
                        oldCampeonatoOfEtapaCollectionNewEtapa = em.merge(oldCampeonatoOfEtapaCollectionNewEtapa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = campeonato.getIdCampeonato();
                if (findCampeonato(id) == null) {
                    throw new NonexistentEntityException("The campeonato with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Campeonato campeonato;
            try {
                campeonato = em.getReference(Campeonato.class, id);
                campeonato.getIdCampeonato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The campeonato with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Etapa> etapaCollectionOrphanCheck = campeonato.getEtapaCollection();
            for (Etapa etapaCollectionOrphanCheckEtapa : etapaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Campeonato (" + campeonato + ") cannot be destroyed since the Etapa " + etapaCollectionOrphanCheckEtapa + " in its etapaCollection field has a non-nullable campeonato field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(campeonato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Campeonato> findCampeonatoEntities() {
        return findCampeonatoEntities(true, -1, -1);
    }

    public List<Campeonato> findCampeonatoEntities(int maxResults, int firstResult) {
        return findCampeonatoEntities(false, maxResults, firstResult);
    }

    private List<Campeonato> findCampeonatoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Campeonato.class));
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

    public Campeonato findCampeonato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Campeonato.class, id);
        } finally {
            em.close();
        }
    }

    public int getCampeonatoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Campeonato> rt = cq.from(Campeonato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

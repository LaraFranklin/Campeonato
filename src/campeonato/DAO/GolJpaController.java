/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.DAO;

import campeonato.DAO.exceptions.NonexistentEntityException;
import campeonato.DAO.exceptions.PreexistingEntityException;
import campeonato.SRV.MODELOS.Gol;
import campeonato.SRV.MODELOS.GolPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import campeonato.SRV.MODELOS.Partido;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lara
 */
public class GolJpaController implements Serializable {

    public GolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gol gol) throws PreexistingEntityException, Exception {
        if (gol.getGolPK() == null) {
            gol.setGolPK(new GolPK());
        }
        gol.getGolPK().setIdCampeonato(gol.getPartido().getPartidoPK().getIdCampeonato());
        gol.getGolPK().setIdEtapa(gol.getPartido().getPartidoPK().getIdEtapa());
        gol.getGolPK().setIdPartido(gol.getPartido().getPartidoPK().getIdPartido());
        gol.getGolPK().setIdGrupo(gol.getPartido().getPartidoPK().getIdGrupo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partido partido = gol.getPartido();
            if (partido != null) {
                partido = em.getReference(partido.getClass(), partido.getPartidoPK());
                gol.setPartido(partido);
            }
            em.persist(gol);
            if (partido != null) {
                partido.getGolCollection().add(gol);
                partido = em.merge(partido);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGol(gol.getGolPK()) != null) {
                throw new PreexistingEntityException("Gol " + gol + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gol gol) throws NonexistentEntityException, Exception {
        gol.getGolPK().setIdCampeonato(gol.getPartido().getPartidoPK().getIdCampeonato());
        gol.getGolPK().setIdEtapa(gol.getPartido().getPartidoPK().getIdEtapa());
        gol.getGolPK().setIdPartido(gol.getPartido().getPartidoPK().getIdPartido());
        gol.getGolPK().setIdGrupo(gol.getPartido().getPartidoPK().getIdGrupo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gol persistentGol = em.find(Gol.class, gol.getGolPK());
            Partido partidoOld = persistentGol.getPartido();
            Partido partidoNew = gol.getPartido();
            if (partidoNew != null) {
                partidoNew = em.getReference(partidoNew.getClass(), partidoNew.getPartidoPK());
                gol.setPartido(partidoNew);
            }
            gol = em.merge(gol);
            if (partidoOld != null && !partidoOld.equals(partidoNew)) {
                partidoOld.getGolCollection().remove(gol);
                partidoOld = em.merge(partidoOld);
            }
            if (partidoNew != null && !partidoNew.equals(partidoOld)) {
                partidoNew.getGolCollection().add(gol);
                partidoNew = em.merge(partidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                GolPK id = gol.getGolPK();
                if (findGol(id) == null) {
                    throw new NonexistentEntityException("The gol with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(GolPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gol gol;
            try {
                gol = em.getReference(Gol.class, id);
                gol.getGolPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gol with id " + id + " no longer exists.", enfe);
            }
            Partido partido = gol.getPartido();
            if (partido != null) {
                partido.getGolCollection().remove(gol);
                partido = em.merge(partido);
            }
            em.remove(gol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gol> findGolEntities() {
        return findGolEntities(true, -1, -1);
    }

    public List<Gol> findGolEntities(int maxResults, int firstResult) {
        return findGolEntities(false, maxResults, firstResult);
    }

    private List<Gol> findGolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gol.class));
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

    public Gol findGol(GolPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gol.class, id);
        } finally {
            em.close();
        }
    }

    public int getGolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gol> rt = cq.from(Gol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

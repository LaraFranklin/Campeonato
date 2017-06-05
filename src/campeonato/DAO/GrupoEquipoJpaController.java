/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.DAO;

import campeonato.DAO.exceptions.NonexistentEntityException;
import campeonato.DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import campeonato.SRV.MODELOS.Grupo;
import campeonato.SRV.MODELOS.Equipo;
import campeonato.SRV.MODELOS.GrupoEquipo;
import campeonato.SRV.MODELOS.GrupoEquipoPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lara
 */
public class GrupoEquipoJpaController implements Serializable {

    public GrupoEquipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GrupoEquipo grupoEquipo) throws PreexistingEntityException, Exception {
        if (grupoEquipo.getGrupoEquipoPK() == null) {
            grupoEquipo.setGrupoEquipoPK(new GrupoEquipoPK());
        }
        grupoEquipo.getGrupoEquipoPK().setIdEtapa(grupoEquipo.getGrupo().getGrupoPK().getIdEtapa());
        grupoEquipo.getGrupoEquipoPK().setIdCampeonato(grupoEquipo.getGrupo().getGrupoPK().getIdCampeonato());
        grupoEquipo.getGrupoEquipoPK().setIdEquipo(grupoEquipo.getEquipo().getIdEquipo());
        grupoEquipo.getGrupoEquipoPK().setIdGrupo(grupoEquipo.getGrupo().getGrupoPK().getIdGrupo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo grupo = grupoEquipo.getGrupo();
            if (grupo != null) {
                grupo = em.getReference(grupo.getClass(), grupo.getGrupoPK());
                grupoEquipo.setGrupo(grupo);
            }
            Equipo equipo = grupoEquipo.getEquipo();
            if (equipo != null) {
                equipo = em.getReference(equipo.getClass(), equipo.getIdEquipo());
                grupoEquipo.setEquipo(equipo);
            }
            em.persist(grupoEquipo);
            if (grupo != null) {
                grupo.getGrupoEquipoCollection().add(grupoEquipo);
                grupo = em.merge(grupo);
            }
            if (equipo != null) {
                equipo.getGrupoEquipoCollection().add(grupoEquipo);
                equipo = em.merge(equipo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGrupoEquipo(grupoEquipo.getGrupoEquipoPK()) != null) {
                throw new PreexistingEntityException("GrupoEquipo " + grupoEquipo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GrupoEquipo grupoEquipo) throws NonexistentEntityException, Exception {
        grupoEquipo.getGrupoEquipoPK().setIdEtapa(grupoEquipo.getGrupo().getGrupoPK().getIdEtapa());
        grupoEquipo.getGrupoEquipoPK().setIdCampeonato(grupoEquipo.getGrupo().getGrupoPK().getIdCampeonato());
        grupoEquipo.getGrupoEquipoPK().setIdEquipo(grupoEquipo.getEquipo().getIdEquipo());
        grupoEquipo.getGrupoEquipoPK().setIdGrupo(grupoEquipo.getGrupo().getGrupoPK().getIdGrupo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoEquipo persistentGrupoEquipo = em.find(GrupoEquipo.class, grupoEquipo.getGrupoEquipoPK());
            Grupo grupoOld = persistentGrupoEquipo.getGrupo();
            Grupo grupoNew = grupoEquipo.getGrupo();
            Equipo equipoOld = persistentGrupoEquipo.getEquipo();
            Equipo equipoNew = grupoEquipo.getEquipo();
            if (grupoNew != null) {
                grupoNew = em.getReference(grupoNew.getClass(), grupoNew.getGrupoPK());
                grupoEquipo.setGrupo(grupoNew);
            }
            if (equipoNew != null) {
                equipoNew = em.getReference(equipoNew.getClass(), equipoNew.getIdEquipo());
                grupoEquipo.setEquipo(equipoNew);
            }
            grupoEquipo = em.merge(grupoEquipo);
            if (grupoOld != null && !grupoOld.equals(grupoNew)) {
                grupoOld.getGrupoEquipoCollection().remove(grupoEquipo);
                grupoOld = em.merge(grupoOld);
            }
            if (grupoNew != null && !grupoNew.equals(grupoOld)) {
                grupoNew.getGrupoEquipoCollection().add(grupoEquipo);
                grupoNew = em.merge(grupoNew);
            }
            if (equipoOld != null && !equipoOld.equals(equipoNew)) {
                equipoOld.getGrupoEquipoCollection().remove(grupoEquipo);
                equipoOld = em.merge(equipoOld);
            }
            if (equipoNew != null && !equipoNew.equals(equipoOld)) {
                equipoNew.getGrupoEquipoCollection().add(grupoEquipo);
                equipoNew = em.merge(equipoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                GrupoEquipoPK id = grupoEquipo.getGrupoEquipoPK();
                if (findGrupoEquipo(id) == null) {
                    throw new NonexistentEntityException("The grupoEquipo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(GrupoEquipoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoEquipo grupoEquipo;
            try {
                grupoEquipo = em.getReference(GrupoEquipo.class, id);
                grupoEquipo.getGrupoEquipoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupoEquipo with id " + id + " no longer exists.", enfe);
            }
            Grupo grupo = grupoEquipo.getGrupo();
            if (grupo != null) {
                grupo.getGrupoEquipoCollection().remove(grupoEquipo);
                grupo = em.merge(grupo);
            }
            Equipo equipo = grupoEquipo.getEquipo();
            if (equipo != null) {
                equipo.getGrupoEquipoCollection().remove(grupoEquipo);
                equipo = em.merge(equipo);
            }
            em.remove(grupoEquipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GrupoEquipo> findGrupoEquipoEntities() {
        return findGrupoEquipoEntities(true, -1, -1);
    }

    public List<GrupoEquipo> findGrupoEquipoEntities(int maxResults, int firstResult) {
        return findGrupoEquipoEntities(false, maxResults, firstResult);
    }

    private List<GrupoEquipo> findGrupoEquipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GrupoEquipo.class));
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

    public GrupoEquipo findGrupoEquipo(GrupoEquipoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GrupoEquipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoEquipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GrupoEquipo> rt = cq.from(GrupoEquipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

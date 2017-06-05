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
import campeonato.SRV.MODELOS.Grupo;
import campeonato.SRV.MODELOS.Equipo;
import campeonato.SRV.MODELOS.Gol;
import campeonato.SRV.MODELOS.Partido;
import campeonato.SRV.MODELOS.PartidoPK;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lara
 */
public class PartidoJpaController implements Serializable {

    public PartidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Partido partido) throws PreexistingEntityException, Exception {
        if (partido.getPartidoPK() == null) {
            partido.setPartidoPK(new PartidoPK());
        }
        if (partido.getGolCollection() == null) {
            partido.setGolCollection(new ArrayList<Gol>());
        }
        partido.getPartidoPK().setIdGrupo(partido.getGrupo().getGrupoPK().getIdGrupo());
        partido.getPartidoPK().setIdEtapa(partido.getGrupo().getGrupoPK().getIdEtapa());
        partido.getPartidoPK().setIdCampeonato(partido.getGrupo().getGrupoPK().getIdCampeonato());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo grupo = partido.getGrupo();
            if (grupo != null) {
                grupo = em.getReference(grupo.getClass(), grupo.getGrupoPK());
                partido.setGrupo(grupo);
            }
            Equipo idEquipoL = partido.getIdEquipoL();
            if (idEquipoL != null) {
                idEquipoL = em.getReference(idEquipoL.getClass(), idEquipoL.getIdEquipo());
                partido.setIdEquipoL(idEquipoL);
            }
            Equipo idEquipoV = partido.getIdEquipoV();
            if (idEquipoV != null) {
                idEquipoV = em.getReference(idEquipoV.getClass(), idEquipoV.getIdEquipo());
                partido.setIdEquipoV(idEquipoV);
            }
            Collection<Gol> attachedGolCollection = new ArrayList<Gol>();
            for (Gol golCollectionGolToAttach : partido.getGolCollection()) {
                golCollectionGolToAttach = em.getReference(golCollectionGolToAttach.getClass(), golCollectionGolToAttach.getGolPK());
                attachedGolCollection.add(golCollectionGolToAttach);
            }
            partido.setGolCollection(attachedGolCollection);
            em.persist(partido);
            if (grupo != null) {
                grupo.getPartidoCollection().add(partido);
                grupo = em.merge(grupo);
            }
            if (idEquipoL != null) {
                idEquipoL.getPartidoCollection().add(partido);
                idEquipoL = em.merge(idEquipoL);
            }
            if (idEquipoV != null) {
                idEquipoV.getPartidoCollection().add(partido);
                idEquipoV = em.merge(idEquipoV);
            }
            for (Gol golCollectionGol : partido.getGolCollection()) {
                Partido oldPartidoOfGolCollectionGol = golCollectionGol.getPartido();
                golCollectionGol.setPartido(partido);
                golCollectionGol = em.merge(golCollectionGol);
                if (oldPartidoOfGolCollectionGol != null) {
                    oldPartidoOfGolCollectionGol.getGolCollection().remove(golCollectionGol);
                    oldPartidoOfGolCollectionGol = em.merge(oldPartidoOfGolCollectionGol);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPartido(partido.getPartidoPK()) != null) {
                throw new PreexistingEntityException("Partido " + partido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Partido partido) throws IllegalOrphanException, NonexistentEntityException, Exception {
        partido.getPartidoPK().setIdGrupo(partido.getGrupo().getGrupoPK().getIdGrupo());
        partido.getPartidoPK().setIdEtapa(partido.getGrupo().getGrupoPK().getIdEtapa());
        partido.getPartidoPK().setIdCampeonato(partido.getGrupo().getGrupoPK().getIdCampeonato());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partido persistentPartido = em.find(Partido.class, partido.getPartidoPK());
            Grupo grupoOld = persistentPartido.getGrupo();
            Grupo grupoNew = partido.getGrupo();
            Equipo idEquipoLOld = persistentPartido.getIdEquipoL();
            Equipo idEquipoLNew = partido.getIdEquipoL();
            Equipo idEquipoVOld = persistentPartido.getIdEquipoV();
            Equipo idEquipoVNew = partido.getIdEquipoV();
            Collection<Gol> golCollectionOld = persistentPartido.getGolCollection();
            Collection<Gol> golCollectionNew = partido.getGolCollection();
            List<String> illegalOrphanMessages = null;
            for (Gol golCollectionOldGol : golCollectionOld) {
                if (!golCollectionNew.contains(golCollectionOldGol)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Gol " + golCollectionOldGol + " since its partido field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (grupoNew != null) {
                grupoNew = em.getReference(grupoNew.getClass(), grupoNew.getGrupoPK());
                partido.setGrupo(grupoNew);
            }
            if (idEquipoLNew != null) {
                idEquipoLNew = em.getReference(idEquipoLNew.getClass(), idEquipoLNew.getIdEquipo());
                partido.setIdEquipoL(idEquipoLNew);
            }
            if (idEquipoVNew != null) {
                idEquipoVNew = em.getReference(idEquipoVNew.getClass(), idEquipoVNew.getIdEquipo());
                partido.setIdEquipoV(idEquipoVNew);
            }
            Collection<Gol> attachedGolCollectionNew = new ArrayList<Gol>();
            for (Gol golCollectionNewGolToAttach : golCollectionNew) {
                golCollectionNewGolToAttach = em.getReference(golCollectionNewGolToAttach.getClass(), golCollectionNewGolToAttach.getGolPK());
                attachedGolCollectionNew.add(golCollectionNewGolToAttach);
            }
            golCollectionNew = attachedGolCollectionNew;
            partido.setGolCollection(golCollectionNew);
            partido = em.merge(partido);
            if (grupoOld != null && !grupoOld.equals(grupoNew)) {
                grupoOld.getPartidoCollection().remove(partido);
                grupoOld = em.merge(grupoOld);
            }
            if (grupoNew != null && !grupoNew.equals(grupoOld)) {
                grupoNew.getPartidoCollection().add(partido);
                grupoNew = em.merge(grupoNew);
            }
            if (idEquipoLOld != null && !idEquipoLOld.equals(idEquipoLNew)) {
                idEquipoLOld.getPartidoCollection().remove(partido);
                idEquipoLOld = em.merge(idEquipoLOld);
            }
            if (idEquipoLNew != null && !idEquipoLNew.equals(idEquipoLOld)) {
                idEquipoLNew.getPartidoCollection().add(partido);
                idEquipoLNew = em.merge(idEquipoLNew);
            }
            if (idEquipoVOld != null && !idEquipoVOld.equals(idEquipoVNew)) {
                idEquipoVOld.getPartidoCollection().remove(partido);
                idEquipoVOld = em.merge(idEquipoVOld);
            }
            if (idEquipoVNew != null && !idEquipoVNew.equals(idEquipoVOld)) {
                idEquipoVNew.getPartidoCollection().add(partido);
                idEquipoVNew = em.merge(idEquipoVNew);
            }
            for (Gol golCollectionNewGol : golCollectionNew) {
                if (!golCollectionOld.contains(golCollectionNewGol)) {
                    Partido oldPartidoOfGolCollectionNewGol = golCollectionNewGol.getPartido();
                    golCollectionNewGol.setPartido(partido);
                    golCollectionNewGol = em.merge(golCollectionNewGol);
                    if (oldPartidoOfGolCollectionNewGol != null && !oldPartidoOfGolCollectionNewGol.equals(partido)) {
                        oldPartidoOfGolCollectionNewGol.getGolCollection().remove(golCollectionNewGol);
                        oldPartidoOfGolCollectionNewGol = em.merge(oldPartidoOfGolCollectionNewGol);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PartidoPK id = partido.getPartidoPK();
                if (findPartido(id) == null) {
                    throw new NonexistentEntityException("The partido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PartidoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partido partido;
            try {
                partido = em.getReference(Partido.class, id);
                partido.getPartidoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partido with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Gol> golCollectionOrphanCheck = partido.getGolCollection();
            for (Gol golCollectionOrphanCheckGol : golCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partido (" + partido + ") cannot be destroyed since the Gol " + golCollectionOrphanCheckGol + " in its golCollection field has a non-nullable partido field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Grupo grupo = partido.getGrupo();
            if (grupo != null) {
                grupo.getPartidoCollection().remove(partido);
                grupo = em.merge(grupo);
            }
            Equipo idEquipoL = partido.getIdEquipoL();
            if (idEquipoL != null) {
                idEquipoL.getPartidoCollection().remove(partido);
                idEquipoL = em.merge(idEquipoL);
            }
            Equipo idEquipoV = partido.getIdEquipoV();
            if (idEquipoV != null) {
                idEquipoV.getPartidoCollection().remove(partido);
                idEquipoV = em.merge(idEquipoV);
            }
            em.remove(partido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Partido> findPartidoEntities() {
        return findPartidoEntities(true, -1, -1);
    }

    public List<Partido> findPartidoEntities(int maxResults, int firstResult) {
        return findPartidoEntities(false, maxResults, firstResult);
    }

    private List<Partido> findPartidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Partido.class));
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

    public Partido findPartido(PartidoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Partido> rt = cq.from(Partido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

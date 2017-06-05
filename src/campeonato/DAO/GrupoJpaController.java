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
import campeonato.SRV.MODELOS.Etapa;
import campeonato.SRV.MODELOS.Grupo;
import campeonato.SRV.MODELOS.GrupoEquipo;
import campeonato.SRV.MODELOS.GrupoPK;
import java.util.ArrayList;
import java.util.Collection;
import campeonato.SRV.MODELOS.Partido;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lara
 */
public class GrupoJpaController implements Serializable {

    public GrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grupo grupo) throws PreexistingEntityException, Exception {
        if (grupo.getGrupoPK() == null) {
            grupo.setGrupoPK(new GrupoPK());
        }
        if (grupo.getGrupoEquipoCollection() == null) {
            grupo.setGrupoEquipoCollection(new ArrayList<GrupoEquipo>());
        }
        if (grupo.getPartidoCollection() == null) {
            grupo.setPartidoCollection(new ArrayList<Partido>());
        }
        grupo.getGrupoPK().setIdCampeonato(grupo.getEtapa().getEtapaPK().getIdCampeonato());
        grupo.getGrupoPK().setIdEtapa(grupo.getEtapa().getEtapaPK().getIdEtapa());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Etapa etapa = grupo.getEtapa();
            if (etapa != null) {
                etapa = em.getReference(etapa.getClass(), etapa.getEtapaPK());
                grupo.setEtapa(etapa);
            }
            Collection<GrupoEquipo> attachedGrupoEquipoCollection = new ArrayList<GrupoEquipo>();
            for (GrupoEquipo grupoEquipoCollectionGrupoEquipoToAttach : grupo.getGrupoEquipoCollection()) {
                grupoEquipoCollectionGrupoEquipoToAttach = em.getReference(grupoEquipoCollectionGrupoEquipoToAttach.getClass(), grupoEquipoCollectionGrupoEquipoToAttach.getGrupoEquipoPK());
                attachedGrupoEquipoCollection.add(grupoEquipoCollectionGrupoEquipoToAttach);
            }
            grupo.setGrupoEquipoCollection(attachedGrupoEquipoCollection);
            Collection<Partido> attachedPartidoCollection = new ArrayList<Partido>();
            for (Partido partidoCollectionPartidoToAttach : grupo.getPartidoCollection()) {
                partidoCollectionPartidoToAttach = em.getReference(partidoCollectionPartidoToAttach.getClass(), partidoCollectionPartidoToAttach.getPartidoPK());
                attachedPartidoCollection.add(partidoCollectionPartidoToAttach);
            }
            grupo.setPartidoCollection(attachedPartidoCollection);
            em.persist(grupo);
            if (etapa != null) {
                etapa.getGrupoCollection().add(grupo);
                etapa = em.merge(etapa);
            }
            for (GrupoEquipo grupoEquipoCollectionGrupoEquipo : grupo.getGrupoEquipoCollection()) {
                Grupo oldGrupoOfGrupoEquipoCollectionGrupoEquipo = grupoEquipoCollectionGrupoEquipo.getGrupo();
                grupoEquipoCollectionGrupoEquipo.setGrupo(grupo);
                grupoEquipoCollectionGrupoEquipo = em.merge(grupoEquipoCollectionGrupoEquipo);
                if (oldGrupoOfGrupoEquipoCollectionGrupoEquipo != null) {
                    oldGrupoOfGrupoEquipoCollectionGrupoEquipo.getGrupoEquipoCollection().remove(grupoEquipoCollectionGrupoEquipo);
                    oldGrupoOfGrupoEquipoCollectionGrupoEquipo = em.merge(oldGrupoOfGrupoEquipoCollectionGrupoEquipo);
                }
            }
            for (Partido partidoCollectionPartido : grupo.getPartidoCollection()) {
                Grupo oldGrupoOfPartidoCollectionPartido = partidoCollectionPartido.getGrupo();
                partidoCollectionPartido.setGrupo(grupo);
                partidoCollectionPartido = em.merge(partidoCollectionPartido);
                if (oldGrupoOfPartidoCollectionPartido != null) {
                    oldGrupoOfPartidoCollectionPartido.getPartidoCollection().remove(partidoCollectionPartido);
                    oldGrupoOfPartidoCollectionPartido = em.merge(oldGrupoOfPartidoCollectionPartido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGrupo(grupo.getGrupoPK()) != null) {
                throw new PreexistingEntityException("Grupo " + grupo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupo grupo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        grupo.getGrupoPK().setIdCampeonato(grupo.getEtapa().getEtapaPK().getIdCampeonato());
        grupo.getGrupoPK().setIdEtapa(grupo.getEtapa().getEtapaPK().getIdEtapa());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getGrupoPK());
            Etapa etapaOld = persistentGrupo.getEtapa();
            Etapa etapaNew = grupo.getEtapa();
            Collection<GrupoEquipo> grupoEquipoCollectionOld = persistentGrupo.getGrupoEquipoCollection();
            Collection<GrupoEquipo> grupoEquipoCollectionNew = grupo.getGrupoEquipoCollection();
            Collection<Partido> partidoCollectionOld = persistentGrupo.getPartidoCollection();
            Collection<Partido> partidoCollectionNew = grupo.getPartidoCollection();
            List<String> illegalOrphanMessages = null;
            for (GrupoEquipo grupoEquipoCollectionOldGrupoEquipo : grupoEquipoCollectionOld) {
                if (!grupoEquipoCollectionNew.contains(grupoEquipoCollectionOldGrupoEquipo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GrupoEquipo " + grupoEquipoCollectionOldGrupoEquipo + " since its grupo field is not nullable.");
                }
            }
            for (Partido partidoCollectionOldPartido : partidoCollectionOld) {
                if (!partidoCollectionNew.contains(partidoCollectionOldPartido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Partido " + partidoCollectionOldPartido + " since its grupo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (etapaNew != null) {
                etapaNew = em.getReference(etapaNew.getClass(), etapaNew.getEtapaPK());
                grupo.setEtapa(etapaNew);
            }
            Collection<GrupoEquipo> attachedGrupoEquipoCollectionNew = new ArrayList<GrupoEquipo>();
            for (GrupoEquipo grupoEquipoCollectionNewGrupoEquipoToAttach : grupoEquipoCollectionNew) {
                grupoEquipoCollectionNewGrupoEquipoToAttach = em.getReference(grupoEquipoCollectionNewGrupoEquipoToAttach.getClass(), grupoEquipoCollectionNewGrupoEquipoToAttach.getGrupoEquipoPK());
                attachedGrupoEquipoCollectionNew.add(grupoEquipoCollectionNewGrupoEquipoToAttach);
            }
            grupoEquipoCollectionNew = attachedGrupoEquipoCollectionNew;
            grupo.setGrupoEquipoCollection(grupoEquipoCollectionNew);
            Collection<Partido> attachedPartidoCollectionNew = new ArrayList<Partido>();
            for (Partido partidoCollectionNewPartidoToAttach : partidoCollectionNew) {
                partidoCollectionNewPartidoToAttach = em.getReference(partidoCollectionNewPartidoToAttach.getClass(), partidoCollectionNewPartidoToAttach.getPartidoPK());
                attachedPartidoCollectionNew.add(partidoCollectionNewPartidoToAttach);
            }
            partidoCollectionNew = attachedPartidoCollectionNew;
            grupo.setPartidoCollection(partidoCollectionNew);
            grupo = em.merge(grupo);
            if (etapaOld != null && !etapaOld.equals(etapaNew)) {
                etapaOld.getGrupoCollection().remove(grupo);
                etapaOld = em.merge(etapaOld);
            }
            if (etapaNew != null && !etapaNew.equals(etapaOld)) {
                etapaNew.getGrupoCollection().add(grupo);
                etapaNew = em.merge(etapaNew);
            }
            for (GrupoEquipo grupoEquipoCollectionNewGrupoEquipo : grupoEquipoCollectionNew) {
                if (!grupoEquipoCollectionOld.contains(grupoEquipoCollectionNewGrupoEquipo)) {
                    Grupo oldGrupoOfGrupoEquipoCollectionNewGrupoEquipo = grupoEquipoCollectionNewGrupoEquipo.getGrupo();
                    grupoEquipoCollectionNewGrupoEquipo.setGrupo(grupo);
                    grupoEquipoCollectionNewGrupoEquipo = em.merge(grupoEquipoCollectionNewGrupoEquipo);
                    if (oldGrupoOfGrupoEquipoCollectionNewGrupoEquipo != null && !oldGrupoOfGrupoEquipoCollectionNewGrupoEquipo.equals(grupo)) {
                        oldGrupoOfGrupoEquipoCollectionNewGrupoEquipo.getGrupoEquipoCollection().remove(grupoEquipoCollectionNewGrupoEquipo);
                        oldGrupoOfGrupoEquipoCollectionNewGrupoEquipo = em.merge(oldGrupoOfGrupoEquipoCollectionNewGrupoEquipo);
                    }
                }
            }
            for (Partido partidoCollectionNewPartido : partidoCollectionNew) {
                if (!partidoCollectionOld.contains(partidoCollectionNewPartido)) {
                    Grupo oldGrupoOfPartidoCollectionNewPartido = partidoCollectionNewPartido.getGrupo();
                    partidoCollectionNewPartido.setGrupo(grupo);
                    partidoCollectionNewPartido = em.merge(partidoCollectionNewPartido);
                    if (oldGrupoOfPartidoCollectionNewPartido != null && !oldGrupoOfPartidoCollectionNewPartido.equals(grupo)) {
                        oldGrupoOfPartidoCollectionNewPartido.getPartidoCollection().remove(partidoCollectionNewPartido);
                        oldGrupoOfPartidoCollectionNewPartido = em.merge(oldGrupoOfPartidoCollectionNewPartido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                GrupoPK id = grupo.getGrupoPK();
                if (findGrupo(id) == null) {
                    throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(GrupoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getGrupoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<GrupoEquipo> grupoEquipoCollectionOrphanCheck = grupo.getGrupoEquipoCollection();
            for (GrupoEquipo grupoEquipoCollectionOrphanCheckGrupoEquipo : grupoEquipoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the GrupoEquipo " + grupoEquipoCollectionOrphanCheckGrupoEquipo + " in its grupoEquipoCollection field has a non-nullable grupo field.");
            }
            Collection<Partido> partidoCollectionOrphanCheck = grupo.getPartidoCollection();
            for (Partido partidoCollectionOrphanCheckPartido : partidoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the Partido " + partidoCollectionOrphanCheckPartido + " in its partidoCollection field has a non-nullable grupo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Etapa etapa = grupo.getEtapa();
            if (etapa != null) {
                etapa.getGrupoCollection().remove(grupo);
                etapa = em.merge(etapa);
            }
            em.remove(grupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grupo> findGrupoEntities() {
        return findGrupoEntities(true, -1, -1);
    }

    public List<Grupo> findGrupoEntities(int maxResults, int firstResult) {
        return findGrupoEntities(false, maxResults, firstResult);
    }

    private List<Grupo> findGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupo.class));
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

    public Grupo findGrupo(GrupoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupo> rt = cq.from(Grupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

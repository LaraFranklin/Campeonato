/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.DAO;

import campeonato.DAO.exceptions.IllegalOrphanException;
import campeonato.DAO.exceptions.NonexistentEntityException;
import campeonato.SRV.MODELOS.Equipo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import campeonato.SRV.MODELOS.Jugador;
import java.util.ArrayList;
import java.util.Collection;
import campeonato.SRV.MODELOS.GrupoEquipo;
import campeonato.SRV.MODELOS.Partido;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lara
 */
public class EquipoJpaController implements Serializable {

    public EquipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipo equipo) {
        if (equipo.getJugadorCollection() == null) {
            equipo.setJugadorCollection(new ArrayList<Jugador>());
        }
        if (equipo.getGrupoEquipoCollection() == null) {
            equipo.setGrupoEquipoCollection(new ArrayList<GrupoEquipo>());
        }
        if (equipo.getPartidoCollection() == null) {
            equipo.setPartidoCollection(new ArrayList<Partido>());
        }
        if (equipo.getPartidoCollection1() == null) {
            equipo.setPartidoCollection1(new ArrayList<Partido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Jugador> attachedJugadorCollection = new ArrayList<Jugador>();
            for (Jugador jugadorCollectionJugadorToAttach : equipo.getJugadorCollection()) {
                jugadorCollectionJugadorToAttach = em.getReference(jugadorCollectionJugadorToAttach.getClass(), jugadorCollectionJugadorToAttach.getIdJugador());
                attachedJugadorCollection.add(jugadorCollectionJugadorToAttach);
            }
            equipo.setJugadorCollection(attachedJugadorCollection);
            Collection<GrupoEquipo> attachedGrupoEquipoCollection = new ArrayList<GrupoEquipo>();
            for (GrupoEquipo grupoEquipoCollectionGrupoEquipoToAttach : equipo.getGrupoEquipoCollection()) {
                grupoEquipoCollectionGrupoEquipoToAttach = em.getReference(grupoEquipoCollectionGrupoEquipoToAttach.getClass(), grupoEquipoCollectionGrupoEquipoToAttach.getGrupoEquipoPK());
                attachedGrupoEquipoCollection.add(grupoEquipoCollectionGrupoEquipoToAttach);
            }
            equipo.setGrupoEquipoCollection(attachedGrupoEquipoCollection);
            Collection<Partido> attachedPartidoCollection = new ArrayList<Partido>();
            for (Partido partidoCollectionPartidoToAttach : equipo.getPartidoCollection()) {
                partidoCollectionPartidoToAttach = em.getReference(partidoCollectionPartidoToAttach.getClass(), partidoCollectionPartidoToAttach.getPartidoPK());
                attachedPartidoCollection.add(partidoCollectionPartidoToAttach);
            }
            equipo.setPartidoCollection(attachedPartidoCollection);
            Collection<Partido> attachedPartidoCollection1 = new ArrayList<Partido>();
            for (Partido partidoCollection1PartidoToAttach : equipo.getPartidoCollection1()) {
                partidoCollection1PartidoToAttach = em.getReference(partidoCollection1PartidoToAttach.getClass(), partidoCollection1PartidoToAttach.getPartidoPK());
                attachedPartidoCollection1.add(partidoCollection1PartidoToAttach);
            }
            equipo.setPartidoCollection1(attachedPartidoCollection1);
            em.persist(equipo);
            for (Jugador jugadorCollectionJugador : equipo.getJugadorCollection()) {
                jugadorCollectionJugador.getEquipoCollection().add(equipo);
                jugadorCollectionJugador = em.merge(jugadorCollectionJugador);
            }
            for (GrupoEquipo grupoEquipoCollectionGrupoEquipo : equipo.getGrupoEquipoCollection()) {
                Equipo oldEquipoOfGrupoEquipoCollectionGrupoEquipo = grupoEquipoCollectionGrupoEquipo.getEquipo();
                grupoEquipoCollectionGrupoEquipo.setEquipo(equipo);
                grupoEquipoCollectionGrupoEquipo = em.merge(grupoEquipoCollectionGrupoEquipo);
                if (oldEquipoOfGrupoEquipoCollectionGrupoEquipo != null) {
                    oldEquipoOfGrupoEquipoCollectionGrupoEquipo.getGrupoEquipoCollection().remove(grupoEquipoCollectionGrupoEquipo);
                    oldEquipoOfGrupoEquipoCollectionGrupoEquipo = em.merge(oldEquipoOfGrupoEquipoCollectionGrupoEquipo);
                }
            }
            for (Partido partidoCollectionPartido : equipo.getPartidoCollection()) {
                Equipo oldIdEquipoLOfPartidoCollectionPartido = partidoCollectionPartido.getIdEquipoL();
                partidoCollectionPartido.setIdEquipoL(equipo);
                partidoCollectionPartido = em.merge(partidoCollectionPartido);
                if (oldIdEquipoLOfPartidoCollectionPartido != null) {
                    oldIdEquipoLOfPartidoCollectionPartido.getPartidoCollection().remove(partidoCollectionPartido);
                    oldIdEquipoLOfPartidoCollectionPartido = em.merge(oldIdEquipoLOfPartidoCollectionPartido);
                }
            }
            for (Partido partidoCollection1Partido : equipo.getPartidoCollection1()) {
                Equipo oldIdEquipoVOfPartidoCollection1Partido = partidoCollection1Partido.getIdEquipoV();
                partidoCollection1Partido.setIdEquipoV(equipo);
                partidoCollection1Partido = em.merge(partidoCollection1Partido);
                if (oldIdEquipoVOfPartidoCollection1Partido != null) {
                    oldIdEquipoVOfPartidoCollection1Partido.getPartidoCollection1().remove(partidoCollection1Partido);
                    oldIdEquipoVOfPartidoCollection1Partido = em.merge(oldIdEquipoVOfPartidoCollection1Partido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equipo equipo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipo persistentEquipo = em.find(Equipo.class, equipo.getIdEquipo());
            Collection<Jugador> jugadorCollectionOld = persistentEquipo.getJugadorCollection();
            Collection<Jugador> jugadorCollectionNew = equipo.getJugadorCollection();
            Collection<GrupoEquipo> grupoEquipoCollectionOld = persistentEquipo.getGrupoEquipoCollection();
            Collection<GrupoEquipo> grupoEquipoCollectionNew = equipo.getGrupoEquipoCollection();
            Collection<Partido> partidoCollectionOld = persistentEquipo.getPartidoCollection();
            Collection<Partido> partidoCollectionNew = equipo.getPartidoCollection();
            Collection<Partido> partidoCollection1Old = persistentEquipo.getPartidoCollection1();
            Collection<Partido> partidoCollection1New = equipo.getPartidoCollection1();
            List<String> illegalOrphanMessages = null;
            for (GrupoEquipo grupoEquipoCollectionOldGrupoEquipo : grupoEquipoCollectionOld) {
                if (!grupoEquipoCollectionNew.contains(grupoEquipoCollectionOldGrupoEquipo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GrupoEquipo " + grupoEquipoCollectionOldGrupoEquipo + " since its equipo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Jugador> attachedJugadorCollectionNew = new ArrayList<Jugador>();
            for (Jugador jugadorCollectionNewJugadorToAttach : jugadorCollectionNew) {
                jugadorCollectionNewJugadorToAttach = em.getReference(jugadorCollectionNewJugadorToAttach.getClass(), jugadorCollectionNewJugadorToAttach.getIdJugador());
                attachedJugadorCollectionNew.add(jugadorCollectionNewJugadorToAttach);
            }
            jugadorCollectionNew = attachedJugadorCollectionNew;
            equipo.setJugadorCollection(jugadorCollectionNew);
            Collection<GrupoEquipo> attachedGrupoEquipoCollectionNew = new ArrayList<GrupoEquipo>();
            for (GrupoEquipo grupoEquipoCollectionNewGrupoEquipoToAttach : grupoEquipoCollectionNew) {
                grupoEquipoCollectionNewGrupoEquipoToAttach = em.getReference(grupoEquipoCollectionNewGrupoEquipoToAttach.getClass(), grupoEquipoCollectionNewGrupoEquipoToAttach.getGrupoEquipoPK());
                attachedGrupoEquipoCollectionNew.add(grupoEquipoCollectionNewGrupoEquipoToAttach);
            }
            grupoEquipoCollectionNew = attachedGrupoEquipoCollectionNew;
            equipo.setGrupoEquipoCollection(grupoEquipoCollectionNew);
            Collection<Partido> attachedPartidoCollectionNew = new ArrayList<Partido>();
            for (Partido partidoCollectionNewPartidoToAttach : partidoCollectionNew) {
                partidoCollectionNewPartidoToAttach = em.getReference(partidoCollectionNewPartidoToAttach.getClass(), partidoCollectionNewPartidoToAttach.getPartidoPK());
                attachedPartidoCollectionNew.add(partidoCollectionNewPartidoToAttach);
            }
            partidoCollectionNew = attachedPartidoCollectionNew;
            equipo.setPartidoCollection(partidoCollectionNew);
            Collection<Partido> attachedPartidoCollection1New = new ArrayList<Partido>();
            for (Partido partidoCollection1NewPartidoToAttach : partidoCollection1New) {
                partidoCollection1NewPartidoToAttach = em.getReference(partidoCollection1NewPartidoToAttach.getClass(), partidoCollection1NewPartidoToAttach.getPartidoPK());
                attachedPartidoCollection1New.add(partidoCollection1NewPartidoToAttach);
            }
            partidoCollection1New = attachedPartidoCollection1New;
            equipo.setPartidoCollection1(partidoCollection1New);
            equipo = em.merge(equipo);
            for (Jugador jugadorCollectionOldJugador : jugadorCollectionOld) {
                if (!jugadorCollectionNew.contains(jugadorCollectionOldJugador)) {
                    jugadorCollectionOldJugador.getEquipoCollection().remove(equipo);
                    jugadorCollectionOldJugador = em.merge(jugadorCollectionOldJugador);
                }
            }
            for (Jugador jugadorCollectionNewJugador : jugadorCollectionNew) {
                if (!jugadorCollectionOld.contains(jugadorCollectionNewJugador)) {
                    jugadorCollectionNewJugador.getEquipoCollection().add(equipo);
                    jugadorCollectionNewJugador = em.merge(jugadorCollectionNewJugador);
                }
            }
            for (GrupoEquipo grupoEquipoCollectionNewGrupoEquipo : grupoEquipoCollectionNew) {
                if (!grupoEquipoCollectionOld.contains(grupoEquipoCollectionNewGrupoEquipo)) {
                    Equipo oldEquipoOfGrupoEquipoCollectionNewGrupoEquipo = grupoEquipoCollectionNewGrupoEquipo.getEquipo();
                    grupoEquipoCollectionNewGrupoEquipo.setEquipo(equipo);
                    grupoEquipoCollectionNewGrupoEquipo = em.merge(grupoEquipoCollectionNewGrupoEquipo);
                    if (oldEquipoOfGrupoEquipoCollectionNewGrupoEquipo != null && !oldEquipoOfGrupoEquipoCollectionNewGrupoEquipo.equals(equipo)) {
                        oldEquipoOfGrupoEquipoCollectionNewGrupoEquipo.getGrupoEquipoCollection().remove(grupoEquipoCollectionNewGrupoEquipo);
                        oldEquipoOfGrupoEquipoCollectionNewGrupoEquipo = em.merge(oldEquipoOfGrupoEquipoCollectionNewGrupoEquipo);
                    }
                }
            }
            for (Partido partidoCollectionOldPartido : partidoCollectionOld) {
                if (!partidoCollectionNew.contains(partidoCollectionOldPartido)) {
                    partidoCollectionOldPartido.setIdEquipoL(null);
                    partidoCollectionOldPartido = em.merge(partidoCollectionOldPartido);
                }
            }
            for (Partido partidoCollectionNewPartido : partidoCollectionNew) {
                if (!partidoCollectionOld.contains(partidoCollectionNewPartido)) {
                    Equipo oldIdEquipoLOfPartidoCollectionNewPartido = partidoCollectionNewPartido.getIdEquipoL();
                    partidoCollectionNewPartido.setIdEquipoL(equipo);
                    partidoCollectionNewPartido = em.merge(partidoCollectionNewPartido);
                    if (oldIdEquipoLOfPartidoCollectionNewPartido != null && !oldIdEquipoLOfPartidoCollectionNewPartido.equals(equipo)) {
                        oldIdEquipoLOfPartidoCollectionNewPartido.getPartidoCollection().remove(partidoCollectionNewPartido);
                        oldIdEquipoLOfPartidoCollectionNewPartido = em.merge(oldIdEquipoLOfPartidoCollectionNewPartido);
                    }
                }
            }
            for (Partido partidoCollection1OldPartido : partidoCollection1Old) {
                if (!partidoCollection1New.contains(partidoCollection1OldPartido)) {
                    partidoCollection1OldPartido.setIdEquipoV(null);
                    partidoCollection1OldPartido = em.merge(partidoCollection1OldPartido);
                }
            }
            for (Partido partidoCollection1NewPartido : partidoCollection1New) {
                if (!partidoCollection1Old.contains(partidoCollection1NewPartido)) {
                    Equipo oldIdEquipoVOfPartidoCollection1NewPartido = partidoCollection1NewPartido.getIdEquipoV();
                    partidoCollection1NewPartido.setIdEquipoV(equipo);
                    partidoCollection1NewPartido = em.merge(partidoCollection1NewPartido);
                    if (oldIdEquipoVOfPartidoCollection1NewPartido != null && !oldIdEquipoVOfPartidoCollection1NewPartido.equals(equipo)) {
                        oldIdEquipoVOfPartidoCollection1NewPartido.getPartidoCollection1().remove(partidoCollection1NewPartido);
                        oldIdEquipoVOfPartidoCollection1NewPartido = em.merge(oldIdEquipoVOfPartidoCollection1NewPartido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipo.getIdEquipo();
                if (findEquipo(id) == null) {
                    throw new NonexistentEntityException("The equipo with id " + id + " no longer exists.");
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
            Equipo equipo;
            try {
                equipo = em.getReference(Equipo.class, id);
                equipo.getIdEquipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<GrupoEquipo> grupoEquipoCollectionOrphanCheck = equipo.getGrupoEquipoCollection();
            for (GrupoEquipo grupoEquipoCollectionOrphanCheckGrupoEquipo : grupoEquipoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipo (" + equipo + ") cannot be destroyed since the GrupoEquipo " + grupoEquipoCollectionOrphanCheckGrupoEquipo + " in its grupoEquipoCollection field has a non-nullable equipo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Jugador> jugadorCollection = equipo.getJugadorCollection();
            for (Jugador jugadorCollectionJugador : jugadorCollection) {
                jugadorCollectionJugador.getEquipoCollection().remove(equipo);
                jugadorCollectionJugador = em.merge(jugadorCollectionJugador);
            }
            Collection<Partido> partidoCollection = equipo.getPartidoCollection();
            for (Partido partidoCollectionPartido : partidoCollection) {
                partidoCollectionPartido.setIdEquipoL(null);
                partidoCollectionPartido = em.merge(partidoCollectionPartido);
            }
            Collection<Partido> partidoCollection1 = equipo.getPartidoCollection1();
            for (Partido partidoCollection1Partido : partidoCollection1) {
                partidoCollection1Partido.setIdEquipoV(null);
                partidoCollection1Partido = em.merge(partidoCollection1Partido);
            }
            em.remove(equipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Equipo> findEquipoEntities() {
        return findEquipoEntities(true, -1, -1);
    }

    public List<Equipo> findEquipoEntities(int maxResults, int firstResult) {
        return findEquipoEntities(false, maxResults, firstResult);
    }

    private List<Equipo> findEquipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipo.class));
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

    public Equipo findEquipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipo> rt = cq.from(Equipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

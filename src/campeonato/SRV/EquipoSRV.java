/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV;

import campeonato.DAO.EquipoJpaController;
import campeonato.DAO.JugadorJpaController;
import campeonato.DAO.exceptions.IllegalOrphanException;
import campeonato.DAO.exceptions.NonexistentEntityException;
import campeonato.SRV.MODELOS.Equipo;
import campeonato.SRV.MODELOS.Jugador;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author lara
 */
public class EquipoSRV {
    private EntityManagerFactory emf ;
    private EntityManager em ;
    private EquipoJpaController service;

    public EquipoSRV() {
        emf = Persistence.createEntityManagerFactory("campeonatoPU");
        em = emf.createEntityManager();
        service = new EquipoJpaController(emf);
    }
    
    private void cerrar(){
        em.close();
        emf.close();
    }
    
    public void add(String nombre) throws Exception{
        if(nombre.trim().length() == 0 || nombre == null){
            //lanzar excepsion
            throw new IllegalArgumentException(nombre);
        }
        Equipo equipo = new Equipo(0, nombre);
        em.getTransaction().begin();
        service.create(equipo);
        em.getTransaction().commit();
        cerrar();
    }
    
    public void mod(Integer ID, String nombre) throws NonexistentEntityException, Exception{
        if(nombre.trim().length() == 0 || nombre == null || ID == 0 || ID == null){
            //lanzar excepsion
            throw new IllegalArgumentException(nombre);
        }
        Equipo equipo = new Equipo(ID, nombre);
        em.getTransaction().begin();
        service.edit(equipo);
        em.getTransaction().commit();
        cerrar();
    }
    
    public void del(Integer ID) throws IllegalOrphanException, NonexistentEntityException{
        if(ID == 0 || ID == null){
            throw new IllegalArgumentException();
        }
        em.getTransaction().begin();
        service.destroy(ID);
        em.getTransaction().commit();
        cerrar();
    }
    
    public Equipo get(Integer ID){
        if(ID == 0 || ID == null){
            throw new IllegalArgumentException();
        }
        
        em.getTransaction().begin();
        return service.findEquipo(ID);
    }
    
    public Equipo get(String nombre){
        if(nombre == null){
            throw new IllegalArgumentException();
        }
        em.getTransaction().begin();
        TypedQuery<Equipo> query = em.createNamedQuery("Equipo.findByNombre", Equipo.class);
        query.setParameter("nombre", nombre); 
        return (Equipo) query.getResultList();
    }
    
    public List<Equipo> getAll(){
        em.getTransaction().begin();
        return service.findEquipoEntities();
    }
    
    public List<Jugador> getJugadores(int id){
        Equipo equipo = get(id);
        return (List<Jugador>) equipo.getJugadorCollection();
    }
            
}

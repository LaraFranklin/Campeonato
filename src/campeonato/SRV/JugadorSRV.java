/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV;

import campeonato.DAO.JugadorJpaController;
import campeonato.DAO.exceptions.NonexistentEntityException;
import campeonato.SRV.MODELOS.Gol;
import campeonato.SRV.MODELOS.Jugador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import sun.print.resources.serviceui;

/**
 *
 * @author lara
 */
public class JugadorSRV {
    private EntityManagerFactory emf ;
    private EntityManager em ;
    private JugadorJpaController service;
    
    public JugadorSRV() {
        emf = Persistence.createEntityManagerFactory("campeonatoPU");
        em = emf.createEntityManager();
        service = new JugadorJpaController(emf);
    }
    
    
    public void add(String idJugador,String nombre,String apellidos,String telefono) throws Exception{
        if (idJugador.trim().length() == 0 || nombre.trim().length() == 0 || 
                apellidos.trim().length() == 0 || telefono.trim().length() == 0){
            throw new IllegalArgumentException();
        }
        JugadorJpaController service = new JugadorJpaController(emf);
        Jugador jugador = new Jugador(idJugador, nombre, apellidos, telefono);
        em.getTransaction().begin();
        service.create(jugador);
        em.getTransaction().commit();
        cerrar();
    }
    
    private void cerrar(){
        em.close();
        emf.close();
    }
    
    public void mod(String idJugador,String nombre,String apellidos,String telefono) throws Exception{
        if (idJugador.trim().length() == 0 || nombre.trim().length() == 0 || 
                apellidos.trim().length() == 0 || telefono.trim().length() == 0){
            //lanzar excepsion
            throw new IllegalArgumentException();
        }
        JugadorJpaController service = new JugadorJpaController(emf);
        Jugador jugador = new Jugador(idJugador, nombre, apellidos, telefono);
        em.getTransaction().begin();
        service.edit(jugador);
        em.getTransaction().commit();
        cerrar();
    }
    
    public Jugador get(String idJugador){
        Jugador Jugador = service.findJugador(idJugador);
        return Jugador;
    }
    
   
    
    public List<Jugador> getAll(){
        JugadorJpaController service = new JugadorJpaController(emf);
        em.getTransaction().begin();
        return service.findJugadorEntities();
    }
    
    public void del(String id) throws NonexistentEntityException{
        em.getTransaction().begin();
        service.destroy(id);
        em.getTransaction().commit();
        cerrar();
    }
    
    
}

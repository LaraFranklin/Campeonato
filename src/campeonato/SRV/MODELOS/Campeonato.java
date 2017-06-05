/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV.MODELOS;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lara
 */
@Entity
@Table(name = "campeonato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Campeonato.findAll", query = "SELECT c FROM Campeonato c")
    , @NamedQuery(name = "Campeonato.findByIdCampeonato", query = "SELECT c FROM Campeonato c WHERE c.idCampeonato = :idCampeonato")
    , @NamedQuery(name = "Campeonato.findByNombre", query = "SELECT c FROM Campeonato c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Campeonato.findByDescripcion", query = "SELECT c FROM Campeonato c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "Campeonato.findByCampeon", query = "SELECT c FROM Campeonato c WHERE c.campeon = :campeon")
    , @NamedQuery(name = "Campeonato.findByFechaInicio", query = "SELECT c FROM Campeonato c WHERE c.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Campeonato.findByFechaFinalizacion", query = "SELECT c FROM Campeonato c WHERE c.fechaFinalizacion = :fechaFinalizacion")})
public class Campeonato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCampeonato")
    private Integer idCampeonato;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "campeon")
    private String campeon;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "fechaFinalizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinalizacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campeonato")
    private Collection<Etapa> etapaCollection;

    public Campeonato() {
    }

    public Campeonato(Integer idCampeonato) {
        this.idCampeonato = idCampeonato;
    }

    public Campeonato(Integer idCampeonato, String nombre) {
        this.idCampeonato = idCampeonato;
        this.nombre = nombre;
    }

    public Integer getIdCampeonato() {
        return idCampeonato;
    }

    public void setIdCampeonato(Integer idCampeonato) {
        this.idCampeonato = idCampeonato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCampeon() {
        return campeon;
    }

    public void setCampeon(String campeon) {
        this.campeon = campeon;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    @XmlTransient
    public Collection<Etapa> getEtapaCollection() {
        return etapaCollection;
    }

    public void setEtapaCollection(Collection<Etapa> etapaCollection) {
        this.etapaCollection = etapaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCampeonato != null ? idCampeonato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Campeonato)) {
            return false;
        }
        Campeonato other = (Campeonato) object;
        if ((this.idCampeonato == null && other.idCampeonato != null) || (this.idCampeonato != null && !this.idCampeonato.equals(other.idCampeonato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "campeonato.SRV.MODELOS.Campeonato[ idCampeonato=" + idCampeonato + " ]";
    }
    
}

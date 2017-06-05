/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV.MODELOS;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lara
 */
@Entity
@Table(name = "etapa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etapa.findAll", query = "SELECT e FROM Etapa e")
    , @NamedQuery(name = "Etapa.findByIdEtapa", query = "SELECT e FROM Etapa e WHERE e.etapaPK.idEtapa = :idEtapa")
    , @NamedQuery(name = "Etapa.findByIdCampeonato", query = "SELECT e FROM Etapa e WHERE e.etapaPK.idCampeonato = :idCampeonato")
    , @NamedQuery(name = "Etapa.findByDescripcion", query = "SELECT e FROM Etapa e WHERE e.descripcion = :descripcion")})
public class Etapa implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EtapaPK etapaPK;
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "idCampeonato", referencedColumnName = "idCampeonato", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Campeonato campeonato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "etapa")
    private Collection<Grupo> grupoCollection;

    public Etapa() {
    }

    public Etapa(EtapaPK etapaPK) {
        this.etapaPK = etapaPK;
    }

    public Etapa(EtapaPK etapaPK, String descripcion, Campeonato campeonato) {
        this.etapaPK = etapaPK;
        this.descripcion = descripcion;
        this.campeonato = campeonato;
    }
    
    public Etapa(String idEtapa, int idCampeonato) {
        this.etapaPK = new EtapaPK(idEtapa, idCampeonato);
    }

    public EtapaPK getEtapaPK() {
        return etapaPK;
    }

    public void setEtapaPK(EtapaPK etapaPK) {
        this.etapaPK = etapaPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    @XmlTransient
    public Collection<Grupo> getGrupoCollection() {
        return grupoCollection;
    }

    public void setGrupoCollection(Collection<Grupo> grupoCollection) {
        this.grupoCollection = grupoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (etapaPK != null ? etapaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etapa)) {
            return false;
        }
        Etapa other = (Etapa) object;
        if ((this.etapaPK == null && other.etapaPK != null) || (this.etapaPK != null && !this.etapaPK.equals(other.etapaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "campeonato.SRV.MODELOS.Etapa[ etapaPK=" + etapaPK + " ]";
    }
    
}

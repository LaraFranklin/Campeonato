/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV.MODELOS;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g")
    , @NamedQuery(name = "Grupo.findByIdGrupo", query = "SELECT g FROM Grupo g WHERE g.grupoPK.idGrupo = :idGrupo")
    , @NamedQuery(name = "Grupo.findByIdEtapa", query = "SELECT g FROM Grupo g WHERE g.grupoPK.idEtapa = :idEtapa")
    , @NamedQuery(name = "Grupo.findByIdCampeonato", query = "SELECT g FROM Grupo g WHERE g.grupoPK.idCampeonato = :idCampeonato")})
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GrupoPK grupoPK;
    @JoinColumns({
        @JoinColumn(name = "idEtapa", referencedColumnName = "idEtapa", insertable = false, updatable = false)
        , @JoinColumn(name = "idCampeonato", referencedColumnName = "idCampeonato", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Etapa etapa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private Collection<GrupoEquipo> grupoEquipoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private Collection<Partido> partidoCollection;

    public Grupo() {
    }

    public Grupo(GrupoPK grupoPK) {
        this.grupoPK = grupoPK;
    }

    public Grupo(int idGrupo, String idEtapa, int idCampeonato) {
        this.grupoPK = new GrupoPK(idGrupo, idEtapa, idCampeonato);
    }

    public Grupo(GrupoPK grupoPK, Etapa etapa) {
        this.grupoPK = grupoPK;
        this.etapa = etapa;
    }

    
    public GrupoPK getGrupoPK() {
        return grupoPK;
    }

    public void setGrupoPK(GrupoPK grupoPK) {
        this.grupoPK = grupoPK;
    }

    public Etapa getEtapa() {
        return etapa;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

    @XmlTransient
    public Collection<GrupoEquipo> getGrupoEquipoCollection() {
        return grupoEquipoCollection;
    }

    public void setGrupoEquipoCollection(Collection<GrupoEquipo> grupoEquipoCollection) {
        this.grupoEquipoCollection = grupoEquipoCollection;
    }

    @XmlTransient
    public Collection<Partido> getPartidoCollection() {
        return partidoCollection;
    }

    public void setPartidoCollection(Collection<Partido> partidoCollection) {
        this.partidoCollection = partidoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoPK != null ? grupoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.grupoPK == null && other.grupoPK != null) || (this.grupoPK != null && !this.grupoPK.equals(other.grupoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "campeonato.SRV.MODELOS.Grupo[ grupoPK=" + grupoPK + " ]";
    }
    
}

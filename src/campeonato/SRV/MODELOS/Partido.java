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
@Table(name = "partido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partido.findAll", query = "SELECT p FROM Partido p")
    , @NamedQuery(name = "Partido.findByIdPartido", query = "SELECT p FROM Partido p WHERE p.partidoPK.idPartido = :idPartido")
    , @NamedQuery(name = "Partido.findByIdGrupo", query = "SELECT p FROM Partido p WHERE p.partidoPK.idGrupo = :idGrupo")
    , @NamedQuery(name = "Partido.findForTabla", 
            query = "SELECT p FROM Partido p WHERE p.partidoPK.idCampeonato = :idCampeonato and p.partidoPK.idGrupo = :idGrupo "
                    + "and p.partidoPK.idEtapa = :idEtapa")
    , @NamedQuery(name = "Partido.findByIdEtapa", query = "SELECT p FROM Partido p WHERE p.partidoPK.idCampeonato = :idCampeonato and p.partidoPK.idEtapa = :idEtapa")
    , @NamedQuery(name = "Partido.findByIdCampeonato", query = "SELECT p FROM Partido p WHERE p.partidoPK.idCampeonato = :idCampeonato")
    , @NamedQuery(name = "Partido.findByMarcadorL", query = "SELECT p FROM Partido p WHERE p.marcadorL = :marcadorL")
    , @NamedQuery(name = "Partido.findByMarcadorV", query = "SELECT p FROM Partido p WHERE p.marcadorV = :marcadorV")})
public class Partido implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PartidoPK partidoPK;
    @Column(name = "marcadorL")
    private Integer marcadorL;
    @Column(name = "marcadorV")
    private Integer marcadorV;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partido")
    private Collection<Gol> golCollection;
    @JoinColumns({
        @JoinColumn(name = "idGrupo", referencedColumnName = "idGrupo", insertable = false, updatable = false)
        , @JoinColumn(name = "idEtapa", referencedColumnName = "idEtapa", insertable = false, updatable = false)
        , @JoinColumn(name = "idCampeonato", referencedColumnName = "idCampeonato", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Grupo grupo;
    @JoinColumn(name = "idEquipoL", referencedColumnName = "idEquipo")
    @ManyToOne
    private Equipo idEquipoL;
    @JoinColumn(name = "idEquipoV", referencedColumnName = "idEquipo")
    @ManyToOne
    private Equipo idEquipoV;

    public Partido() {
    }

    public Partido(PartidoPK partidoPK, Integer marcadorL, Integer marcadorV, Grupo grupo, Equipo idEquipoL, Equipo idEquipoV) {
        this.partidoPK = partidoPK;
        this.marcadorL = marcadorL;
        this.marcadorV = marcadorV;
        this.grupo = grupo;
        this.idEquipoL = idEquipoL;
        this.idEquipoV = idEquipoV;
    }

    
    public Partido(PartidoPK partidoPK) {
        this.partidoPK = partidoPK;
    }

    public Partido(int idPartido, int idGrupo, String idEtapa, int idCampeonato) {
        this.partidoPK = new PartidoPK(idPartido, idGrupo, idEtapa, idCampeonato);
    }

    public PartidoPK getPartidoPK() {
        return partidoPK;
    }

    public void setPartidoPK(PartidoPK partidoPK) {
        this.partidoPK = partidoPK;
    }

    public Integer getMarcadorL() {
        return marcadorL;
    }

    public void setMarcadorL(Integer marcadorL) {
        this.marcadorL = marcadorL;
    }

    public Integer getMarcadorV() {
        return marcadorV;
    }

    public void setMarcadorV(Integer marcadorV) {
        this.marcadorV = marcadorV;
    }

    @XmlTransient
    public Collection<Gol> getGolCollection() {
        return golCollection;
    }

    public void setGolCollection(Collection<Gol> golCollection) {
        this.golCollection = golCollection;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Equipo getIdEquipoL() {
        return idEquipoL;
    }

    public void setIdEquipoL(Equipo idEquipoL) {
        this.idEquipoL = idEquipoL;
    }

    public Equipo getIdEquipoV() {
        return idEquipoV;
    }

    public void setIdEquipoV(Equipo idEquipoV) {
        this.idEquipoV = idEquipoV;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partidoPK != null ? partidoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partido)) {
            return false;
        }
        Partido other = (Partido) object;
        if ((this.partidoPK == null && other.partidoPK != null) || (this.partidoPK != null && !this.partidoPK.equals(other.partidoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "campeonato.SRV.MODELOS.Partido[ partidoPK=" + partidoPK + " ]";
    }
    
}

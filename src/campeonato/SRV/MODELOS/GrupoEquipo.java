/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV.MODELOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lara
 */
@Entity
@Table(name = "grupoEquipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoEquipo.findAll", query = "SELECT g FROM GrupoEquipo g")
    , @NamedQuery(name = "GrupoEquipo.findByIdEquipo", query = "SELECT g FROM GrupoEquipo g WHERE g.grupoEquipoPK.idEquipo = :idEquipo")
    , @NamedQuery(name = "GrupoEquipo.findByIdGrupo", 
            query = "SELECT g FROM GrupoEquipo g WHERE g.grupoEquipoPK.idGrupo = :idGrupo and g.grupoEquipoPK.idCampeonato = :idCampeonato "
                    + " and g.grupoEquipoPK.idEtapa = :idEtapa and g.posicion = :posicion")
    , @NamedQuery(name = "GrupoEquipo.findForTabla", 
            query = "SELECT g FROM GrupoEquipo g WHERE g.grupoEquipoPK.idGrupo = :idGrupo and g.grupoEquipoPK.idCampeonato = :idCampeonato "
                    + " and g.grupoEquipoPK.idEtapa = :idEtapa")
    , @NamedQuery(name = "GrupoEquipo.findByIdEtapa", query = "SELECT g FROM GrupoEquipo g WHERE g.grupoEquipoPK.idEtapa = :idEtapa")
    , @NamedQuery(name = "GrupoEquipo.findByIdCampeonato", query = "SELECT g FROM GrupoEquipo g WHERE g.grupoEquipoPK.idCampeonato = :idCampeonato")
    , @NamedQuery(name = "GrupoEquipo.findByPosicion", query = "SELECT g FROM GrupoEquipo g WHERE g.posicion = :posicion")
    , @NamedQuery(name = "GrupoEquipo.findByFecha", query = "SELECT g FROM GrupoEquipo g WHERE g.fecha = :fecha")})
public class GrupoEquipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GrupoEquipoPK grupoEquipoPK;
    @Basic(optional = false)
    @Column(name = "posicion")
    private int posicion;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumns({
        @JoinColumn(name = "idGrupo", referencedColumnName = "idGrupo", insertable = false, updatable = false)
        , @JoinColumn(name = "idEtapa", referencedColumnName = "idEtapa", insertable = false, updatable = false)
        , @JoinColumn(name = "IdCampeonato", referencedColumnName = "idCampeonato", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Grupo grupo;
    @JoinColumn(name = "idEquipo", referencedColumnName = "idEquipo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Equipo equipo;

    public GrupoEquipo() {
    }

    public GrupoEquipo(GrupoEquipoPK grupoEquipoPK) {
        this.grupoEquipoPK = grupoEquipoPK;
    }

    public GrupoEquipo(GrupoEquipoPK grupoEquipoPK, int posicion, Date fecha, Grupo grupo, Equipo equipo) {
        this.grupoEquipoPK = grupoEquipoPK;
        this.posicion = posicion;
        this.fecha = fecha;
        this.grupo = grupo;
        this.equipo = equipo;
    }
    
    

    public GrupoEquipo(GrupoEquipoPK grupoEquipoPK, int posicion) {
        this.grupoEquipoPK = grupoEquipoPK;
        this.posicion = posicion;
    }

    public GrupoEquipo(int idEquipo, int idGrupo, String idEtapa, int idCampeonato) {
        this.grupoEquipoPK = new GrupoEquipoPK(idEquipo, idGrupo, idEtapa, idCampeonato);
    }

    public GrupoEquipoPK getGrupoEquipoPK() {
        return grupoEquipoPK;
    }

    public void setGrupoEquipoPK(GrupoEquipoPK grupoEquipoPK) {
        this.grupoEquipoPK = grupoEquipoPK;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoEquipoPK != null ? grupoEquipoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoEquipo)) {
            return false;
        }
        GrupoEquipo other = (GrupoEquipo) object;
        if ((this.grupoEquipoPK == null && other.grupoEquipoPK != null) || (this.grupoEquipoPK != null && !this.grupoEquipoPK.equals(other.grupoEquipoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "campeonato.SRV.MODELOS.GrupoEquipo[ grupoEquipoPK=" + grupoEquipoPK + " ]";
    }
    
}

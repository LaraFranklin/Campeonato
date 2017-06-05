/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV.MODELOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author lara
 */
@Embeddable
public class GrupoEquipoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idEquipo")
    private int idEquipo;
    @Basic(optional = false)
    @Column(name = "idGrupo")
    private int idGrupo;
    @Basic(optional = false)
    @Column(name = "idEtapa")
    private String idEtapa;
    @Basic(optional = false)
    @Column(name = "IdCampeonato")
    private int idCampeonato;

    public GrupoEquipoPK() {
    }

    public GrupoEquipoPK(int idEquipo, int idGrupo, String idEtapa, int idCampeonato) {
        this.idEquipo = idEquipo;
        this.idGrupo = idGrupo;
        this.idEtapa = idEtapa;
        this.idCampeonato = idCampeonato;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getIdEtapa() {
        return idEtapa;
    }

    public void setIdEtapa(String idEtapa) {
        this.idEtapa = idEtapa;
    }

    public int getIdCampeonato() {
        return idCampeonato;
    }

    public void setIdCampeonato(int idCampeonato) {
        this.idCampeonato = idCampeonato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEquipo;
        hash += (int) idGrupo;
        hash += (idEtapa != null ? idEtapa.hashCode() : 0);
        hash += (int) idCampeonato;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoEquipoPK)) {
            return false;
        }
        GrupoEquipoPK other = (GrupoEquipoPK) object;
        if (this.idEquipo != other.idEquipo) {
            return false;
        }
        if (this.idGrupo != other.idGrupo) {
            return false;
        }
        if ((this.idEtapa == null && other.idEtapa != null) || (this.idEtapa != null && !this.idEtapa.equals(other.idEtapa))) {
            return false;
        }
        if (this.idCampeonato != other.idCampeonato) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "campeonato.SRV.MODELOS.GrupoEquipoPK[ idEquipo=" + idEquipo + ", idGrupo=" + idGrupo + ", idEtapa=" + idEtapa + ", idCampeonato=" + idCampeonato + " ]";
    }
    
}

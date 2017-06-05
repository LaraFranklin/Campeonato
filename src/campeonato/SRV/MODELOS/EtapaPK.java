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
public class EtapaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idEtapa")
    private String idEtapa;
    @Basic(optional = false)
    @Column(name = "idCampeonato")
    private int idCampeonato;

    public EtapaPK() {
    }

    public EtapaPK(String idEtapa, int idCampeonato) {
        this.idEtapa = idEtapa;
        this.idCampeonato = idCampeonato;
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
        hash += (idEtapa != null ? idEtapa.hashCode() : 0);
        hash += (int) idCampeonato;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EtapaPK)) {
            return false;
        }
        EtapaPK other = (EtapaPK) object;
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
        return "campeonato.SRV.MODELOS.EtapaPK[ idEtapa=" + idEtapa + ", idCampeonato=" + idCampeonato + " ]";
    }
    
}

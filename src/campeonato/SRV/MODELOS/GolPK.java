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
public class GolPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idGol")
    private int idGol;
    @Basic(optional = false)
    @Column(name = "idJugador")
    private String idJugador;
    @Basic(optional = false)
    @Column(name = "idPartido")
    private int idPartido;
    @Basic(optional = false)
    @Column(name = "idGrupo")
    private int idGrupo;
    @Basic(optional = false)
    @Column(name = "idEtapa")
    private String idEtapa;
    @Basic(optional = false)
    @Column(name = "idCampeonato")
    private int idCampeonato;

    public GolPK() {
    }

    public GolPK(int idGol, String idJugador, int idPartido, int idGrupo, String idEtapa, int idCampeonato) {
        this.idGol = idGol;
        this.idJugador = idJugador;
        this.idPartido = idPartido;
        this.idGrupo = idGrupo;
        this.idEtapa = idEtapa;
        this.idCampeonato = idCampeonato;
    }

    public int getIdGol() {
        return idGol;
    }

    public void setIdGol(int idGol) {
        this.idGol = idGol;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
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
        hash += (int) idGol;
        hash += (idJugador != null ? idJugador.hashCode() : 0);
        hash += (int) idPartido;
        hash += (int) idGrupo;
        hash += (idEtapa != null ? idEtapa.hashCode() : 0);
        hash += (int) idCampeonato;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GolPK)) {
            return false;
        }
        GolPK other = (GolPK) object;
        if (this.idGol != other.idGol) {
            return false;
        }
        if ((this.idJugador == null && other.idJugador != null) || (this.idJugador != null && !this.idJugador.equals(other.idJugador))) {
            return false;
        }
        if (this.idPartido != other.idPartido) {
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
        return "campeonato.SRV.MODELOS.GolPK[ idGol=" + idGol + ", idJugador=" + idJugador + ", idPartido=" + idPartido + ", idGrupo=" + idGrupo + ", idEtapa=" + idEtapa + ", idCampeonato=" + idCampeonato + " ]";
    }
    
}

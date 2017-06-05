/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV.MODELOS;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lara
 */
@Entity
@Table(name = "gol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gol.findAll", query = "SELECT g FROM Gol g")
    , @NamedQuery(name = "Gol.findByIdGol", query = "SELECT g FROM Gol g WHERE g.golPK.idGol = :idGol")
    , @NamedQuery(name = "Gol.findByIdJugador", query = "SELECT g FROM Gol g WHERE g.golPK.idJugador = :idJugador")
    , @NamedQuery(name = "Gol.findByIdPartido", query = "SELECT g FROM Gol g WHERE g.golPK.idPartido = :idPartido")
    , @NamedQuery(name = "Gol.findByIdGrupo", query = "SELECT g FROM Gol g WHERE g.golPK.idGrupo = :idGrupo")
    , @NamedQuery(name = "Gol.findByIdEtapa", query = "SELECT g FROM Gol g WHERE g.golPK.idEtapa = :idEtapa")
    , @NamedQuery(name = "Gol.findByIdCampeonato", query = "SELECT g FROM Gol g WHERE g.golPK.idCampeonato = :idCampeonato")})
public class Gol implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GolPK golPK;
    @JoinColumns({
        @JoinColumn(name = "idPartido", referencedColumnName = "idPartido", insertable = false, updatable = false)
        , @JoinColumn(name = "idGrupo", referencedColumnName = "idGrupo", insertable = false, updatable = false)
        , @JoinColumn(name = "idEtapa", referencedColumnName = "idEtapa", insertable = false, updatable = false)
        , @JoinColumn(name = "idCampeonato", referencedColumnName = "idCampeonato", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Partido partido;

    public Gol() {
    }

    public Gol(GolPK golPK) {
        this.golPK = golPK;
    }

    public Gol(int idGol, String idJugador, int idPartido, int idGrupo, String idEtapa, int idCampeonato) {
        this.golPK = new GolPK(idGol, idJugador, idPartido, idGrupo, idEtapa, idCampeonato);
    }

    public GolPK getGolPK() {
        return golPK;
    }

    public void setGolPK(GolPK golPK) {
        this.golPK = golPK;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (golPK != null ? golPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gol)) {
            return false;
        }
        Gol other = (Gol) object;
        if ((this.golPK == null && other.golPK != null) || (this.golPK != null && !this.golPK.equals(other.golPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "campeonato.SRV.MODELOS.Gol[ golPK=" + golPK + " ]";
    }
    
}

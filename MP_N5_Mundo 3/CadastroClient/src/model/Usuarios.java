package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Usuarios")
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByIdUsuarios", query = "SELECT u FROM Usuarios u WHERE u.idUsuarios = :idUsuarios"),
    @NamedQuery(name = "Usuarios.findByNomeUsuarios", query = "SELECT u FROM Usuarios u WHERE u.nomeUsuarios = :nomeUsuarios"),
    @NamedQuery(name = "Usuarios.findBySenhaUsuarios", query = "SELECT u FROM Usuarios u WHERE u.senhaUsuarios = :senhaUsuarios")
})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUsuarios")
    private int idUsuarios;

    @Column(name = "NomeUsuarios")
    private String nomeUsuarios;

    @Column(name = "SenhaUsuarios")
    private String senhaUsuarios;

    @OneToMany(mappedBy = "operadorID")
    private Collection<Movimentos> movimentosCollection;

    public Usuarios() {
    }

    public Usuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public int getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public String getNomeUsuarios() {
        return nomeUsuarios;
    }

    public void setNomeUsuarios(String nomeUsuarios) {
        this.nomeUsuarios = nomeUsuarios;
    }

    public String getSenhaUsuarios() {
        return senhaUsuarios;
    }

    public void setSenhaUsuarios(String senhaUsuarios) {
        this.senhaUsuarios = senhaUsuarios;
    }

    public Collection<Movimentos> getMovimentosCollection() {
        return movimentosCollection;
    }

    public void setMovimentosCollection(Collection<Movimentos> movimentosCollection) {
        this.movimentosCollection = movimentosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + idUsuarios;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        return this.idUsuarios == other.idUsuarios;
    }

    @Override
    public String toString() {
        return "model.Usuarios[ idUsuarios=" + idUsuarios + " ]";
    }
}

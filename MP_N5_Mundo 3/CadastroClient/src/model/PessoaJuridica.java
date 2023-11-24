/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author felip
 */
@Entity
@Table(name = "PessoaJuridica")
@NamedQueries({
    @NamedQuery(name = "PessoaJuridica.findAll", query = "SELECT p FROM PessoaJuridica p"),
    @NamedQuery(name = "PessoaJuridica.findByIdPessoasJuridicas", query = "SELECT p FROM PessoaJuridica p WHERE p.idPessoasJuridicas = :idPessoasJuridicas"),
    @NamedQuery(name = "PessoaJuridica.findByCnpj", query = "SELECT p FROM PessoaJuridica p WHERE p.cnpj = :cnpj")})
public class PessoaJuridica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idPessoasJuridicas")
    private Integer idPessoasJuridicas;
    @Column(name = "Cnpj")
    private String cnpj;
    @JoinColumn(name = "idPessoasJuridicas", referencedColumnName = "idPessoas", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Pessoas pessoas;

    public PessoaJuridica() {
    }

    public PessoaJuridica(Integer idPessoasJuridicas) {
        this.idPessoasJuridicas = idPessoasJuridicas;
    }

    public Integer getIdPessoasJuridicas() {
        return idPessoasJuridicas;
    }

    public void setIdPessoasJuridicas(Integer idPessoasJuridicas) {
        this.idPessoasJuridicas = idPessoasJuridicas;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Pessoas getPessoas() {
        return pessoas;
    }

    public void setPessoas(Pessoas pessoas) {
        this.pessoas = pessoas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPessoasJuridicas != null ? idPessoasJuridicas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PessoaJuridica)) {
            return false;
        }
        PessoaJuridica other = (PessoaJuridica) object;
        if ((this.idPessoasJuridicas == null && other.idPessoasJuridicas != null) || (this.idPessoasJuridicas != null && !this.idPessoasJuridicas.equals(other.idPessoasJuridicas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PessoaJuridica[ idPessoasJuridicas=" + idPessoasJuridicas + " ]";
    }
    
}

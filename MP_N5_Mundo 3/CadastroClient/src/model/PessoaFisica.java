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
@Table(name = "PessoaFisica")
@NamedQueries({
    @NamedQuery(name = "PessoaFisica.findAll", query = "SELECT p FROM PessoaFisica p"),
    @NamedQuery(name = "PessoaFisica.findByIdPessoasFisicas", query = "SELECT p FROM PessoaFisica p WHERE p.idPessoasFisicas = :idPessoasFisicas"),
    @NamedQuery(name = "PessoaFisica.findByCpf", query = "SELECT p FROM PessoaFisica p WHERE p.cpf = :cpf")})
public class PessoaFisica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idPessoasFisicas")
    private Integer idPessoasFisicas;
    @Column(name = "Cpf")
    private String cpf;
    @JoinColumn(name = "idPessoasFisicas", referencedColumnName = "idPessoas", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Pessoas pessoas;

    public PessoaFisica() {
    }

    public PessoaFisica(Integer idPessoasFisicas) {
        this.idPessoasFisicas = idPessoasFisicas;
    }

    public Integer getIdPessoasFisicas() {
        return idPessoasFisicas;
    }

    public void setIdPessoasFisicas(Integer idPessoasFisicas) {
        this.idPessoasFisicas = idPessoasFisicas;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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
        hash += (idPessoasFisicas != null ? idPessoasFisicas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PessoaFisica)) {
            return false;
        }
        PessoaFisica other = (PessoaFisica) object;
        if ((this.idPessoasFisicas == null && other.idPessoasFisicas != null) || (this.idPessoasFisicas != null && !this.idPessoasFisicas.equals(other.idPessoasFisicas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PessoaFisica[ idPessoasFisicas=" + idPessoasFisicas + " ]";
    }
    
}

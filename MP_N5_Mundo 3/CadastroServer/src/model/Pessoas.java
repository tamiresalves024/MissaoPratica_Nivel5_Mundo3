/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author felip
 */
@Entity
@Table(name = "Pessoas")
@NamedQueries({
    @NamedQuery(name = "Pessoas.findAll", query = "SELECT p FROM Pessoas p"),
    @NamedQuery(name = "Pessoas.findByIdPessoas", query = "SELECT p FROM Pessoas p WHERE p.idPessoas = :idPessoas"),
    @NamedQuery(name = "Pessoas.findByNomePessoas", query = "SELECT p FROM Pessoas p WHERE p.nomePessoas = :nomePessoas"),
    @NamedQuery(name = "Pessoas.findByEndere\u00e7oPessoas", query = "SELECT p FROM Pessoas p WHERE p.endere\u00e7oPessoas = :endere\u00e7oPessoas"),
    @NamedQuery(name = "Pessoas.findByContatoPessoas", query = "SELECT p FROM Pessoas p WHERE p.contatoPessoas = :contatoPessoas")})
public class Pessoas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPessoas")
    private Integer idPessoas;
    @Column(name = "NomePessoas")
    private String nomePessoas;
    @Column(name = "Endere\u00e7oPessoas")
    private String endereçoPessoas;
    @Column(name = "ContatoPessoas")
    private String contatoPessoas;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pessoas")
    private PessoaJuridica pessoaJuridica;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pessoas")
    private PessoaFisica pessoaFisica;

    public Pessoas() {
    }

    public Pessoas(Integer idPessoas) {
        this.idPessoas = idPessoas;
    }

    public Integer getIdPessoas() {
        return idPessoas;
    }

    public void setIdPessoas(Integer idPessoas) {
        this.idPessoas = idPessoas;
    }

    public String getNomePessoas() {
        return nomePessoas;
    }

    public void setNomePessoas(String nomePessoas) {
        this.nomePessoas = nomePessoas;
    }

    public String getEndereçoPessoas() {
        return endereçoPessoas;
    }

    public void setEndereçoPessoas(String endereçoPessoas) {
        this.endereçoPessoas = endereçoPessoas;
    }

    public String getContatoPessoas() {
        return contatoPessoas;
    }

    public void setContatoPessoas(String contatoPessoas) {
        this.contatoPessoas = contatoPessoas;
    }

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPessoas != null ? idPessoas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pessoas)) {
            return false;
        }
        Pessoas other = (Pessoas) object;
        if ((this.idPessoas == null && other.idPessoas != null) || (this.idPessoas != null && !this.idPessoas.equals(other.idPessoas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Pessoas[ idPessoas=" + idPessoas + " ]";
    }
    
}

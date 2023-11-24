/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 *
 * @author felip
 */
@Entity
@Table(name = "Produtos")
@NamedQueries({
    @NamedQuery(name = "Produtos.findAll", query = "SELECT p FROM Produtos p"),
    @NamedQuery(name = "Produtos.findByIdProdutos", query = "SELECT p FROM Produtos p WHERE p.idProdutos = :idProdutos"),
    @NamedQuery(name = "Produtos.findByNomeProdutos", query = "SELECT p FROM Produtos p WHERE p.nomeProdutos = :nomeProdutos"),
    @NamedQuery(name = "Produtos.findByQuantidadeProdutos", query = "SELECT p FROM Produtos p WHERE p.quantidadeProdutos = :quantidadeProdutos"),
    @NamedQuery(name = "Produtos.findByPre\u00e7oProdutos", query = "SELECT p FROM Produtos p WHERE p.pre\u00e7oProdutos = :pre\u00e7oProdutos")})
public class Produtos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProdutos")
    private int idProdutos;
    @Column(name = "NomeProdutos")
    private String nomeProdutos;
    @Column(name = "QuantidadeProdutos")
    private int quantidadeProdutos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Pre\u00e7oProdutos")
    private BigDecimal preçoProdutos;
    @OneToMany(mappedBy = "produtoID")
    private Collection<Movimentos> movimentosCollection;

    public Produtos() {
    }

    public Produtos(int idProdutos) {
        this.idProdutos = idProdutos;
    }

    public int getIdProdutos() {
        return idProdutos;
    }

    public void setIdProdutos(int idProdutos) {
        this.idProdutos = idProdutos;
    }

    public String getNomeProdutos() {
        return nomeProdutos;
    }

    public void setNomeProdutos(String nomeProdutos) {
        this.nomeProdutos = nomeProdutos;
    }

    public int getQuantidadeProdutos() {
        return quantidadeProdutos;
    }

    public void setQuantidadeProdutos(int quantidadeProdutos) {
        this.quantidadeProdutos = quantidadeProdutos;
    }

    public BigDecimal getPreçoProdutos() {
        return preçoProdutos;
    }

    public void setPreçoProdutos(BigDecimal preçoProdutos) {
        this.preçoProdutos = preçoProdutos;
    }

    public Collection<Movimentos> getMovimentosCollection() {
        return movimentosCollection;
    }

    public void setMovimentosCollection(Collection<Movimentos> movimentosCollection) {
        this.movimentosCollection = movimentosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += idProdutos;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Produtos)) {
            return false;
        }
        Produtos other = (Produtos) object;
        if (this.idProdutos != other.idProdutos) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "model.Produtos[ idProdutos=" + idProdutos + " ]";
    }

}

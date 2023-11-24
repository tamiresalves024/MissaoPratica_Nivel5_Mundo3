/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author felip
 */
@Entity
@Table(name = "Movimentos")
@NamedQueries({
    @NamedQuery(name = "Movimentos.findAll", query = "SELECT m FROM Movimentos m"),
    @NamedQuery(name = "Movimentos.findByIdMovimento", query = "SELECT m FROM Movimentos m WHERE m.idMovimento = :idMovimento"),
    @NamedQuery(name = "Movimentos.findByMovimentacao", query = "SELECT m FROM Movimentos m WHERE m.movimentacao = :movimentacao"),
    @NamedQuery(name = "Movimentos.findByQuantidadeMovimento", query = "SELECT m FROM Movimentos m WHERE m.quantidadeMovimento = :quantidadeMovimento"),
    @NamedQuery(name = "Movimentos.findByPre\u00e7oMovimento", query = "SELECT m FROM Movimentos m WHERE m.pre\u00e7oMovimento = :pre\u00e7oMovimento")})
public class Movimentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMovimento")
    private Integer idMovimento;
    @Column(name = "Movimentacao")
    private String movimentacao;
    @Column(name = "QuantidadeMovimento")
    private Integer quantidadeMovimento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Pre\u00e7oMovimento")
    private BigDecimal preçoMovimento;
    @JoinColumn(name = "ProdutoID", referencedColumnName = "idProdutos")
    @ManyToOne
    private Produtos produtoID;
    @JoinColumn(name = "OperadorID", referencedColumnName = "idUsuarios")
    @ManyToOne
    private Usuarios operadorID;

    public Movimentos() {
    }

    public Movimentos(Integer idMovimento) {
        this.idMovimento = idMovimento;
    }

    public Integer getIdMovimento() {
        return idMovimento;
    }

    public void setIdMovimento(Integer idMovimento) {
        this.idMovimento = idMovimento;
    }

    public String getMovimentacao() {
        return movimentacao;
    }

    public void setMovimentacao(String movimentacao) {
        this.movimentacao = movimentacao;
    }

    public Integer getQuantidadeMovimento() {
        return quantidadeMovimento;
    }

    public void setQuantidadeMovimento(Integer quantidadeMovimento) {
        this.quantidadeMovimento = quantidadeMovimento;
    }

    public BigDecimal getPreçoMovimento() {
        return preçoMovimento;
    }

    public void setPreçoMovimento(BigDecimal preçoMovimento) {
        this.preçoMovimento = preçoMovimento;
    }

    public Produtos getProdutoID() {
        return produtoID;
    }

    public void setProdutoID(Produtos produtoID) {
        this.produtoID = produtoID;
    }

    public Usuarios getOperadorID() {
        return operadorID;
    }
    

    public void setOperadorID(Usuarios operadorID) {
        this.operadorID = operadorID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMovimento != null ? idMovimento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimentos)) {
            return false;
        }
        Movimentos other = (Movimentos) object;
        if ((this.idMovimento == null && other.idMovimento != null) || (this.idMovimento != null && !this.idMovimento.equals(other.idMovimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Movimentos[ idMovimento=" + idMovimento + " ]";
    }
    
}

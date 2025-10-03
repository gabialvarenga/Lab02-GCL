package lab02.br.locadora.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Credito {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String numeroCredito;
    private BigDecimal valorCredito;
    private Integer numeroParcelas;
    private BigDecimal taxaJuros;
    private LocalDate dataAprovacao;
    
    @Enumerated(EnumType.STRING)
    private StatusCredito status;
    
    @OneToOne
    @JoinColumn(name = "contrato_id")
    private Contrato contrato;
    
    @ManyToOne
    @JoinColumn(name = "banco_id")
    private Banco banco;
    
    @Column(length = 1000)
    private String observacoes;
    
    // Construtores
    public Credito() {
        this.status = StatusCredito.ANALISE;
        this.dataAprovacao = null;
    }
    
    public Credito(BigDecimal valorCredito, Integer numeroParcelas, BigDecimal taxaJuros, Contrato contrato, Banco banco) {
        this.valorCredito = valorCredito;
        this.numeroParcelas = numeroParcelas;
        this.taxaJuros = taxaJuros;
        this.contrato = contrato;
        this.banco = banco;
        this.status = StatusCredito.ANALISE;
        this.dataAprovacao = null;
        
        // Geração de número de crédito
        this.numeroCredito = "CRD-" + System.currentTimeMillis();
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNumeroCredito() {
        return numeroCredito;
    }
    
    public void setNumeroCredito(String numeroCredito) {
        this.numeroCredito = numeroCredito;
    }
    
    public BigDecimal getValorCredito() {
        return valorCredito;
    }
    
    public void setValorCredito(BigDecimal valorCredito) {
        this.valorCredito = valorCredito;
    }
    
    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }
    
    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }
    
    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }
    
    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }
    
    public LocalDate getDataAprovacao() {
        return dataAprovacao;
    }
    
    public void setDataAprovacao(LocalDate dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }
    
    public StatusCredito getStatus() {
        return status;
    }
    
    public void setStatus(StatusCredito status) {
        this.status = status;
    }
    
    public Contrato getContrato() {
        return contrato;
    }
    
    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
    
    public Banco getBanco() {
        return banco;
    }
    
    public void setBanco(Banco banco) {
        this.banco = banco;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    // Métodos para aprovar e rejeitar crédito
    public void aprovar() {
        this.status = StatusCredito.APROVADO;
        this.dataAprovacao = LocalDate.now();
    }
    
    public void rejeitar() {
        this.status = StatusCredito.REJEITADO;
    }
    
    public void cancelar() {
        this.status = StatusCredito.CANCELADO;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credito credito = (Credito) o;
        return Objects.equals(id, credito.id) || 
               Objects.equals(numeroCredito, credito.numeroCredito);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, numeroCredito);
    }
    
    @Override
    public String toString() {
        return "Credito{" +
                "id=" + id +
                ", numeroCredito='" + numeroCredito + '\'' +
                ", valorCredito=" + valorCredito +
                ", numeroParcelas=" + numeroParcelas +
                ", taxaJuros=" + taxaJuros +
                ", status=" + status +
                ", contrato=" + (contrato != null ? contrato.getId() : null) +
                ", banco=" + (banco != null ? banco.getId() : null) +
                '}';
    }
}
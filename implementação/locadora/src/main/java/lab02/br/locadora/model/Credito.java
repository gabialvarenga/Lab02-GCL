package lab02.br.locadora.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Credito {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String numeroCredito;
    private BigDecimal valorFinanciado;
    private Integer prazoMeses;
    private Integer parcelas;
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
    
    public Credito(BigDecimal valorFinanciado, Integer prazoMeses, BigDecimal taxaJuros, Contrato contrato, Banco banco) {
        this.valorFinanciado = valorFinanciado;
        this.prazoMeses = prazoMeses;
        this.parcelas = prazoMeses; // parcelas = prazo em meses
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
    
    public BigDecimal getValorFinanciado() {
        return valorFinanciado;
    }
    
    public void setValorFinanciado(BigDecimal valorFinanciado) {
        this.valorFinanciado = valorFinanciado;
    }
    
    public Integer getPrazoMeses() {
        return prazoMeses;
    }
    
    public void setPrazoMeses(Integer prazoMeses) {
        this.prazoMeses = prazoMeses;
    }
    
    public Integer getParcelas() {
        return parcelas;
    }
    
    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
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
    
    // Método de negócio conforme diagrama
    public BigDecimal calcularParcela() {
        if (valorFinanciado == null || parcelas == null || parcelas == 0) {
            return BigDecimal.ZERO;
        }
        
        if (taxaJuros == null || taxaJuros.compareTo(BigDecimal.ZERO) == 0) {
            // Sem juros, apenas divide o valor pelas parcelas
            return valorFinanciado.divide(BigDecimal.valueOf(parcelas), 2, RoundingMode.HALF_UP);
        }
        
        // Fórmula de Price: PMT = PV * (i * (1+i)^n) / ((1+i)^n - 1)
        // onde PV = valor presente, i = taxa mensal, n = número de parcelas
        BigDecimal taxaMensal = taxaJuros.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP);
        BigDecimal umMaisTaxa = BigDecimal.ONE.add(taxaMensal);
        
        // Calcular (1+i)^n
        BigDecimal potencia = umMaisTaxa.pow(parcelas);
        
        // Numerador: i * (1+i)^n
        BigDecimal numerador = taxaMensal.multiply(potencia);
        
        // Denominador: (1+i)^n - 1
        BigDecimal denominador = potencia.subtract(BigDecimal.ONE);
        
        // Fração final
        BigDecimal fator = numerador.divide(denominador, 6, RoundingMode.HALF_UP);
        
        // Valor da parcela
        return valorFinanciado.multiply(fator).setScale(2, RoundingMode.HALF_UP);
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
                ", valorFinanciado=" + valorFinanciado +
                ", prazoMeses=" + prazoMeses +
                ", parcelas=" + parcelas +
                ", taxaJuros=" + taxaJuros +
                ", status=" + status +
                ", contrato=" + (contrato != null ? contrato.getId() : null) +
                ", banco=" + (banco != null ? banco.getId() : null) +
                '}';
    }
}
package lab02.br.locadora.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Contrato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String numero;
    
    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    
    private LocalDate dataAssinatura;
    private LocalDate dataRetirada;
    private LocalDate dataDevolucao;
    private BigDecimal valorTotal;
    private BigDecimal valorCaucao;
    private Boolean assinado;
    
    @Enumerated(EnumType.STRING)
    private TipoContrato tipoContrato;
    
    @OneToOne(mappedBy = "contrato", cascade = CascadeType.ALL)
    private Credito credito;
    
    // Construtores
    public Contrato() {
        this.dataAssinatura = LocalDate.now();
        this.assinado = false;
        this.tipoContrato = TipoContrato.ALUGUEL;
        this.valorCaucao = BigDecimal.ZERO;
    }
    
    public Contrato(Pedido pedido) {
        this.pedido = pedido;
        this.dataAssinatura = LocalDate.now();
        this.valorTotal = pedido.getValorTotal();
        this.assinado = false;
        this.tipoContrato = TipoContrato.ALUGUEL;
        this.valorCaucao = BigDecimal.ZERO;
        
        // Geração de número de contrato
        this.numero = "CTR-" + System.currentTimeMillis();
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public Pedido getPedido() {
        return pedido;
    }
    
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
    public LocalDate getDataAssinatura() {
        return dataAssinatura;
    }
    
    public void setDataAssinatura(LocalDate dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }
    
    public LocalDate getDataRetirada() {
        return dataRetirada;
    }
    
    public void setDataRetirada(LocalDate dataRetirada) {
        this.dataRetirada = dataRetirada;
    }
    
    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }
    
    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public BigDecimal getValorCaucao() {
        return valorCaucao;
    }
    
    public void setValorCaucao(BigDecimal valorCaucao) {
        this.valorCaucao = valorCaucao;
    }
    
    public Boolean getAssinado() {
        return assinado;
    }
    
    public void setAssinado(Boolean assinado) {
        this.assinado = assinado;
    }
    
    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }
    
    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }
    
    public Credito getCredito() {
        return credito;
    }
    
    public void setCredito(Credito credito) {
        this.credito = credito;
    }
    
    // Métodos de negócio conforme diagrama
    public boolean assinar() {
        if (!assinado) {
            this.assinado = true;
            return true;
        }
        return false;
    }
    
    public void registrarRetirada() {
        this.dataRetirada = LocalDate.now();
    }
    
    public void registrarDevolucao(int quilometragemFinal) {
        this.dataDevolucao = LocalDate.now();
        // Lógica adicional para registrar quilometragem pode ser adicionada aqui
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return Objects.equals(id, contrato.id) || 
               Objects.equals(numero, contrato.numero);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, numero);
    }
    
    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", dataAssinatura=" + dataAssinatura +
                ", dataRetirada=" + dataRetirada +
                ", dataDevolucao=" + dataDevolucao +
                ", valorTotal=" + valorTotal +
                ", valorCaucao=" + valorCaucao +
                ", assinado=" + assinado +
                ", tipoContrato=" + tipoContrato +
                '}';
    }
}
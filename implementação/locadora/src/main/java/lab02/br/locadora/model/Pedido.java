package lab02.br.locadora.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime dataPedido;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal valorTotal;
    
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "automovel_id")
    private Automovel automovel;
    
    @ManyToOne
    @JoinColumn(name = "agente_id")
    private Agente agenteResponsavel;
    
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Contrato contrato;
    
    @Column(length = 2000)
    private String observacoes;
    
    // Construtores
    public Pedido() {
        this.dataPedido = LocalDateTime.now();
        this.status = StatusPedido.PENDENTE;
        this.valorTotal = BigDecimal.ZERO;
    }
    
    public Pedido(Cliente cliente, Automovel automovel, LocalDate dataInicio, LocalDate dataFim) {
        this.cliente = cliente;
        this.automovel = automovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataPedido = LocalDateTime.now();
        this.status = StatusPedido.PENDENTE;
        this.valorTotal = BigDecimal.ZERO;
        atualizarValorTotal();
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getDataPedido() {
        return dataPedido;
    }
    
    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public LocalDate getDataInicio() {
        return dataInicio;
    }
    
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public LocalDate getDataFim() {
        return dataFim;
    }
    
    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }
    
    public StatusPedido getStatus() {
        return status;
    }
    
    public void setStatus(StatusPedido status) {
        this.status = status;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Automovel getAutomovel() {
        return automovel;
    }
    
    public void setAutomovel(Automovel automovel) {
        this.automovel = automovel;
    }
    
    public Agente getAgenteResponsavel() {
        return agenteResponsavel;
    }
    
    public void setAgenteResponsavel(Agente agenteResponsavel) {
        this.agenteResponsavel = agenteResponsavel;
    }
    
    public Contrato getContrato() {
        return contrato;
    }
    
    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    // Métodos de negócio conforme diagrama
    public void atualizarValorTotal() {
        if (automovel != null && automovel.getValorDiaria() != null && 
            dataInicio != null && dataFim != null) {
            long dias = ChronoUnit.DAYS.between(dataInicio, dataFim);
            if (dias < 0) dias = 0;
            this.valorTotal = automovel.getValorDiaria().multiply(BigDecimal.valueOf(dias));
        } else {
            this.valorTotal = BigDecimal.ZERO;
        }
    }
    
    public void enviarParaAnalise() {
        if (status == StatusPedido.PENDENTE) {
            status = StatusPedido.EM_ANALISE;
        }
    }
    
    public Contrato aprovar(Long agenteId) {
        if (status == StatusPedido.PENDENTE || status == StatusPedido.EM_ANALISE) {
            status = StatusPedido.APROVADO;
            Contrato novoContrato = new Contrato(this);
            this.contrato = novoContrato;
            return novoContrato;
        }
        return null;
    }
    
    public void rejeitar(Long agenteId, String motivo) {
        if (status == StatusPedido.PENDENTE || status == StatusPedido.EM_ANALISE) {
            status = StatusPedido.REJEITADO;
            this.observacoes = "Rejeitado pelo agente ID: " + agenteId + ". Motivo: " + motivo;
        }
    }
    
    // Métodos úteis para fluxo de aprovação (manter compatibilidade)
    public boolean aprovar() {
        if (status == StatusPedido.PENDENTE || status == StatusPedido.EM_ANALISE) {
            status = StatusPedido.APROVADO;
            return true;
        }
        return false;
    }
    
    public boolean rejeitar() {
        if (status == StatusPedido.PENDENTE || status == StatusPedido.EM_ANALISE) {
            status = StatusPedido.REJEITADO;
            return true;
        }
        return false;
    }
    
    public boolean iniciarAnalise() {
        if (status == StatusPedido.PENDENTE) {
            status = StatusPedido.EM_ANALISE;
            return true;
        }
        return false;
    }
    
    public boolean cancelar() {
        if (status != StatusPedido.CONTRATO_GERADO && status != StatusPedido.FINALIZADO) {
            status = StatusPedido.CANCELADO;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", dataPedido=" + dataPedido +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", status=" + status +
                ", cliente=" + (cliente != null ? cliente.getId() : null) +
                ", automovel=" + (automovel != null ? automovel.getPlaca() : null) +
                '}';
    }
}
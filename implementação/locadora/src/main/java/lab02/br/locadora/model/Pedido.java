package lab02.br.locadora.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate dataPedido;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    
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
        this.dataPedido = LocalDate.now();
        this.status = StatusPedido.PENDENTE;
    }
    
    public Pedido(Cliente cliente, Automovel automovel, LocalDate dataInicio, LocalDate dataFim) {
        this.cliente = cliente;
        this.automovel = automovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataPedido = LocalDate.now();
        this.status = StatusPedido.PENDENTE;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDate getDataPedido() {
        return dataPedido;
    }
    
    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
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
    
    // Métodos úteis para fluxo de aprovação
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
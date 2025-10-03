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
    
    private String numeroContrato;
    private LocalDate dataAssinatura;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal valorTotal;
    private Boolean ativo;
    
    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "automovel_id")
    private Automovel automovel;
    
    @OneToOne(mappedBy = "contrato", cascade = CascadeType.ALL)
    private Credito credito;
    
    @Column(length = 2000)
    private String termosContratuais;
    
    // Construtores
    public Contrato() {
        this.dataAssinatura = LocalDate.now();
        this.ativo = true;
    }
    
    public Contrato(Pedido pedido) {
        this.pedido = pedido;
        this.cliente = pedido.getCliente();
        this.automovel = pedido.getAutomovel();
        this.dataAssinatura = LocalDate.now();
        this.dataInicio = pedido.getDataInicio();
        this.dataFim = pedido.getDataFim();
        this.ativo = true;
        
        // Geração de número de contrato
        this.numeroContrato = "CTR-" + System.currentTimeMillis();
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNumeroContrato() {
        return numeroContrato;
    }
    
    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }
    
    public LocalDate getDataAssinatura() {
        return dataAssinatura;
    }
    
    public void setDataAssinatura(LocalDate dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
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
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public Boolean getAtivo() {
        return ativo;
    }
    
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    
    public Pedido getPedido() {
        return pedido;
    }
    
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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
    
    public Credito getCredito() {
        return credito;
    }
    
    public void setCredito(Credito credito) {
        this.credito = credito;
    }
    
    public String getTermosContratuais() {
        return termosContratuais;
    }
    
    public void setTermosContratuais(String termosContratuais) {
        this.termosContratuais = termosContratuais;
    }
    
    // Método para cancelar o contrato
    public void cancelar() {
        this.ativo = false;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return Objects.equals(id, contrato.id) || 
               Objects.equals(numeroContrato, contrato.numeroContrato);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, numeroContrato);
    }
    
    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", numeroContrato='" + numeroContrato + '\'' +
                ", dataAssinatura=" + dataAssinatura +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", valorTotal=" + valorTotal +
                ", ativo=" + ativo +
                ", cliente=" + (cliente != null ? cliente.getId() : null) +
                '}';
    }
}
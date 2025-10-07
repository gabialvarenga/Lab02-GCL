package lab02.br.locadora.dto;

import lab02.br.locadora.model.TipoContrato;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para retorno de contrato
 */
public class ContratoDTO {
    
    private Long id;
    private String numero;
    private Long pedidoId;
    private LocalDate dataAssinatura;
    private LocalDate dataRetirada;
    private LocalDate dataDevolucao;
    private BigDecimal valorTotal;
    private BigDecimal valorCaucao;
    private Boolean assinado;
    private TipoContrato tipoContrato;
    
    // Informações do pedido associado
    private Long clienteId;
    private String clienteNome;
    private Long automovelId;
    private String automovelModelo;
    private String automovelPlaca;
    
    // Informações do crédito (se houver)
    private Long creditoId;
    private BigDecimal valorFinanciado;
    private Integer parcelas;
    private BigDecimal valorParcela;
    
    // Construtores
    public ContratoDTO() {
        // Construtor vazio necessário para serialização/desserialização
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
    
    public Long getPedidoId() {
        return pedidoId;
    }
    
    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
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
    
    public Long getClienteId() {
        return clienteId;
    }
    
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    
    public String getClienteNome() {
        return clienteNome;
    }
    
    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }
    
    public Long getAutomovelId() {
        return automovelId;
    }
    
    public void setAutomovelId(Long automovelId) {
        this.automovelId = automovelId;
    }
    
    public String getAutomovelModelo() {
        return automovelModelo;
    }
    
    public void setAutomovelModelo(String automovelModelo) {
        this.automovelModelo = automovelModelo;
    }
    
    public String getAutomovelPlaca() {
        return automovelPlaca;
    }
    
    public void setAutomovelPlaca(String automovelPlaca) {
        this.automovelPlaca = automovelPlaca;
    }
    
    public Long getCreditoId() {
        return creditoId;
    }
    
    public void setCreditoId(Long creditoId) {
        this.creditoId = creditoId;
    }
    
    public BigDecimal getValorFinanciado() {
        return valorFinanciado;
    }
    
    public void setValorFinanciado(BigDecimal valorFinanciado) {
        this.valorFinanciado = valorFinanciado;
    }
    
    public Integer getParcelas() {
        return parcelas;
    }
    
    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }
    
    public BigDecimal getValorParcela() {
        return valorParcela;
    }
    
    public void setValorParcela(BigDecimal valorParcela) {
        this.valorParcela = valorParcela;
    }
}

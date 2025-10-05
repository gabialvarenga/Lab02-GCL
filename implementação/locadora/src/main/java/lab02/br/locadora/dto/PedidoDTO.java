package lab02.br.locadora.dto;

import lab02.br.locadora.model.StatusPedido;
import java.time.LocalDate;

public class PedidoDTO {
    private Long id;
    private LocalDate dataPedido;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private StatusPedido status;
    private Long clienteId;
    private String clienteNome;
    private Long automovelId;
    private String automovelModelo;
    private String automovelPlaca;
    private String observacoes;
    private Long agenteResponsavelId;
    private String agenteResponsavelNome;

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

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Long getAgenteResponsavelId() {
        return agenteResponsavelId;
    }

    public void setAgenteResponsavelId(Long agenteResponsavelId) {
        this.agenteResponsavelId = agenteResponsavelId;
    }

    public String getAgenteResponsavelNome() {
        return agenteResponsavelNome;
    }

    public void setAgenteResponsavelNome(String agenteResponsavelNome) {
        this.agenteResponsavelNome = agenteResponsavelNome;
    }
}

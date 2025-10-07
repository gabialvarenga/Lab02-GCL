package lab02.br.locadora.dto;

import lab02.br.locadora.model.TipoContrato;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para criação de contrato
 */
public class ContratoCreateDTO {
    
    private Long pedidoId;
    private BigDecimal valorCaucao;
    private TipoContrato tipoContrato;
    
    // Construtores
    public ContratoCreateDTO() {
    }
    
    public ContratoCreateDTO(Long pedidoId, BigDecimal valorCaucao, TipoContrato tipoContrato) {
        this.pedidoId = pedidoId;
        this.valorCaucao = valorCaucao;
        this.tipoContrato = tipoContrato;
    }
    
    // Getters e Setters
    public Long getPedidoId() {
        return pedidoId;
    }
    
    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }
    
    public BigDecimal getValorCaucao() {
        return valorCaucao;
    }
    
    public void setValorCaucao(BigDecimal valorCaucao) {
        this.valorCaucao = valorCaucao;
    }
    
    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }
    
    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }
}

package lab02.br.locadora.dto;

import lab02.br.locadora.model.TipoContrato;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para criação de contrato
 */
public class ContratoCreateDTO {
    
    private Long pedidoAluguelId;
    private BigDecimal valorCaucao;
    private TipoContrato tipoContrato;
    
    // Construtores
    public ContratoCreateDTO() {
    }
    
    public ContratoCreateDTO(Long pedidoAluguelId, BigDecimal valorCaucao, TipoContrato tipoContrato) {
        this.pedidoAluguelId = pedidoAluguelId;
        this.valorCaucao = valorCaucao;
        this.tipoContrato = tipoContrato;
    }
    
    // Getters e Setters
    public Long getPedidoAluguelId() {
        return pedidoAluguelId;
    }
    
    public void setPedidoAluguelId(Long pedidoAluguelId) {
        this.pedidoAluguelId = pedidoAluguelId;
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

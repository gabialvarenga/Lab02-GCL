package lab02.br.locadora.repository;

import lab02.br.locadora.model.Contrato;
import lab02.br.locadora.model.TipoContrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    
    /**
     * Busca contrato por número
     */
    Optional<Contrato> findByNumero(String numero);
    
    /**
     * Busca contrato por pedido de aluguel
     */
    Optional<Contrato> findByPedidoId(Long pedidoId);
    
    /**
     * Lista contratos por tipo
     */
    List<Contrato> findByTipoContrato(TipoContrato tipoContrato);
    
    /**
     * Lista contratos assinados
     */
    List<Contrato> findByAssinado(Boolean assinado);
    
    /**
     * Lista contratos por período de assinatura
     */
    @Query("SELECT c FROM Contrato c WHERE c.dataAssinatura BETWEEN :dataInicio AND :dataFim")
    List<Contrato> findByPeriodoAssinatura(
        @Param("dataInicio") LocalDate dataInicio, 
        @Param("dataFim") LocalDate dataFim
    );
    
    /**
     * Lista contratos ativos (assinados e não vencidos)
     */
    @Query("SELECT c FROM Contrato c WHERE c.assinado = true AND c.dataDevolucao IS NULL")
    List<Contrato> findContratosAtivos();
    
    /**
     * Lista contratos com devolução pendente
     */
    @Query("SELECT c FROM Contrato c WHERE c.dataRetirada IS NOT NULL AND c.dataDevolucao IS NULL")
    List<Contrato> findContratosPendenteDevolucao();
    
    /**
     * Lista contratos por cliente
     */
    @Query("SELECT c FROM Contrato c WHERE c.pedido.cliente.id = :clienteId")
    List<Contrato> findByClienteId(@Param("clienteId") Long clienteId);
    
    /**
     * Lista contratos com crédito associado
     */
    @Query("SELECT c FROM Contrato c WHERE c.credito IS NOT NULL")
    List<Contrato> findContratosComCredito();
}

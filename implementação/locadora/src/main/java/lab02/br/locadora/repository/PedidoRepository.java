package lab02.br.locadora.repository;

import lab02.br.locadora.model.Pedido;
import lab02.br.locadora.model.StatusPedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByClienteIdOrderByDataPedidoDesc(Long clienteId);
}
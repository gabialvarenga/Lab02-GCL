package lab02.br.locadora.service;

import lab02.br.locadora.dto.PedidoCreateDTO;
import lab02.br.locadora.dto.PedidoDTO;
import lab02.br.locadora.model.*;
import lab02.br.locadora.repository.AutomovelRepository;
import lab02.br.locadora.repository.PedidoRepository;
import lab02.br.locadora.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private AutomovelRepository automovelRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Cria um novo pedido de aluguel
     */
    @Transactional
    public PedidoDTO criarPedido(Long clienteId, PedidoCreateDTO pedidoCreateDTO) {
        // Buscar cliente
        Cliente cliente = (Cliente) usuarioRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Buscar automóvel
        Automovel automovel = automovelRepository.findById(pedidoCreateDTO.getAutomovelId())
                .orElseThrow(() -> new RuntimeException("Automóvel não encontrado"));

        // Validar se o automóvel está disponível
        if (!automovel.getDisponivel()) {
            throw new RuntimeException("Automóvel não está disponível para aluguel");
        }

        // Validar datas
        if (pedidoCreateDTO.getDataInicio().isBefore(LocalDate.now())) {
            throw new RuntimeException("Data de início não pode ser anterior à data atual");
        }

        if (pedidoCreateDTO.getDataFim().isBefore(pedidoCreateDTO.getDataInicio())) {
            throw new RuntimeException("Data de fim não pode ser anterior à data de início");
        }

        // Criar pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setAutomovel(automovel);
        pedido.setDataInicio(pedidoCreateDTO.getDataInicio());
        pedido.setDataFim(pedidoCreateDTO.getDataFim());
        pedido.setObservacoes(pedidoCreateDTO.getObservacoes());
        pedido.setDataPedido(LocalDate.now());
        pedido.setStatus(StatusPedido.PENDENTE);

        // Marcar automóvel como indisponível
        automovel.setDisponivel(false);
        automovelRepository.save(automovel);

        // Salvar pedido
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return toDTO(pedidoSalvo);
    }

    /**
     * Lista todos os pedidos
     */
    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista pedidos de um cliente específico
     */
    public List<PedidoDTO> listarPedidosDoCliente(Long clienteId) {
        return pedidoRepository.findByClienteIdOrderByDataPedidoDesc(clienteId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um pedido por ID
     */
    public Optional<PedidoDTO> buscarPorId(Long id) {
        return pedidoRepository.findById(id).map(this::toDTO);
    }

    /**
     * Consulta apenas o status de um pedido
     */
    public Optional<StatusPedido> consultarStatus(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).map(Pedido::getStatus);
    }

    /**
     * Modifica um pedido (apenas se ainda estiver PENDENTE)
     */
    @Transactional
    public PedidoDTO modificarPedido(Long pedidoId, Long clienteId, PedidoCreateDTO pedidoCreateDTO) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // Verificar se o pedido pertence ao cliente
        if (!pedido.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("Você não tem permissão para modificar este pedido");
        }

        // Só permite modificação se estiver PENDENTE
        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new RuntimeException("Apenas pedidos pendentes podem ser modificados");
        }

        // Atualizar datas
        pedido.setDataInicio(pedidoCreateDTO.getDataInicio());
        pedido.setDataFim(pedidoCreateDTO.getDataFim());
        pedido.setObservacoes(pedidoCreateDTO.getObservacoes());

        // Se trocar de automóvel
        if (!pedido.getAutomovel().getId().equals(pedidoCreateDTO.getAutomovelId())) {
            // Liberar automóvel anterior
            Automovel automovelAnterior = pedido.getAutomovel();
            automovelAnterior.setDisponivel(true);
            automovelRepository.save(automovelAnterior);

            // Reservar novo automóvel
            Automovel novoAutomovel = automovelRepository.findById(pedidoCreateDTO.getAutomovelId())
                    .orElseThrow(() -> new RuntimeException("Automóvel não encontrado"));

            if (!novoAutomovel.getDisponivel()) {
                throw new RuntimeException("Automóvel não está disponível");
            }

            novoAutomovel.setDisponivel(false);
            automovelRepository.save(novoAutomovel);
            pedido.setAutomovel(novoAutomovel);
        }

        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        return toDTO(pedidoAtualizado);
    }

    /**
     * Cancela um pedido
     */
    @Transactional
    public boolean cancelarPedido(Long pedidoId, Long clienteId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // Verificar se o pedido pertence ao cliente
        if (!pedido.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("Você não tem permissão para cancelar este pedido");
        }

        // Tentar cancelar
        boolean cancelado = pedido.cancelar();

        if (cancelado) {
            // Liberar o automóvel
            Automovel automovel = pedido.getAutomovel();
            automovel.setDisponivel(true);
            automovelRepository.save(automovel);

            pedidoRepository.save(pedido);
        }

        return cancelado;
    }

    /**
     * Converte entidade Pedido para DTO
     */
    private PedidoDTO toDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setDataInicio(pedido.getDataInicio());
        dto.setDataFim(pedido.getDataFim());
        dto.setStatus(pedido.getStatus());
        dto.setObservacoes(pedido.getObservacoes());

        if (pedido.getCliente() != null) {
            dto.setClienteId(pedido.getCliente().getId());
            dto.setClienteNome(pedido.getCliente().getNome());
        }

        if (pedido.getAutomovel() != null) {
            dto.setAutomovelId(pedido.getAutomovel().getId());
            dto.setAutomovelModelo(pedido.getAutomovel().getMarca() + " " + pedido.getAutomovel().getModelo());
            dto.setAutomovelPlaca(pedido.getAutomovel().getPlaca());
        }

        if (pedido.getAgenteResponsavel() != null) {
            dto.setAgenteResponsavelId(pedido.getAgenteResponsavel().getId());
            dto.setAgenteResponsavelNome(pedido.getAgenteResponsavel().getNome());
        }

        return dto;
    }
}

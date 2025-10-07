package lab02.br.locadora.service;

import lab02.br.locadora.dto.ContratoCreateDTO;
import lab02.br.locadora.dto.ContratoDTO;
import lab02.br.locadora.model.*;
import lab02.br.locadora.repository.AutomovelRepository;
import lab02.br.locadora.repository.ContratoRepository;
import lab02.br.locadora.repository.PedidoRepository;
import lab02.br.locadora.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private AutomovelRepository automovelRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Cria um novo contrato a partir de um pedido aprovado
     */
    @Transactional
    public ContratoDTO criarContrato(ContratoCreateDTO contratoCreateDTO) {
        // Buscar pedido
        Pedido pedido = pedidoRepository.findById(contratoCreateDTO.getpedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // Validar se o pedido foi aprovado
        if (pedido.getStatus() != StatusPedido.APROVADO) {
            throw new RuntimeException("Apenas pedidos APROVADOS podem gerar contrato");
        }

        // Verificar se já existe contrato para este pedido
        if (contratoRepository.findByPedidoAluguelId(pedido.getId()).isPresent()) {
            throw new RuntimeException("Já existe um contrato para este pedido");
        }

        // Criar contrato
        Contrato contrato = new Contrato(pedido);
        
        // Configurar valores
        if (contratoCreateDTO.getValorCaucao() != null) {
            contrato.setValorCaucao(contratoCreateDTO.getValorCaucao());
        }
        
        if (contratoCreateDTO.getTipoContrato() != null) {
            contrato.setTipoContrato(contratoCreateDTO.getTipoContrato());
        }
        
        // Registrar propriedade do automóvel conforme tipo de contrato
        registrarPropriedadeAutomovel(contrato, pedido);

        // Salvar contrato
        Contrato contratoSalvo = contratoRepository.save(contrato);
        
        // Atualizar pedido com o contrato
        pedido.setContrato(contratoSalvo);
        pedidoRepository.save(pedido);

        return toDTO(contratoSalvo);
    }

    /**
     * Registra a propriedade do automóvel conforme tipo de contrato
     */
    private void registrarPropriedadeAutomovel(Contrato contrato, Pedido pedido) {
        Automovel automovel = pedido.getAutomovel();
        
        if (contrato.getTipoContrato() == TipoContrato.CREDITO) {
            // Se for crédito, o automóvel pode ser registrado como propriedade
            // Por padrão, mantém o proprietário atual (empresa/banco que forneceu)
            // Após quitação total, pode ser transferido para o cliente
        } else {
            // Para aluguel simples, o automóvel continua com o proprietário original
            // (empresa ou banco que forneceu o veículo)
        }
        
        automovelRepository.save(automovel);
    }
    
    /**
     * Transfere propriedade do automóvel para o cliente
     * (usado quando crédito é quitado)
     */
    @Transactional
    public boolean transferirPropriedadeParaCliente(Long contratoId) {
        Contrato contrato = contratoRepository.findById(contratoId)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));
        
        // Verificar se é contrato de crédito
        if (contrato.getTipoContrato() != TipoContrato.CREDITO) {
            throw new RuntimeException("Apenas contratos de CRÉDITO permitem transferência de propriedade");
        }
        
        // Verificar se o crédito foi quitado
        if (contrato.getCredito() == null || contrato.getCredito().getStatus() != StatusCredito.APROVADO) {
            throw new RuntimeException("Crédito não está quitado");
        }
        
        // Transferir propriedade
        Automovel automovel = contrato.getpedido().getAutomovel();
        Cliente cliente = contrato.getpedido().getCliente();
        
        automovel.setProprietario(cliente);
        automovelRepository.save(automovel);
        
        return true;
    }

    /**
     * Lista todos os contratos
     */
    public List<ContratoDTO> listarTodos() {
        return contratoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca contrato por ID
     */
    public Optional<ContratoDTO> buscarPorId(Long id) {
        return contratoRepository.findById(id).map(this::toDTO);
    }

    /**
     * Busca contrato por número
     */
    public Optional<ContratoDTO> buscarPorNumero(String numero) {
        return contratoRepository.findByNumero(numero).map(this::toDTO);
    }

    /**
     * Lista contratos de um cliente
     */
    public List<ContratoDTO> listarPorCliente(Long clienteId) {
        return contratoRepository.findByClienteId(clienteId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista contratos ativos
     */
    public List<ContratoDTO> listarContratosAtivos() {
        return contratoRepository.findContratosAtivos().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista contratos com crédito associado
     */
    public List<ContratoDTO> listarContratosComCredito() {
        return contratoRepository.findContratosComCredito().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Assina um contrato
     */
    @Transactional
    public boolean assinarContrato(Long contratoId) {
        Contrato contrato = contratoRepository.findById(contratoId)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));

        boolean assinado = contrato.assinar();
        
        if (assinado) {
            contratoRepository.save(contrato);
        }
        
        return assinado;
    }

    /**
     * Registra retirada do veículo
     */
    @Transactional
    public boolean registrarRetirada(Long contratoId) {
        Contrato contrato = contratoRepository.findById(contratoId)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));

        if (!contrato.getAssinado()) {
            throw new RuntimeException("Contrato precisa estar assinado para registrar retirada");
        }

        contrato.registrarRetirada();
        contratoRepository.save(contrato);
        
        return true;
    }

    /**
     * Registra devolução do veículo
     */
    @Transactional
    public boolean registrarDevolucao(Long contratoId, int quilometragemFinal) {
        Contrato contrato = contratoRepository.findById(contratoId)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));

        if (contrato.getDataRetirada() == null) {
            throw new RuntimeException("Veículo não foi retirado ainda");
        }

        contrato.registrarDevolucao(quilometragemFinal);
        
        // Liberar automóvel para novas locações
        Automovel automovel = contrato.getpedido().getAutomovel();
        automovel.setDisponivel(true);
        automovelRepository.save(automovel);
        
        contratoRepository.save(contrato);
        
        return true;
    }

    /**
     * Converte Contrato para DTO
     */
    private ContratoDTO toDTO(Contrato contrato) {
        ContratoDTO dto = new ContratoDTO();
        
        dto.setId(contrato.getId());
        dto.setNumero(contrato.getNumero());
        dto.setDataAssinatura(contrato.getDataAssinatura());
        dto.setDataRetirada(contrato.getDataRetirada());
        dto.setDataDevolucao(contrato.getDataDevolucao());
        dto.setValorTotal(contrato.getValorTotal());
        dto.setValorCaucao(contrato.getValorCaucao());
        dto.setAssinado(contrato.getAssinado());
        dto.setTipoContrato(contrato.getTipoContrato());

        // Dados do pedido
        if (contrato.getpedido() != null) {
            dto.setPedidoAluguelId(contrato.getpedido().getId());
            
            if (contrato.getpedido().getCliente() != null) {
                dto.setClienteId(contrato.getpedido().getCliente().getId());
                dto.setClienteNome(contrato.getpedido().getCliente().getNome());
            }
            
            if (contrato.getpedido().getAutomovel() != null) {
                Automovel automovel = contrato.getpedido().getAutomovel();
                dto.setAutomovelId(automovel.getId());
                dto.setAutomovelModelo(automovel.getMarca() + " " + automovel.getModelo());
                dto.setAutomovelPlaca(automovel.getPlaca());
            }
        }

        // Dados do crédito (se houver)
        if (contrato.getCredito() != null) {
            Credito credito = contrato.getCredito();
            dto.setCreditoId(credito.getId());
            dto.setValorFinanciado(credito.getValorFinanciado());
            dto.setParcelas(credito.getParcelas());
            dto.setValorParcela(credito.calcularParcela());
        }

        return dto;
    }
}

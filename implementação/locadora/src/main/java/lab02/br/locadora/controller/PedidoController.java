package lab02.br.locadora.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lab02.br.locadora.dto.PedidoCreateDTO;
import lab02.br.locadora.dto.PedidoDTO;
import lab02.br.locadora.model.StatusPedido;
import lab02.br.locadora.model.Usuario;
import lab02.br.locadora.repository.UsuarioRepository;
import lab02.br.locadora.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "API para gerenciamento de pedidos de aluguel")
@SecurityRequirement(name = "bearer-jwt")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Operation(summary = "Criar novo pedido de aluguel", 
               description = "Permite que um cliente crie um novo pedido de aluguel de automóvel")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Automóvel não encontrado", content = @Content)
    })
    public ResponseEntity<PedidoDTO> criarPedido(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do pedido")
            @RequestBody PedidoCreateDTO pedidoCreateDTO) {
        try {
            Long clienteId = getClienteIdFromAuthentication();
            PedidoDTO pedido = pedidoService.criarPedido(clienteId, pedidoCreateDTO);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/meus-pedidos")
    @Operation(summary = "Listar meus pedidos", 
               description = "Retorna todos os pedidos do cliente autenticado")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<List<PedidoDTO>> listarMeusPedidos() {
        Long clienteId = getClienteIdFromAuthentication();
        List<PedidoDTO> pedidos = pedidoService.listarPedidosDoCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping
    @Operation(summary = "Listar todos os pedidos", 
               description = "Retorna todos os pedidos (apenas para administradores/agentes)")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<List<PedidoDTO>> listarTodosPedidos() {
        List<PedidoDTO> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", 
               description = "Retorna os detalhes completos de um pedido específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content)
    })
    public ResponseEntity<PedidoDTO> buscarPorId(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/status")
    @Operation(summary = "Consultar status do pedido", 
               description = "Retorna apenas o status atual de um pedido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content)
    })
    public ResponseEntity<Map<String, StatusPedido>> consultarStatus(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        return pedidoService.consultarStatus(id)
                .map(status -> ResponseEntity.ok(Map.of("status", status)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar pedido", 
               description = "Permite modificar um pedido que ainda está pendente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido modificado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Pedido não pode ser modificado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content)
    })
    public ResponseEntity<PedidoDTO> modificarPedido(
            @Parameter(description = "ID do pedido") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados do pedido")
            @RequestBody PedidoCreateDTO pedidoCreateDTO) {
        try {
            Long clienteId = getClienteIdFromAuthentication();
            PedidoDTO pedido = pedidoService.modificarPedido(id, clienteId, pedidoCreateDTO);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar pedido", 
               description = "Permite cancelar um pedido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido cancelado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Pedido não pode ser cancelado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content)
    })
    public ResponseEntity<Map<String, String>> cancelarPedido(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        try {
            Long clienteId = getClienteIdFromAuthentication();
            boolean cancelado = pedidoService.cancelarPedido(id, clienteId);
            
            if (cancelado) {
                return ResponseEntity.ok(Map.of("message", "Pedido cancelado com sucesso"));
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Pedido não pode ser cancelado"));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * Obtém o ID do cliente a partir do contexto de segurança
     */
    private Long getClienteIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        return usuario.getId();
    }
}

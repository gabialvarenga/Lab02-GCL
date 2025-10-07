package lab02.br.locadora.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lab02.br.locadora.dto.ContratoCreateDTO;
import lab02.br.locadora.dto.ContratoDTO;
import lab02.br.locadora.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contratos")
@Tag(name = "Contratos", description = "Gerenciamento de contratos de aluguel e crédito")
@SecurityRequirement(name = "bearer-token")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @PostMapping
    @Operation(summary = "Criar contrato", 
               description = "Cria um novo contrato a partir de um pedido aprovado")
    @PreAuthorize("hasAuthority('ROLE_ATENDENTE')")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Contrato criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou pedido não aprovado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content)
    })
    public ResponseEntity<?> criarContrato(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do contrato") 
            @RequestBody ContratoCreateDTO contratoCreateDTO) {
        try {
            ContratoDTO contratoDTO = contratoService.criarContrato(contratoCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(contratoDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Listar contratos", 
               description = "Lista todos os contratos do sistema")
    @PreAuthorize("hasAnyAuthority('ROLE_ATENDENTE', 'ROLE_BANCO', 'ROLE_EMPRESA')")
    @ApiResponse(responseCode = "200", description = "Lista de contratos retornada com sucesso")
    public ResponseEntity<List<ContratoDTO>> listarTodos() {
        List<ContratoDTO> contratos = contratoService.listarTodos();
        return ResponseEntity.ok(contratos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar contrato", 
               description = "Busca um contrato por ID")
    @PreAuthorize("hasAnyAuthority('ROLE_ATENDENTE', 'ROLE_CLIENTE', 'ROLE_BANCO', 'ROLE_EMPRESA')")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contrato encontrado"),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado", content = @Content)
    })
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID do contrato") @PathVariable Long id) {
        return contratoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @GetMapping("/numero/{numero}")
    @Operation(summary = "Buscar por número", 
               description = "Busca um contrato pelo número")
    @PreAuthorize("hasAnyAuthority('ROLE_ATENDENTE', 'ROLE_CLIENTE', 'ROLE_BANCO', 'ROLE_EMPRESA')")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contrato encontrado"),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado", content = @Content)
    })
    public ResponseEntity<?> buscarPorNumero(
            @Parameter(description = "Número do contrato") @PathVariable String numero) {
        return contratoService.buscarPorNumero(numero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar contratos do cliente", 
               description = "Lista todos os contratos de um cliente específico")
    @PreAuthorize("hasAnyAuthority('ROLE_ATENDENTE', 'ROLE_CLIENTE')")
    @ApiResponse(responseCode = "200", description = "Lista de contratos do cliente")
    public ResponseEntity<List<ContratoDTO>> listarPorCliente(
            @Parameter(description = "ID do cliente") @PathVariable Long clienteId) {
        List<ContratoDTO> contratos = contratoService.listarPorCliente(clienteId);
        return ResponseEntity.ok(contratos);
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar contratos ativos", 
               description = "Lista todos os contratos ativos (assinados e sem devolução)")
    @PreAuthorize("hasAnyAuthority('ROLE_ATENDENTE', 'ROLE_BANCO', 'ROLE_EMPRESA')")
    @ApiResponse(responseCode = "200", description = "Lista de contratos ativos")
    public ResponseEntity<List<ContratoDTO>> listarContratosAtivos() {
        List<ContratoDTO> contratos = contratoService.listarContratosAtivos();
        return ResponseEntity.ok(contratos);
    }

    @GetMapping("/com-credito")
    @Operation(summary = "Listar contratos com crédito", 
               description = "Lista todos os contratos que possuem crédito associado")
    @PreAuthorize("hasAnyAuthority('ROLE_ATENDENTE', 'ROLE_BANCO')")
    @ApiResponse(responseCode = "200", description = "Lista de contratos com crédito")
    public ResponseEntity<List<ContratoDTO>> listarContratosComCredito() {
        List<ContratoDTO> contratos = contratoService.listarContratosComCredito();
        return ResponseEntity.ok(contratos);
    }

    @PutMapping("/{id}/assinar")
    @Operation(summary = "Assinar contrato", 
               description = "Registra a assinatura de um contrato")
    @PreAuthorize("hasAnyAuthority('ROLE_ATENDENTE', 'ROLE_CLIENTE')")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contrato assinado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Contrato não pode ser assinado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado", content = @Content)
    })
    public ResponseEntity<Map<String, String>> assinarContrato(
            @Parameter(description = "ID do contrato") @PathVariable Long id) {
        try {
            boolean assinado = contratoService.assinarContrato(id);
            
            if (assinado) {
                return ResponseEntity.ok(Map.of("message", "Contrato assinado com sucesso"));
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Contrato já está assinado"));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}/retirada")
    @Operation(summary = "Registrar retirada", 
               description = "Registra a retirada do veículo pelo cliente")
    @PreAuthorize("hasAuthority('ROLE_ATENDENTE')")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retirada registrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro ao registrar retirada", content = @Content),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado", content = @Content)
    })
    public ResponseEntity<Map<String, String>> registrarRetirada(
            @Parameter(description = "ID do contrato") @PathVariable Long id) {
        try {
            contratoService.registrarRetirada(id);
            return ResponseEntity.ok(Map.of("message", "Retirada registrada com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}/devolucao")
    @Operation(summary = "Registrar devolução", 
               description = "Registra a devolução do veículo pelo cliente")
    @PreAuthorize("hasAuthority('ROLE_ATENDENTE')")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Devolução registrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro ao registrar devolução", content = @Content),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado", content = @Content)
    })
    public ResponseEntity<Map<String, String>> registrarDevolucao(
            @Parameter(description = "ID do contrato") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Quilometragem final")
            @RequestBody Map<String, Integer> body) {
        try {
            int quilometragemFinal = body.getOrDefault("quilometragemFinal", 0);
            contratoService.registrarDevolucao(id, quilometragemFinal);
            return ResponseEntity.ok(Map.of("message", "Devolução registrada com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}/transferir-propriedade")
    @Operation(summary = "Transferir propriedade", 
               description = "Transfere a propriedade do automóvel para o cliente (após quitação do crédito)")
    @PreAuthorize("hasAnyAuthority('ROLE_BANCO', 'ROLE_EMPRESA')")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Propriedade transferida com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro ao transferir propriedade", content = @Content),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado", content = @Content)
    })
    public ResponseEntity<Map<String, String>> transferirPropriedade(
            @Parameter(description = "ID do contrato") @PathVariable Long id) {
        try {
            contratoService.transferirPropriedadeParaCliente(id);
            return ResponseEntity.ok(Map.of("message", "Propriedade do automóvel transferida para o cliente com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}

package lab02.br.locadora.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lab02.br.locadora.dto.ClienteCreateDTO;
import lab02.br.locadora.dto.UsuarioDTO;
import lab02.br.locadora.model.Cliente;
import lab02.br.locadora.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "API para gerenciamento de clientes")
public class ClienteController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista apenas com clientes cadastrados (apenas para atendentes)")
    @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    @PreAuthorize("hasAuthority('ROLE_ATENDENTE')")
    public List<UsuarioDTO> listarTodos() {
        return usuarioService.listarClientes();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente único com base no ID fornecido (apenas para atendentes)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    @PreAuthorize("hasAuthority('ROLE_ATENDENTE')")
    public ResponseEntity<UsuarioDTO> buscarPorId(@Parameter(description = "ID do cliente") @PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar um novo cliente", description = "Cria um novo cliente com os dados fornecidos (apenas para atendentes)")
    @ApiResponse(responseCode = "200", description = "Cliente criado com sucesso")
    @PreAuthorize("hasAuthority('ROLE_ATENDENTE')")
    public UsuarioDTO criar(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do cliente a ser criado", 
            required = true, content = @Content(schema = @Schema(implementation = ClienteCreateDTO.class)))
            @RequestBody ClienteCreateDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setPasswordHash(clienteDTO.getPassword());
        cliente.setRole(clienteDTO.getRole());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setRg(clienteDTO.getRg());
        cliente.setEndereco(clienteDTO.getEndereco());
        cliente.setProfissao(clienteDTO.getProfissao());
        
        return usuarioService.salvar(cliente);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um cliente", description = "Atualiza os dados de um cliente existente (apenas para atendentes)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    @PreAuthorize("hasAuthority('ROLE_ATENDENTE')")
    public ResponseEntity<UsuarioDTO> atualizar(
            @Parameter(description = "ID do cliente") @PathVariable Long id, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados do cliente") 
            @RequestBody ClienteCreateDTO clienteDTO) {
        return usuarioService.buscarPorId(id)
                .map(usuarioDTO -> {
                    Cliente cliente = new Cliente();
                    cliente.setId(id);
                    cliente.setNome(clienteDTO.getNome());
                    cliente.setEmail(clienteDTO.getEmail());
                    cliente.setPasswordHash(clienteDTO.getPassword());
                    cliente.setRole(clienteDTO.getRole());
                    cliente.setCpf(clienteDTO.getCpf());
                    cliente.setRg(clienteDTO.getRg());
                    cliente.setEndereco(clienteDTO.getEndereco());
                    cliente.setProfissao(clienteDTO.getProfissao());
                    
                    return ResponseEntity.ok(usuarioService.salvar(cliente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cliente", description = "Remove um cliente com base no ID fornecido (apenas para atendentes)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    @PreAuthorize("hasAuthority('ROLE_ATENDENTE')")
    public ResponseEntity<Void> deletar(@Parameter(description = "ID do cliente") @PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
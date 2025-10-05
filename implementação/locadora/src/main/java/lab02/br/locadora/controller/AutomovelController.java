package lab02.br.locadora.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lab02.br.locadora.dto.AutomovelDTO;
import lab02.br.locadora.model.Automovel;
import lab02.br.locadora.service.AutomovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automoveis")
@Tag(name = "Automóveis", description = "API para gerenciamento de automóveis")
@SecurityRequirement(name = "bearer-jwt")
public class AutomovelController {

    @Autowired
    private AutomovelService automovelService;

    @GetMapping
    @Operation(summary = "Listar todos os automóveis", 
               description = "Retorna uma lista com todos os automóveis cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de automóveis retornada com sucesso")
    public ResponseEntity<List<AutomovelDTO>> listarTodos() {
        List<AutomovelDTO> automoveis = automovelService.listarTodos();
        return ResponseEntity.ok(automoveis);
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Listar automóveis disponíveis", 
               description = "Retorna apenas automóveis disponíveis para aluguel")
    @ApiResponse(responseCode = "200", description = "Lista de automóveis disponíveis retornada com sucesso")
    public ResponseEntity<List<AutomovelDTO>> listarDisponiveis() {
        List<AutomovelDTO> automoveis = automovelService.listarDisponiveis();
        return ResponseEntity.ok(automoveis);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar automóvel por ID", 
               description = "Retorna os detalhes de um automóvel específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Automóvel encontrado"),
        @ApiResponse(responseCode = "404", description = "Automóvel não encontrado", content = @Content)
    })
    public ResponseEntity<AutomovelDTO> buscarPorId(
            @Parameter(description = "ID do automóvel") @PathVariable Long id) {
        return automovelService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/placa/{placa}")
    @Operation(summary = "Buscar automóvel por placa", 
               description = "Retorna um automóvel com base na placa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Automóvel encontrado"),
        @ApiResponse(responseCode = "404", description = "Automóvel não encontrado", content = @Content)
    })
    public ResponseEntity<AutomovelDTO> buscarPorPlaca(
            @Parameter(description = "Placa do automóvel") @PathVariable String placa) {
        return automovelService.buscarPorPlaca(placa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo automóvel", 
               description = "Cria um novo automóvel no sistema")
    @ApiResponse(responseCode = "200", description = "Automóvel cadastrado com sucesso")
    public ResponseEntity<AutomovelDTO> criar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do automóvel")
            @RequestBody Automovel automovel) {
        AutomovelDTO automovelDTO = automovelService.criar(automovel);
        return ResponseEntity.ok(automovelDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar automóvel", 
               description = "Atualiza os dados de um automóvel existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Automóvel atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Automóvel não encontrado", content = @Content)
    })
    public ResponseEntity<AutomovelDTO> atualizar(
            @Parameter(description = "ID do automóvel") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados do automóvel")
            @RequestBody Automovel automovel) {
        return automovelService.atualizar(id, automovel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir automóvel", 
               description = "Remove um automóvel do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Automóvel removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Automóvel não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do automóvel") @PathVariable Long id) {
        automovelService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

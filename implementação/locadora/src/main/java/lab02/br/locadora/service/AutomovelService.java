package lab02.br.locadora.service;

import lab02.br.locadora.dto.AutomovelDTO;
import lab02.br.locadora.model.Automovel;
import lab02.br.locadora.repository.AutomovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutomovelService {

    @Autowired
    private AutomovelRepository automovelRepository;

    /**
     * Lista todos os automóveis
     */
    public List<AutomovelDTO> listarTodos() {
        return automovelRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista apenas automóveis disponíveis
     */
    public List<AutomovelDTO> listarDisponiveis() {
        return automovelRepository.findByDisponivel(true).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um automóvel por ID
     */
    public Optional<AutomovelDTO> buscarPorId(Long id) {
        return automovelRepository.findById(id).map(this::toDTO);
    }

    /**
     * Busca um automóvel por placa
     */
    public Optional<AutomovelDTO> buscarPorPlaca(String placa) {
        return automovelRepository.findByPlaca(placa).map(this::toDTO);
    }

    /**
     * Cria um novo automóvel
     */
    public AutomovelDTO criar(Automovel automovel) {
        automovel.setDisponivel(true);
        Automovel salvo = automovelRepository.save(automovel);
        return toDTO(salvo);
    }

    /**
     * Atualiza um automóvel
     */
    public Optional<AutomovelDTO> atualizar(Long id, Automovel automovelAtualizado) {
        return automovelRepository.findById(id).map(automovel -> {
            automovel.setMatricula(automovelAtualizado.getMatricula());
            automovel.setAno(automovelAtualizado.getAno());
            automovel.setMarca(automovelAtualizado.getMarca());
            automovel.setModelo(automovelAtualizado.getModelo());
            automovel.setPlaca(automovelAtualizado.getPlaca());
            automovel.setProprietario(automovelAtualizado.getProprietario());
            Automovel salvo = automovelRepository.save(automovel);
            return toDTO(salvo);
        });
    }

    /**
     * Remove um automóvel
     */
    public void deletar(Long id) {
        automovelRepository.deleteById(id);
    }

    /**
     * Converte entidade Automovel para DTO
     */
    private AutomovelDTO toDTO(Automovel automovel) {
        AutomovelDTO dto = new AutomovelDTO();
        dto.setId(automovel.getId());
        dto.setMatricula(automovel.getMatricula());
        dto.setAno(automovel.getAno());
        dto.setMarca(automovel.getMarca());
        dto.setModelo(automovel.getModelo());
        dto.setPlaca(automovel.getPlaca());
        dto.setDisponivel(automovel.getDisponivel());

        if (automovel.getProprietario() != null) {
            dto.setProprietarioId(automovel.getProprietario().getId());
            dto.setProprietarioNome(automovel.getProprietario().getNome());
        }

        return dto;
    }
}

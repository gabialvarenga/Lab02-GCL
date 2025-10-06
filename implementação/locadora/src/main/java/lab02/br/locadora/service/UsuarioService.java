package lab02.br.locadora.service;


import lab02.br.locadora.model.Role;
import lab02.br.locadora.model.Usuario;
import lab02.br.locadora.model.Cliente;
import lab02.br.locadora.repository.UsuarioRepository;
import lab02.br.locadora.dto.UsuarioDTO;
import lab02.br.locadora.dto.UsuarioCreateDTO;
import lab02.br.locadora.dto.ClienteCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }
    
    public List<UsuarioDTO> listarClientes() {
        return usuarioRepository.findByRole(Role.CLIENTE).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> buscarPorId(Long id) {
        return usuarioRepository.findById(id).map(this::toDTO);
    }

    public UsuarioDTO salvar(UsuarioCreateDTO usuarioCreateDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(usuarioCreateDTO.getNome());
        cliente.setEmail(usuarioCreateDTO.getEmail());
        cliente.setPasswordHash(usuarioCreateDTO.getPassword()); // Em produção, use hash seguro!
        cliente.setRole(usuarioCreateDTO.getRole());
        Usuario salvo = usuarioRepository.save(cliente);
        return toDTO(salvo);
    }
    
    public UsuarioDTO salvar(Usuario usuario) {
        Usuario salvo = usuarioRepository.save(usuario);
        return toDTO(salvo);
    }

    public Optional<UsuarioDTO> atualizar(Long id, UsuarioCreateDTO usuarioCreateDTO) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(usuarioCreateDTO.getNome());
            usuario.setEmail(usuarioCreateDTO.getEmail());
            usuario.setPasswordHash(usuarioCreateDTO.getPassword());
            usuario.setRole(usuarioCreateDTO.getRole());
            Usuario salvo = usuarioRepository.save(usuario);
            return toDTO(salvo);
        });
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setRole(usuario.getRole());
        return dto;
    }
}

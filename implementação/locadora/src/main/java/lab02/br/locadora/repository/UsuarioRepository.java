package lab02.br.locadora.repository;

import lab02.br.locadora.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Métodos de consulta customizados podem ser adicionados aqui
}

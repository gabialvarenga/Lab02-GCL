package lab02.br.locadora.repository;

import lab02.br.locadora.model.Automovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutomovelRepository extends JpaRepository<Automovel, Long> {
    List<Automovel> findByDisponivel(Boolean disponivel);
    Optional<Automovel> findByPlaca(String placa);
}

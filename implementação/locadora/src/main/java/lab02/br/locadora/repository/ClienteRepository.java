package lab02.br.locadora.repository;

import lab02.br.locadora.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    java.util.Optional<Cliente> findByEmail(String email);
    java.util.Optional<Cliente> findByCpf(String cpf);
}

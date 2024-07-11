package br.com.fiap.techchallenge.infra.persistence;

import br.com.fiap.techchallenge.infra.persistence.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteEntityRepository extends JpaRepository<ClienteEntity, Long> {

    Optional<ClienteEntity> findByEmail(String email);

    Optional<ClienteEntity> findByCpf(String cpf);

    Optional<List<ClienteEntity>> findByEmailOrCpf(String email, String cpf);
}

package br.com.fiap.techchallenge.repository;

import br.com.fiap.techchallenge.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByCpf(String email);
    Optional<List<Cliente>> findByEmailOrCpf(String email, String cpf);

}
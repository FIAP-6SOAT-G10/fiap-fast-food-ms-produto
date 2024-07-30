package br.com.fiap.techchallenge.infra.persistence;

import br.com.fiap.techchallenge.infra.persistence.entities.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoEntityRepository extends JpaRepository<PedidoEntity, Long> {
    @Query(value = "SELECT p FROM PedidoEntity p JOIN FETCH p.produtos WHERE p.id = :id")
    Optional<PedidoEntity> loadPedidoById(@Param("id") Long id);
}
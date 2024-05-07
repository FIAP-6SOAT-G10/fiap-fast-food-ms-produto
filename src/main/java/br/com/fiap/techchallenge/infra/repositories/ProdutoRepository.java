package br.com.fiap.techchallenge.infra.repositories;

import br.com.fiap.techchallenge.domain.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {}

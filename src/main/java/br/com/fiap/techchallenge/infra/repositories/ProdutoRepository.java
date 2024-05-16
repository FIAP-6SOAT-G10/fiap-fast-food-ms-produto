package br.com.fiap.techchallenge.infra.repositories;

import br.com.fiap.techchallenge.domain.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<List<Produto>> findByNomeOrDescricaoOrPreco(String nome, String descricao, BigDecimal preco);


}

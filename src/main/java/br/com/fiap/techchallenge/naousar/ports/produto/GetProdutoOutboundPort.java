package br.com.fiap.techchallenge.naousar.ports.produto;

import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
import br.com.fiap.techchallenge.naousar.domain.valueobjects.ProdutoDTO;

import java.math.BigDecimal;
import java.util.List;

public interface GetProdutoOutboundPort {
    List<ProdutoDTO> listarProdutos(Integer page, Integer size, String nome, String descricao, BigDecimal preco);

    List<ProdutoEntity> listarProdutosPorCategoria(CategoriaEnum categoriaEnum);
}
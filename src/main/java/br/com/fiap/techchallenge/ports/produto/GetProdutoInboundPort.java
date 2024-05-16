package br.com.fiap.techchallenge.ports.produto;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;

import java.math.BigDecimal;
import java.util.List;

public interface GetProdutoInboundPort {
    List<ProdutoDTO> listarProdutos(Integer page, Integer size, String nome, String descricao, BigDecimal preco);

    List<Produto> pegaProdutosPorCategoria(String categoria);
}
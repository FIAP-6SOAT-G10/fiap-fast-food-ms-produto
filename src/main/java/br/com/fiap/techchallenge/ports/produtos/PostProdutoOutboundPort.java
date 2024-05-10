package br.com.fiap.techchallenge.ports.produtos;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;

public interface PostProdutoOutboundPort {
    Produto criarProduto(ProdutoDTO produtoDTO);
}

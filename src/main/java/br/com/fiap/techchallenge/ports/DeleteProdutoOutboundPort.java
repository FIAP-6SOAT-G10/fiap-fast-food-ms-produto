package br.com.fiap.techchallenge.ports;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;

public interface DeleteProdutoOutboundPort {
    Produto deletarProduto(Long id, ProdutoDTO produtoDTO);
}
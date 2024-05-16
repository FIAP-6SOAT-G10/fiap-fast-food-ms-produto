package br.com.fiap.techchallenge.ports.produto;

import br.com.fiap.techchallenge.domain.entities.Produto;

public interface DeleteProdutoInboundPort {
    Produto deletarProduto(String id);
}
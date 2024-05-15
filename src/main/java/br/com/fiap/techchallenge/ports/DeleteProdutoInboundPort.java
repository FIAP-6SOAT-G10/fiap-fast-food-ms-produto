package br.com.fiap.techchallenge.ports;

import br.com.fiap.techchallenge.domain.entities.Produto;

public interface DeleteProdutoInboundPort {
    Produto deletarProduto(String id);
}
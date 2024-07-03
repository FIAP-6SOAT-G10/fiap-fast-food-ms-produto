package br.com.fiap.techchallenge.naousar.ports.produto;

import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;

public interface DeleteProdutoInboundPort {
    ProdutoEntity deletarProduto(String id);
}
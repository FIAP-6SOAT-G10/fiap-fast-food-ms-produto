package br.com.fiap.techchallenge.naousar.ports.produto;

import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;

public interface DeleteProdutoOutboundPort {
    ProdutoEntity deletarProduto(Long id);
}
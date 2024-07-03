package br.com.fiap.techchallenge.naousar.ports.produto;

import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
import br.com.fiap.techchallenge.naousar.domain.valueobjects.ProdutoDTO;

public interface PutProdutoInboundPort {
    ProdutoEntity atualizarProduto(String id, ProdutoDTO produtoDTO);
}
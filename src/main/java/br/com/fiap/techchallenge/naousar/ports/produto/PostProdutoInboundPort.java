package br.com.fiap.techchallenge.naousar.ports.produto;

import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
import br.com.fiap.techchallenge.naousar.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.BaseException;

public interface PostProdutoInboundPort {
    ProdutoEntity criarProduto(ProdutoDTO produtoDTO) throws BaseException;
}

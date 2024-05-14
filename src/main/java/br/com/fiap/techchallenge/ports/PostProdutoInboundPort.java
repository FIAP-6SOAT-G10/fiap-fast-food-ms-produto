package br.com.fiap.techchallenge.ports;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.BaseException;

public interface PostProdutoInboundPort {
    Produto criarProduto(ProdutoDTO produtoDTO) throws BaseException;
}

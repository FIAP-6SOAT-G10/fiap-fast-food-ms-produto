package br.com.fiap.techchallenge.ports.produtos;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;

public interface PostProdutoInboundPort {
    Produto criarProduto(ProdutoDTO produtoDTO) throws BaseException;
}

package br.com.fiap.techchallenge.ports;

import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;

import java.math.BigDecimal;
import java.util.List;

public interface IGetProdutosUseCase {

    List<ProdutoDTO> listarProdutos(Integer page, Integer size, String nome, String descricao, BigDecimal preco);

}

package br.com.fiap.techchallenge.ports;

import br.com.fiap.techchallenge.domain.model.enums.CategoriaEnum;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;

import java.util.List;

public interface GetProdutoOutboundPort {
    List<ProdutoDTO> pegaProdutosPorCategoria(CategoriaEnum categoriaEnum);
}
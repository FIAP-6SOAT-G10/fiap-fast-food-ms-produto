package br.com.fiap.techchallenge.ports;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.enums.CategoriaEnum;

import java.util.List;

public interface GetProdutoOutboundPort {
    List<Produto> pegaProdutosPorCategoria(CategoriaEnum categoriaEnum);
}
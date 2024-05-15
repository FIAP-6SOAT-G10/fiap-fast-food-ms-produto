package br.com.fiap.techchallenge.ports;

import br.com.fiap.techchallenge.domain.entities.Produto;

import java.util.List;

public interface GetProdutoInboundPort {
    List<Produto> pegaProdutosPorCategoria(String categoria);
}
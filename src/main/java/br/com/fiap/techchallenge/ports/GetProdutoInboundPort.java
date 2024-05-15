package br.com.fiap.techchallenge.ports;

import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;

import java.util.List;

public interface GetProdutoInboundPort {
    List<ProdutoDTO> pegaProdutosPorCategoria(String categoria);
}
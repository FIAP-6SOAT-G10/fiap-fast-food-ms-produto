package br.com.fiap.techchallenge.naousar.ports.categoria;

import br.com.fiap.techchallenge.naousar.domain.valueobjects.CategoriaDTO;

import java.util.List;

public interface GetCategoriaInboundPort {
    List<CategoriaDTO> listarCategorias();
}

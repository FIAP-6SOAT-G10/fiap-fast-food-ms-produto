package br.com.fiap.techchallenge.ports.categoria;

import br.com.fiap.techchallenge.domain.valueobjects.CategoriaDTO;

import java.util.List;

public interface GetCategoriaInboundPort {
    List<CategoriaDTO> listarCategorias();
}

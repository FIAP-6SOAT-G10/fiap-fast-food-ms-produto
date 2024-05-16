package br.com.fiap.techchallenge.ports;

import br.com.fiap.techchallenge.domain.valueobjects.CategoriaDTO;

import java.util.List;

public interface GetCategoriaOutboundPort {
    List<CategoriaDTO> listarCategorias();
}
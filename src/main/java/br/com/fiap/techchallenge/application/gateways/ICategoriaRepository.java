package br.com.fiap.techchallenge.application.gateways;

import br.com.fiap.techchallenge.domain.entities.produto.Categoria;

import java.util.List;

public interface ICategoriaRepository {

    List<Categoria> listarCategorias();

}

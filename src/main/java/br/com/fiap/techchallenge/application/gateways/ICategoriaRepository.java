package br.com.fiap.techchallenge.application.gateways;

import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;

import java.util.List;

public interface ICategoriaRepository {

    List<CategoriaEntity> listarCategorias();

}

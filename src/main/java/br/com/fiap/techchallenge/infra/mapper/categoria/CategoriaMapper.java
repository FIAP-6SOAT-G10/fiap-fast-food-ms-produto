package br.com.fiap.techchallenge.infra.mapper.categoria;

import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;

public class CategoriaMapper {

    public CategoriaEntity fromDomainToEntity(CategoriaEnum categoria) {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(categoria.getIdCategoria());
        categoriaEntity.setNome(categoria.getNome());
        return categoriaEntity;
    }

}
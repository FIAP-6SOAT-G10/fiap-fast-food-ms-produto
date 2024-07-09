package br.com.fiap.techchallenge.infra.gateways.categorias;

import br.com.fiap.techchallenge.application.gateways.ICategoriaRepository;
import br.com.fiap.techchallenge.infra.mapper.categoria.CategoriaMapper;
import br.com.fiap.techchallenge.infra.persistence.CategoriaEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;

import java.util.List;

public class CategoriaRepository implements ICategoriaRepository {

    private final CategoriaEntityRepository categoriaRepository;


    public CategoriaRepository(CategoriaEntityRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<CategoriaEntity> listarCategorias() {
         List<CategoriaEntity> categorias = categoriaRepository.findAll();
         return categorias;
    }
}

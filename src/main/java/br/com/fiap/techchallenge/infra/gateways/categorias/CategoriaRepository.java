package br.com.fiap.techchallenge.infra.gateways.categorias;

import br.com.fiap.techchallenge.application.gateways.ICategoriaRepository;
import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import br.com.fiap.techchallenge.infra.persistence.CategoriaEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;

import java.util.ArrayList;
import java.util.List;

public class CategoriaRepository implements ICategoriaRepository {

    private final CategoriaEntityRepository categoriaEntityRepository;

    public CategoriaRepository(CategoriaEntityRepository categoriaRepository){
        this.categoriaEntityRepository = categoriaRepository;
    }

    @Override
    public List<Categoria> listarCategorias() {
         List<Categoria> allCategories = new ArrayList<>();
         List<CategoriaEntity> categorias = categoriaEntityRepository.findAll();
         categorias.stream().forEach(categoriaEntity ->
             allCategories.add( new Categoria(categoriaEntity.getNome(), categoriaEntity.getDescricao()))
         );
         return allCategories;
    }
}

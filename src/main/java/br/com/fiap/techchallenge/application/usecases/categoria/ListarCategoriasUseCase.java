package br.com.fiap.techchallenge.application.usecases.categoria;

import br.com.fiap.techchallenge.application.gateways.ICategoriaRepository;
import br.com.fiap.techchallenge.domain.entities.produto.Categoria;

import java.util.List;

public class ListarCategoriasUseCase {

    private final ICategoriaRepository categoriaRepository;

    public ListarCategoriasUseCase(ICategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarCategorias() {
        return this.categoriaRepository.listarCategorias();
    }


}

package br.com.fiap.techchallenge.adapters.categoria;

import br.com.fiap.techchallenge.domain.entities.Categoria;
import br.com.fiap.techchallenge.domain.model.mapper.categoria.CategoriaMapper;
import br.com.fiap.techchallenge.domain.valueobjects.CategoriaDTO;
import br.com.fiap.techchallenge.infra.repositories.CategoriaRepository;
import br.com.fiap.techchallenge.ports.categoria.GetCategoriaOutboundPort;

import java.util.List;

public class GetCategoriaAdapter implements GetCategoriaOutboundPort {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper mapper;

    public GetCategoriaAdapter(CategoriaRepository categoriaRepository, CategoriaMapper mapper) {
        this.categoriaRepository = categoriaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CategoriaDTO> listarCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return mapper.fromListEntityToListDTO(categorias);
    }
}

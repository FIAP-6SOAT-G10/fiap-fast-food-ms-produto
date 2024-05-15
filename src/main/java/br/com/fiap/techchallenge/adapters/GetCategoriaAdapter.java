package br.com.fiap.techchallenge.adapters;

import br.com.fiap.techchallenge.domain.entities.Categoria;
import br.com.fiap.techchallenge.domain.model.mapper.CategoriaMapper;
import br.com.fiap.techchallenge.domain.valueobjects.CategoriaDTO;
import br.com.fiap.techchallenge.infra.repositories.CategoriaRepository;
import br.com.fiap.techchallenge.ports.GetCategoriaOutboundPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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

//package br.com.fiap.techchallenge.naousar.adapters.categoria;
//
//import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;
//import br.com.fiap.techchallenge.infra.mapper.categoria.CategoriaMapper;
//import br.com.fiap.techchallenge.naousar.domain.valueobjects.CategoriaDTO;
//import br.com.fiap.techchallenge.infra.persistence.CategoriaRepository;
//import br.com.fiap.techchallenge.naousar.ports.categoria.GetCategoriaOutboundPort;
//
//import java.util.List;
//
//public class GetCategoriaAdapter implements GetCategoriaOutboundPort {
//
//    private final CategoriaRepository categoriaRepository;
//    private final CategoriaMapper mapper;
//
//    public GetCategoriaAdapter(CategoriaRepository categoriaRepository, CategoriaMapper mapper) {
//        this.categoriaRepository = categoriaRepository;
//        this.mapper = mapper;
//    }
//
//    @Override
//    public List<CategoriaDTO> listarCategorias() {
//        List<CategoriaEntity> categoriaEntities = categoriaRepository.findAll();
//        return mapper.fromListEntityToListDTO(categoriaEntities);
//    }
//}

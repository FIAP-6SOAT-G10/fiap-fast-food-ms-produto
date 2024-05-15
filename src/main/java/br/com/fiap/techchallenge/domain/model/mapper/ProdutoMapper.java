package br.com.fiap.techchallenge.domain.model.mapper;

import br.com.fiap.techchallenge.domain.entities.Categoria;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.enums.CategoriaEnum;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProdutoMapper {
    @Mappings({
            @Mapping(source = "produtoDTO.categoria.idCategoria", target = "categoria.id")
    })
    Produto fromDTOToEntity(ProdutoDTO produtoDTO);

    @Mapping(source = "produto.categoria", target = "categoria", qualifiedByName = "toCategoriaEnum")
    ProdutoDTO fromEntityToDTO(Produto produto);

    List<ProdutoDTO> fromListEntityToListDTO(List<Produto> produtos);

    @Named("toCategoriaEnum")
    default CategoriaEnum fromCategoriaToCategoriaEnum(Categoria categoria) {
        return CategoriaEnum.fromName(categoria.getNome());
    }
}
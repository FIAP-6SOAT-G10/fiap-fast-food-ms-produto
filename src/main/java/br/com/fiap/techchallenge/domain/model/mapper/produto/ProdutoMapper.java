package br.com.fiap.techchallenge.domain.model.mapper.produto;

import br.com.fiap.techchallenge.domain.entities.Categoria;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.enums.CategoriaEnum;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProdutoMapper {
    @Mapping(source = "produtoDTO.categoria.idCategoria", target = "categoria.id")
    Produto fromDTOToEntity(ProdutoDTO produtoDTO);

    @Mapping(source = "categoria", target = "categoria", qualifiedByName = "toCategoriaEnum")
    ProdutoDTO fromEntityToDTO(Produto produto);

    @Mapping(source = "categoria", target = "categoria", qualifiedByName = "toCategoriaEnum")
    List<ProdutoDTO> fromListEntityToListDTO(List<Produto> produtos);

    @Named("toCategoriaEnum")
    default CategoriaEnum categoriaToCategoriaEnum(Categoria categoria) {
        return CategoriaEnum.fromIdCategoria(categoria.getId());
    }

}


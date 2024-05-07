package br.com.fiap.techchallenge.domain.model.mapper;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProdutoMapper {
    @Mappings({
            @Mapping(source = "produtoDTO.categoria.idCategoria", target = "categoria.id")
    })
    Produto fromDTOToEntity(ProdutoDTO produtoDTO);
}
package br.com.fiap.techchallenge.domain.model.mapper;

import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProdutoMapper {
    @Mappings({
            @Mapping(source = "produtoDTO.categoria.idCategoria", target = "categoria.id")
    })
    Produto fromDTOToEntity(ProdutoDTO produtoDTO);

    @Mappings({
            @Mapping(source = "produtos.categoria.id", target = "produtoDTO.categoria.idCategoria")
    })
    List<ProdutoDTO> fromListEntityToListDTO(List<Produto> produtos);

}
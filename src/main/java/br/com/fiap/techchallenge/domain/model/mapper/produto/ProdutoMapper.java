package br.com.fiap.techchallenge.domain.model.mapper.produto;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.mapper.categoria.CategoriaMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
        CategoriaMapper.class
})
public interface ProdutoMapper {

    Produto fromDTOToEntity(ProdutoDTO produtoDTO);

    ProdutoDTO fromEntityToDTO(Produto produto);

    List<ProdutoDTO> fromListEntityToListDTO(List<Produto> produtos);

}
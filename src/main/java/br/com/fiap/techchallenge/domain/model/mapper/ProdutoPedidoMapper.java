package br.com.fiap.techchallenge.domain.model.mapper;

import br.com.fiap.techchallenge.domain.entities.ProdutoPedido;
import br.com.fiap.techchallenge.domain.model.mapper.categoria.CategoriaMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoPedidoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
        CategoriaMapper.class
})
public interface ProdutoPedidoMapper {

    ProdutoPedidoDTO fromEntityToDTO(ProdutoPedido produtoPedido);

    List<ProdutoPedidoDTO> fromListEntityToListDTO(List<ProdutoPedido> produtosPedido);

}

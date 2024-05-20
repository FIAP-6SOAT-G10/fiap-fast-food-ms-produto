package br.com.fiap.techchallenge.domain.model.mapper.pedido;

import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.model.mapper.categoria.CategoriaMapper;
import br.com.fiap.techchallenge.domain.model.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
        CategoriaMapper.class,
        ProdutoMapper.class
})
public interface PedidoMapper {

    PedidoDTO toDTO(Pedido pedido);

    Pedido toEntity(PedidoDTO pedidoDTO);

    List<PedidoDTO> fromListEntityToListDTO(List<Pedido> pedidos);

    List<Pedido> fromListDTOToListEntity(List<PedidoDTO> pedidos);

}
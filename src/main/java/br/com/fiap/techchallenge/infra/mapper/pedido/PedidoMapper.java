package br.com.fiap.techchallenge.infra.mapper.pedido;

import br.com.fiap.techchallenge.infra.controllers.PedidoDTO;
import br.com.fiap.techchallenge.infra.mapper.categoria.CategoriaMapper;
import br.com.fiap.techchallenge.infra.persistence.entities.Pedido;
import br.com.fiap.techchallenge.infra.mapper.produto.ProdutoMapper;
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

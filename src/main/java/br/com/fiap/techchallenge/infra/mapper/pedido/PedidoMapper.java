package br.com.fiap.techchallenge.infra.mapper.pedido;

import br.com.fiap.techchallenge.infra.controllers.PedidoDTO;
import br.com.fiap.techchallenge.infra.mapper.categoria.CategoriaMapper;
import br.com.fiap.techchallenge.infra.persistence.entities.Pedido;
import br.com.fiap.techchallenge.infra.mapper.produto.ProdutoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

public class PedidoMapper {

    public PedidoDTO toDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        return dto;
    }

    public Pedido toEntity(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setId(pedidoDTO.getId());
        return pedido;
    }

    public List<PedidoDTO> fromListEntityToListDTO(List<Pedido> pedidos) {
        return pedidos.stream().map(this::toDTO).toList();
    }

    public List<Pedido> fromListDTOToListEntity(List<PedidoDTO> pedidos) {
        return pedidos.stream().map(this::toEntity).toList();
    }

}

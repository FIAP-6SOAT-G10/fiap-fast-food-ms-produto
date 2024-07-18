package br.com.fiap.techchallenge.infra.mapper.pedido;

import br.com.fiap.techchallenge.domain.entities.cliente.Cliente;
import br.com.fiap.techchallenge.domain.entities.pedido.Pedido;
import br.com.fiap.techchallenge.domain.entities.pedido.ProdutoPedido;
import br.com.fiap.techchallenge.domain.entities.pedido.StatusPedido;
import br.com.fiap.techchallenge.infra.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.infra.persistence.entities.PedidoEntity;
import java.util.List;

public class PedidoMapper {

    public Pedido fromEntityToDomain(PedidoEntity pedidoEntity) {
        Cliente cliente = new ClienteMapper().fromEntityToDomain(pedidoEntity.getCliente());

        StatusPedido status = new StatusPedido(pedidoEntity.getStatus().getNome());

//        List<ProdutoPedido> produtosPedidos = new ProdutoPedidoMapperImpl().fromListEntityToListDTO(pedidoEntity.getProdutos());

        Pedido pedido = new Pedido();
        pedido.setId(pedidoEntity.getId());
        pedido.setCliente(cliente);
        pedido.setValor(pedidoEntity.getValor());
        pedido.setDataCriacao(pedidoEntity.getDataCriacao());
        pedido.setDataFinalizacao(pedidoEntity.getDataFinalizacao());
        pedido.setDataCancelamento(pedidoEntity.getDataCancelamento());
        pedido.setStatus(status);
//        pedido.setProdutoPedidos(produtosPedidos);

        return pedido;
    }

    public PedidoEntity fromDomainToEntity(Pedido pedido) {
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setId(pedido.getId());
        return pedidoEntity;
    }

    public List<Pedido> fromListEntityToListDTO(List<PedidoEntity> pedidos) {
        return pedidos.stream().map(this::fromEntityToDomain).toList();
    }

    public List<PedidoEntity> fromListDTOToListEntity(List<Pedido> pedidos) {
        return pedidos.stream().map(this::fromDomainToEntity).toList();
    }

}

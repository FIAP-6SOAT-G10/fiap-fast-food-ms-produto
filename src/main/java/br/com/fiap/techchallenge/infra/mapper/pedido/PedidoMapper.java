package br.com.fiap.techchallenge.infra.mapper.pedido;

import br.com.fiap.techchallenge.domain.entities.cliente.Cliente;
import br.com.fiap.techchallenge.domain.entities.pagamento.StatusPagamento;
import br.com.fiap.techchallenge.domain.entities.pedido.*;
import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.infra.controllers.pedido.PedidoDTO;
import br.com.fiap.techchallenge.infra.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.infra.mapper.produtopedido.ProdutoPedidoMapper;
import br.com.fiap.techchallenge.infra.persistence.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidoMapper {

    private final ClienteMapper clienteMapper = new ClienteMapper();
    private final ProdutoPedidoMapper produtoPedidoMapper = new ProdutoPedidoMapper();

    public Pedido fromEntityToDomain(PedidoEntity pedidoEntity) {

        Cliente cliente = clienteMapper.fromEntityToDomain(pedidoEntity.getCliente());

        StatusPedido status = new StatusPedido(pedidoEntity.getStatus().getId());
        StatusPagamento statusPagamento = new StatusPagamento(pedidoEntity.getStatusPagamento().getId());

        List<ProdutoPedido> produtosPedidos = produtoPedidoMapper.fromListEntityToListDomain(pedidoEntity.getProdutos());
        Map<Long, List<ItemPedido>> mapItemPedido = buildMapItemPedido(produtosPedidos);
        List<ItemPedido> lanches = mapItemPedido.get(CategoriaEnum.LANCHE.getIdCategoria());
        List<ItemPedido> bebida = mapItemPedido.get(CategoriaEnum.BEBIDA.getIdCategoria());
        List<ItemPedido> acompanhamento = mapItemPedido.get(CategoriaEnum.ACOMPANHAMENTO.getIdCategoria());
        List<ItemPedido> sobremesa = mapItemPedido.get(CategoriaEnum.SOBREMESA.getIdCategoria());

        return new Pedido(
                pedidoEntity.getId(),
                cliente,
                status,
                pedidoEntity.getValor(),
                pedidoEntity.getDataCriacao(),
                pedidoEntity.getDataFinalizacao(),
                pedidoEntity.getDataCancelamento(),
                statusPagamento,
                produtosPedidos,
                new Item(lanches,bebida,acompanhamento,sobremesa));
    }

    public Pedido fromDTOToDomain(PedidoDTO pedidoDTO) {
        Cliente cliente = new ClienteMapper().fromDTOToDomain(pedidoDTO.getCliente());
        List<ProdutoPedido> produtosPedidos = pedidoDTO.getProdutos();
        return new Pedido(cliente, pedidoDTO.getValor(), produtosPedidos);
    }

    public PedidoEntity fromDomainToEntity(Pedido pedido) {
        PedidoEntity pedidoEntity = new PedidoEntity();

        if (pedido.getCliente() != null) {
            Cliente cliente = pedido.getCliente();
            pedidoEntity.setCliente(new ClienteEntity(cliente.getId(), cliente.getCpf(), cliente.getNome(), cliente.getEmail()));
        }

        StatusPedido status = pedido.getStatus();
        StatusPedidoEntity statusPedidoEntity = new StatusPedidoEntity();
        statusPedidoEntity.setId(status.getId());
        statusPedidoEntity.setNome(status.getNome());

        StatusPagamento statusPagamento = pedido.getStatusPagamento();
        StatusPagamentoEntity statusPagamentoEntity = new StatusPagamentoEntity();
        statusPagamentoEntity.setId(statusPagamento.getId());
        statusPagamentoEntity.setNome(statusPagamento.getNome());

        pedidoEntity.setId(pedido.getId());
        pedidoEntity.setStatus(statusPedidoEntity);
        pedidoEntity.setStatusPagamento(statusPagamentoEntity);
        pedidoEntity.setValor(pedido.getValor());
        pedidoEntity.setProdutos(produtoPedidoMapper.fromListDomainToListEntity(pedido.getProdutoPedidos()));

        return pedidoEntity;
    }

    private ProdutoPedidoEntity toEntity(ProdutoPedido produtoPedido) {
        Produto produto = produtoPedido.getProduto();
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(produto.getId());

        ProdutoPedidoEntity produtoPedidoEntity = new ProdutoPedidoEntity();
        produtoPedidoEntity.setProdutoEntity(produtoEntity);
        return produtoPedidoEntity;
    }

    public List<Pedido> fromListEntityToListDTO(List<PedidoEntity> pedidos) {
        return pedidos.stream().map(this::fromEntityToDomain).toList();
    }

    public List<PedidoEntity> fromListDTOToListEntity(List<Pedido> pedidos) {
        return pedidos.stream().map(this::fromDomainToEntity).toList();
    }

    private static Map<Long, List<ItemPedido>> buildMapItemPedido(List<ProdutoPedido> produtosPedidos) {
        Map<Long, List<ItemPedido>> mapItemPedido = new HashMap<>();
        produtosPedidos.stream().forEach(it -> {

            ItemPedido itemPedido = new ItemPedido(it.getPedido().getId(), it.getQuantidade().longValue());
            CategoriaEnum categoriaEnum = CategoriaEnum.fromName(it.getProduto().getCategoria().getNome());

            if(mapItemPedido.isEmpty()) {
                List<ItemPedido> list = new ArrayList<>();
                list.add(itemPedido);
                mapItemPedido.put(categoriaEnum.getIdCategoria(), list);
            }

            if(mapItemPedido.containsKey(categoriaEnum.getIdCategoria())) {
                mapItemPedido.get(categoriaEnum.getIdCategoria()).add(itemPedido);
            }else {
                List<ItemPedido> list = new ArrayList<>();
                list.add(itemPedido);
                mapItemPedido.put(categoriaEnum.getIdCategoria(), list);
            }
        });
        return mapItemPedido;
    }

}

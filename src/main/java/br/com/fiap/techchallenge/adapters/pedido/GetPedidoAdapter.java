package br.com.fiap.techchallenge.adapters.pedido;

import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import br.com.fiap.techchallenge.ports.pedido.GetPedidoOutboundPort;

public class GetPedidoAdapter implements GetPedidoOutboundPort {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    public GetPedidoAdapter(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public PedidoDTO buscarPedidoPorId(Long id) {
        try {
            Pedido pedido = this.pedidoRepository.findById(id).orElseThrow();
            return this.pedidoMapper.toDTO(pedido);
        } catch (Exception e) {
            return null;
        }
    }
}

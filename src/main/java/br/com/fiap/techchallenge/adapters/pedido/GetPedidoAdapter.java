package br.com.fiap.techchallenge.adapters.pedido;

import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import br.com.fiap.techchallenge.ports.pedido.GetPedidoOutboundPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<PedidoDTO> listarPedidos(Integer page, Integer size) {
        List<Pedido> listaPedido = new ArrayList<>();

        PageRequest pageable = PageRequest.of(page, size);
        Page<Pedido> pagePedido = pedidoRepository.findAll(pageable);

        if (pagePedido != null) {
            listaPedido.addAll(pagePedido.toList());
        }

        return pedidoMapper.fromListEntityToListDTO(listaPedido);
    }
}

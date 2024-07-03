package br.com.fiap.techchallenge.naousar.domain.usecases.pedido;

import br.com.fiap.techchallenge.naousar.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.naousar.domain.valueobjects.PedidoRequestDTO;
import br.com.fiap.techchallenge.naousar.domain.valueobjects.response.PedidoResponseDTO;
import br.com.fiap.techchallenge.infra.exception.PedidoException;
import br.com.fiap.techchallenge.naousar.ports.cliente.PostPedidoInboundPort;
import br.com.fiap.techchallenge.naousar.ports.cliente.PostPedidoOutboundPort;
import lombok.extern.slf4j.Slf4j;

import static br.com.fiap.techchallenge.domain.ErrosEnum.PEDIDO_VAZIO;

@Slf4j
public class PostPedidoUseCase implements PostPedidoInboundPort {

    private final PostPedidoOutboundPort port;

    public PostPedidoUseCase(PostPedidoOutboundPort postPedidoAdapter) {
        this.port = postPedidoAdapter;
    }

    @Override
    public PedidoDTO realizarCheckout(Long id) throws InterruptedException {
        return port.realizarCheckout(id);
    }

    @Override
    public PedidoResponseDTO criarPedido(PedidoRequestDTO request) {
        if (itensPedidoVazio(request)) {
            throw new PedidoException(PEDIDO_VAZIO);
        }
        PedidoDTO pedido = this.port.criarPedido(request);
        return PedidoResponseDTO
                .builder()
                .id(pedido.getId())
                .total(pedido.getValor())
                .build();
    }

    private boolean itensPedidoVazio(PedidoRequestDTO request) {
        return request.getItems().getAcompanhamento().isEmpty() &&
                request.getItems().getBebida().isEmpty() &&
                request.getItems().getLanches().isEmpty() &&
                request.getItems().getSobremesa().isEmpty();
    }
}

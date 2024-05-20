package br.com.fiap.techchallenge.adapters.pedido;

import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.entities.StatusPagamento;
import br.com.fiap.techchallenge.domain.entities.StatusPedido;
import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import br.com.fiap.techchallenge.domain.model.mapper.ProdutoPedidoMapper;
import br.com.fiap.techchallenge.domain.model.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.exception.PedidoException;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import br.com.fiap.techchallenge.ports.cliente.PostPedidoOutboundPort;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PostPedidoAdapter implements PostPedidoOutboundPort {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper mapper;
    private final ClienteMapper clienteMapper;
    private final ProdutoPedidoMapper produtoPedidoMapper;

    public PostPedidoAdapter(PedidoRepository pedidoRepository, PedidoMapper mapper, ClienteMapper clienteMapper, ProdutoPedidoMapper produtoPedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.mapper = mapper;
        this.clienteMapper = clienteMapper;
        this.produtoPedidoMapper = produtoPedidoMapper;
    }

    @Override
    public PedidoDTO realizarCheckout(Long id) throws BaseException, InterruptedException {
        log.info("realizarCheckout");

        TimeUnit.SECONDS.sleep(5);

        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isEmpty()) {
            throw new PedidoException(ErrosEnum.PEDIDO_CODIGO_IDENTIFICADOR_INVALIDO);
        }

        Pedido pedido = pedidoOptional.get();
        pedido.setStatus(new StatusPedido("recebido"));
        pedido.setStatusPagamento(new StatusPagamento("pago"));

        return mapper.toDTO(pedidoRepository.saveAndFlush(pedido));
    }

    private ClienteDTO fromClienteToClienteDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .email(cliente.getEmail()).build();
    }

}

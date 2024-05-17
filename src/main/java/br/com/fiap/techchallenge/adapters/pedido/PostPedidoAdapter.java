package br.com.fiap.techchallenge.adapters.pedido;

import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.entities.StatusPagamento;
import br.com.fiap.techchallenge.domain.entities.StatusPedido;
import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import br.com.fiap.techchallenge.domain.model.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.domain.valueobjects.StatusPagamentoDTO;
import br.com.fiap.techchallenge.domain.valueobjects.StatusPedidoDTO;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.infra.exception.PedidoException;
import br.com.fiap.techchallenge.infra.repositories.ClienteRepository;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import br.com.fiap.techchallenge.ports.cliente.PostClienteOutboundPort;
import br.com.fiap.techchallenge.ports.cliente.PostPedidoOutboundPort;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static br.com.fiap.techchallenge.domain.model.enums.ErrosEnum.CLIENTE_JA_CADASTRADO;

@Slf4j
public class PostPedidoAdapter implements PostPedidoOutboundPort {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper mapper;

    public PostPedidoAdapter(PedidoRepository pedidoRepository, PedidoMapper mapper) {
        this.pedidoRepository = pedidoRepository;
        this.mapper = mapper;
    }

    @Override
    public PedidoDTO realizarCheckout(Long id) throws BaseException, InterruptedException {
        log.info("realizarCheckout");

        TimeUnit.SECONDS.sleep(5);

        Optional<Pedido> existingPedidoOpt = pedidoRepository.findById(id);

        if (existingPedidoOpt == null || !existingPedidoOpt.isPresent()) {
            throw new PedidoException(ErrosEnum.PEDIDO_CODIGO_IDENTIFICADOR_INVALIDO);
        }

        StatusPagamentoDTO statusPagamento = new StatusPagamentoDTO();
        statusPagamento.setId(1L);
        StatusPedidoDTO statusPedido = new StatusPedidoDTO();
        statusPedido.setId(1L);

        Pedido existingPedido = existingPedidoOpt.get();
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(existingPedido.getId());
        pedidoDTO.setCliente(existingPedido.getCliente());
        pedidoDTO.setStatus(statusPedido);
        pedidoDTO.setValor(existingPedido.getValor());
        pedidoDTO.setDataCriacao(existingPedido.getDataCriacao());
        pedidoDTO.setDataFinalizacao(existingPedido.getDataFinalizacao());
        pedidoDTO.setDataCancelamento(existingPedido.getDataCancelamento());
        pedidoDTO.setPagamento(statusPagamento);
        pedidoDTO.setProdutos(existingPedido.getProdutos());

        return mapper.toDTO(pedidoRepository.saveAndFlush(mapper.toEntity(pedidoDTO)));
    }
}

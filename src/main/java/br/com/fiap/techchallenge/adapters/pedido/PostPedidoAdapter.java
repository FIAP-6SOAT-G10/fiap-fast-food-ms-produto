package br.com.fiap.techchallenge.adapters.pedido;

import br.com.fiap.techchallenge.domain.entities.*;
import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import br.com.fiap.techchallenge.domain.model.enums.StatusPagamentoEnum;
import br.com.fiap.techchallenge.domain.model.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.*;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.exception.PedidoException;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import br.com.fiap.techchallenge.ports.cliente.PostPedidoOutboundPort;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class PostPedidoAdapter implements PostPedidoOutboundPort {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final ProdutoRepository produtoRepository;

    public PostPedidoAdapter(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.produtoRepository = produtoRepository;
    }

    @Override
    public PedidoDTO realizarCheckout(Long id) throws BaseException, InterruptedException {
        log.info("realizarCheckout");

        TimeUnit.SECONDS.sleep(5);

        Optional<Pedido> existingPedidoOpt = pedidoRepository.findById(id);

        if (existingPedidoOpt == null || !existingPedidoOpt.isPresent()) {
            throw new PedidoException(ErrosEnum.PEDIDO_INVALIDO);
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
        pedidoDTO.setStatusPagamentoDTO(statusPagamento);
        pedidoDTO.setProdutos(existingPedido.getProdutos());

        return pedidoMapper.toDTO(pedidoRepository.saveAndFlush(pedidoMapper.toEntity(pedidoDTO)));
    }

    private List<ProdutoPedido> sumarizaItemDoPedido(List<ItemPedidoDTO> item) {
        List<ProdutoPedido> todosOsItensDoPedidoSumarizado = new ArrayList<>();
        Map<Long, List<ItemPedidoDTO>> itensDoPedido = item.stream().collect(Collectors.groupingBy(ItemPedidoDTO::getId));
        itensDoPedido.forEach((key, value) -> {
            Produto produto = produtoRepository.findById(key).orElseThrow(()-> new ProdutoException(ErrosEnum.PRODUTO_NAO_ENCONTRADO));
            ProdutoPedido produtoPedido = ProdutoPedido.builder().build();
            produtoPedido.setQuantidade(BigInteger.ZERO);
            produtoPedido.setValorTotal(BigDecimal.ZERO);
            value.forEach(valueIt -> {
                produtoPedido.getQuantidade().add(BigInteger.valueOf(valueIt.getQuantidade()));
                produtoPedido.getValorTotal().add(produto.getPreco().multiply(new BigDecimal(valueIt.getQuantidade())));
            });
            todosOsItensDoPedidoSumarizado.add(produtoPedido);
        });
        return todosOsItensDoPedidoSumarizado;
    }

    @Override
    public PedidoDTO criarPedido(PedidoRequestDTO request) {
        List<ProdutoPedido> todosOsItensDoPedidoSumarizado = new ArrayList<>();
        todosOsItensDoPedidoSumarizado.addAll(sumarizaItemDoPedido(request.getItems().getLanches()));
        todosOsItensDoPedidoSumarizado.addAll(sumarizaItemDoPedido(request.getItems().getAcompanhamento()));
        todosOsItensDoPedidoSumarizado.addAll(sumarizaItemDoPedido(request.getItems().getBebida()));
        todosOsItensDoPedidoSumarizado.addAll(sumarizaItemDoPedido(request.getItems().getSobremesa()));
        BigDecimal totalSumarizado = todosOsItensDoPedidoSumarizado.stream().map(ProdutoPedido::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        Pedido pedido = pedidoRepository.saveAndFlush(pedidoMapper.toEntity(PedidoDTO
                .builder()
                .valor(totalSumarizado)
                .statusPagamentoDTO(StatusPagamentoDTO
                        .builder()
                        .id(StatusPagamentoEnum.PENDENTE.getId())
                        .nome(StatusPagamentoEnum.PENDENTE.getNome())
                        .build())
                .status(StatusPedidoDTO
                        .builder()
                        .id(StatusPedidoEnum.RECEBIDO.getId())
                        .nome(StatusPedidoEnum.RECEBIDO.getNome())
                        .build())
                .build()));

        return pedidoMapper.toDTO(pedido);
    }
}

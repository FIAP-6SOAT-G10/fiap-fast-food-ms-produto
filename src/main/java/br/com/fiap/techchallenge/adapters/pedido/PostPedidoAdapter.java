package br.com.fiap.techchallenge.adapters.pedido;

import br.com.fiap.techchallenge.domain.entities.*;
import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import br.com.fiap.techchallenge.domain.model.enums.StatusPagamentoEnum;
import br.com.fiap.techchallenge.domain.model.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.domain.model.mapper.ProdutoPedidoMapper;
import br.com.fiap.techchallenge.domain.model.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.*;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.infra.exception.PedidoException;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.repositories.ClienteRepository;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import br.com.fiap.techchallenge.infra.repositories.ProdutoPedidoRepository;
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
    private final ClienteMapper clienteMapper;
    private final ProdutoPedidoMapper produtoPedidoMapper;
    private final ProdutoRepository produtoRepository;
    private final ProdutoPedidoRepository produtoPedidoRepository;
    private final ClienteRepository clienteRepository;

    public PostPedidoAdapter(PedidoRepository pedidoRepository, PedidoMapper mapper, ClienteMapper clienteMapper, ProdutoPedidoMapper produtoPedidoMapper, ProdutoRepository produtoRepository, ProdutoPedidoRepository produtoPedidoRepository, ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = mapper;
        this.clienteMapper = clienteMapper;
        this.produtoPedidoMapper = produtoPedidoMapper;
        this.produtoRepository = produtoRepository;
        this.produtoPedidoRepository = produtoPedidoRepository;
        this.clienteRepository = clienteRepository;
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
        pedido.setStatus(new StatusPedido(StatusPedidoEnum.RECEBIDO.getStatus()));
        pedido.setStatusPagamento(new StatusPagamento(StatusPagamentoEnum.PAGO.getStatus()));

        return this.pedidoMapper.toDTO(pedidoRepository.saveAndFlush(pedido));
    }

    private List<ProdutoPedido> sumarizaItemDoPedido(List<ItemPedidoDTO> item) {
        List<ProdutoPedido> todosOsItensDoPedidoSumarizado = new ArrayList<>();
        Map<Long, List<ItemPedidoDTO>> itensDoPedido = item.stream().collect(Collectors.groupingBy(ItemPedidoDTO::getId));
        itensDoPedido.forEach((key, value) -> {
            Produto produto = produtoRepository.findById(key).orElseThrow(()-> new ProdutoException(ErrosEnum.PRODUTO_NAO_ENCONTRADO));
            ProdutoPedido produtoPedido = ProdutoPedido
                    .builder()
                    .produto(produto)
                    .build();
            produtoPedido.setQuantidade(BigInteger.ZERO);
            produtoPedido.setValorTotal(BigDecimal.ZERO);
            value.forEach(valueIt -> {
                produtoPedido.setQuantidade(produtoPedido.getQuantidade().add(BigInteger.valueOf(valueIt.getQuantidade())));
                produtoPedido.setValorTotal(produtoPedido.getValorTotal().add(produto.getPreco().multiply(new BigDecimal(valueIt.getQuantidade()))));
            });
            todosOsItensDoPedidoSumarizado.add(produtoPedido);
        });
        return todosOsItensDoPedidoSumarizado;
    }

    @Override
    public PedidoDTO criarPedido(PedidoRequestDTO request) {

        Cliente cliente = buscaCliente(request);

        List<ProdutoPedido> todosOsItensDoPedidoSumarizado = new ArrayList<>();
        todosOsItensDoPedidoSumarizado.addAll(sumarizaItemDoPedido(request.getItems().getLanches()));
        todosOsItensDoPedidoSumarizado.addAll(sumarizaItemDoPedido(request.getItems().getAcompanhamento()));
        todosOsItensDoPedidoSumarizado.addAll(sumarizaItemDoPedido(request.getItems().getBebida()));
        todosOsItensDoPedidoSumarizado.addAll(sumarizaItemDoPedido(request.getItems().getSobremesa()));
        BigDecimal totalSumarizado = todosOsItensDoPedidoSumarizado.stream().map(ProdutoPedido::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);


        Pedido pedido = pedidoRepository.saveAndFlush(pedidoMapper.toEntity(PedidoDTO
                .builder()
                .valor(totalSumarizado)
                .cliente(cliente == null ? null : clienteMapper.toDTO(cliente))
                .statusPagamento(StatusPagamentoDTO
                        .builder()
                        .id(StatusPagamentoEnum.PENDENTE.getId())
                        .nome(StatusPagamentoEnum.PENDENTE.getStatus())
                        .build())
                .status(StatusPedidoDTO
                        .builder()
                        .id(StatusPedidoEnum.RECEBIDO.getId())
                        .nome(StatusPedidoEnum.RECEBIDO.getStatus())
                        .build())
                .build()));
        todosOsItensDoPedidoSumarizado.parallelStream().forEach(itemDoPedido -> {
            itemDoPedido.setPedido(pedido);
            produtoPedidoRepository.saveAndFlush(itemDoPedido);
        });
        return this.pedidoMapper.toDTO(pedido);
    }

    private Cliente buscaCliente(PedidoRequestDTO request) {
        if(request.getCliente() == null || request.getCliente().isBlank() || request.getCliente().isEmpty()) {
            return null;
        }
        Optional<Cliente> cliente = clienteRepository.findByCpf(request.getCliente());
        if (cliente.isEmpty()){
            throw new ClienteException(ErrosEnum.CLIENTE_CPF_NAO_EXISTE);
        }
        return cliente.get();
    }
}

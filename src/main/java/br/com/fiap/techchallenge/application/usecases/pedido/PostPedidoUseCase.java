package br.com.fiap.techchallenge.application.usecases.pedido;

import br.com.fiap.techchallenge.application.gateways.IPedidoRepository;
import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.domain.entities.pagamento.PagamentoResponseDTO;
import br.com.fiap.techchallenge.domain.entities.pedido.Item;
import br.com.fiap.techchallenge.domain.entities.pedido.ItemPedido;
import br.com.fiap.techchallenge.domain.entities.pedido.Pedido;
import br.com.fiap.techchallenge.domain.entities.pedido.ProdutoPedido;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.application.usecases.pagamento.RealizarPagamentoUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class PostPedidoUseCase  {

    private final IPedidoRepository pedidoRepository;
    private final IProdutoRepository produtoRepository;
    private final RealizarPagamentoUseCase realizarPagamentoUseCase;

    public PostPedidoUseCase(IPedidoRepository pedidoRepository, IProdutoRepository produtoRepository, RealizarPagamentoUseCase realizarPagamentoUseCase) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.realizarPagamentoUseCase = realizarPagamentoUseCase;
    }

    public Pedido criarPedido(Pedido pedido) {

        List<ProdutoPedido> itens = totalizaItensDoPedido(pedido.getItems());
        BigDecimal subtotal = itens.stream().map(ProdutoPedido::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        /** REVISAR ESSA PARTE **/
        pedido.getProdutoPedidos().addAll(itens);
        pedido.getValor().add(subtotal);


//        pedido.setProdutoPedidos(itens);
//        pedido.setValor(subtotal);

       return pedidoRepository.criarPedido(pedido);
    }

    private List<ProdutoPedido> totalizaItensDoPedido(Item item) {
        Map<Long, List<ItemPedido>> items = Stream.of(
                        item.getLanches(),
                        item.getAcompanhamento(),
                        item.getBebida(),
                        item.getSobremesa())
                .reduce(this::unificarItens)
                .get()
                .stream()
                .collect(Collectors.groupingBy(ItemPedido::getId));

        return items.entrySet().stream().map(this::criarListaDeProdutosPedido)
                .reduce(this::unificarProdutos)
                .stream()
                .findAny()
                .orElse(Collections.emptyList());
    }

    private List<ItemPedido> unificarItens(List<ItemPedido> primeira, List<ItemPedido> segunda) {
        primeira.addAll(segunda);
        return primeira;
    }

    private List<ProdutoPedido> criarListaDeProdutosPedido(Map.Entry<Long, List<ItemPedido>> entry) {
        Long id = entry.getKey();
        List<ItemPedido> itens = entry.getValue();

        long quantidade = itens.stream().map(ItemPedido::getQuantidade).reduce(0L, Long::sum);
        Produto produto = produtoRepository.findById(id);

        ProdutoPedido produtoPedido = new ProdutoPedido(produto, produto.getPreco().multiply(BigDecimal.valueOf(quantidade)), BigInteger.valueOf(quantidade));

        return List.of(produtoPedido);
    }

    private List<ProdutoPedido> unificarProdutos(List<ProdutoPedido> primeira, List<ProdutoPedido> segunda) {
        List<ProdutoPedido> produtos = new ArrayList<>();
        produtos.addAll(primeira);
        produtos.addAll(segunda);

        return produtos;
    }

    public PagamentoResponseDTO realizarCheckout(Long id) {
        Pedido pedido = pedidoRepository.buscarPedidoPorId(id);
        return realizarPagamentoUseCase.efetuarPagamento(pedido);
    }
}

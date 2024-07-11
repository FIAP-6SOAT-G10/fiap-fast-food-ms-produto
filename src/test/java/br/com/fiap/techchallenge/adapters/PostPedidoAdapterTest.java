//package br.com.fiap.techchallenge.adapters;
//
//import br.com.fiap.techchallenge.naousar.adapters.pedido.PostPedidoAdapter;
//import br.com.fiap.techchallenge.infra.persistence.entities.Pedido;
//import br.com.fiap.techchallenge.infra.mapper.ProdutoPedidoMapper;
//import br.com.fiap.techchallenge.infra.mapper.cliente.ClienteMapper;
//import br.com.fiap.techchallenge.infra.mapper.pedido.PedidoMapper;
//import br.com.fiap.techchallenge.naousar.domain.valueobjects.PedidoDTO;
//import br.com.fiap.techchallenge.naousar.infra.exception.PedidoException;
//import br.com.fiap.techchallenge.infra.persistence.ClienteRepository;
//import br.com.fiap.techchallenge.infra.persistence.PedidoEntityRepository;
//import br.com.fiap.techchallenge.infra.persistence.ProdutoPedidoRepository;
//import br.com.fiap.techchallenge.infra.persistence.ProdutoEntityRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//public class PostPedidoAdapterTest {
//
//    @Mock
//    private PedidoEntityRepository pedidoRepository;
//
//    @Autowired
//    private PedidoMapper pedidoMapper;
//
//    @Autowired
//    private ClienteMapper clienteMapper;
//
//    @Autowired
//    private ProdutoPedidoMapper produtoPedidoMapper;
//
//    @Mock
//    private ProdutoEntityRepository produtoEntityRepository;
//
//    @Mock
//    private ProdutoPedidoRepository produtoPedidoRepository;
//
//    @Mock
//    private ClienteRepository clienteRepository;
//
//    @Test
//    public void shouldRetornarPedidoDtoWhenCheckoutIsOk() throws InterruptedException {
//        Pedido pedido = new Pedido();
//        when(pedidoRepository.findById(any())).thenReturn(Optional.of(pedido));
//        when(pedidoRepository.saveAndFlush(any())).thenReturn(pedido);
//        when(pedidoRepository.findById(any())).thenReturn(Optional.of(pedido));
//
//        PostPedidoAdapter postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoEntityRepository, produtoPedidoRepository, clienteRepository);
//        PedidoDTO result = postPedidoAdapter.realizarCheckout(1L);
//
//        assertNotNull(result);
//    }
//
//    @Test
//    public void mustThrowPedidoExceptionWhenPedidoNaoExiste() {
//        when(pedidoRepository.findById(any())).thenReturn(Optional.empty());
//        PostPedidoAdapter postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoEntityRepository, produtoPedidoRepository, clienteRepository);
//        assertThrows(PedidoException.class, () -> postPedidoAdapter.realizarCheckout(1L));
//    }
//}

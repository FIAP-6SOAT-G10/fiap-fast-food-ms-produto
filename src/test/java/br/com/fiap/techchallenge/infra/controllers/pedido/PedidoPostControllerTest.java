//package br.com.fiap.techchallenge.apis.pedido;
//
//import br.com.fiap.techchallenge.naousar.apis.PedidoController;
//import br.com.fiap.techchallenge.infra.persistence.entities.Cliente;
//import br.com.fiap.techchallenge.infra.persistence.entities.Pedido;
//import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
//import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoPedido;
//import br.com.fiap.techchallenge.infra.mapper.ProdutoPedidoMapper;
//import br.com.fiap.techchallenge.infra.mapper.cliente.ClienteMapper;
//import br.com.fiap.techchallenge.infra.mapper.pedido.PedidoMapper;
//import br.com.fiap.techchallenge.naousar.domain.valueobjects.ItemDTO;
//import br.com.fiap.techchallenge.naousar.domain.valueobjects.ItemPedidoDTO;
//import br.com.fiap.techchallenge.naousar.domain.valueobjects.PedidoRequestDTO;
//import br.com.fiap.techchallenge.naousar.domain.valueobjects.response.PedidoResponseDTO;
//import br.com.fiap.techchallenge.naousar.infra.exception.PedidoException;
//import br.com.fiap.techchallenge.infra.persistence.ClienteRepository;
//import br.com.fiap.techchallenge.infra.persistence.PedidoEntityRepository;
//import br.com.fiap.techchallenge.infra.persistence.ProdutoPedidoRepository;
//import br.com.fiap.techchallenge.infra.persistence.ProdutoEntityRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Random;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//public class PedidoPostControllerTest {
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
//    public void shouldReturnCreatedWhenCheckoutIsSuccessful() throws InterruptedException {
//
//        Pedido pedido = new Pedido();
//        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(pedido));
//        when(pedidoRepository.saveAndFlush(any())).thenReturn(pedido);
//
//        PedidoController pedidoController = new PedidoController(pedidoRepository, produtoEntityRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, clienteRepository);
//        ResponseEntity<Void> response = pedidoController.realizarCheckout(1L);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//    }
//
//    @Test
//    public void shouldReturnBadRequestWhenCheckoutFails() throws InterruptedException {
//
//        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.empty());
//        PedidoController pedidoController = new PedidoController(pedidoRepository, produtoEntityRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, clienteRepository);
//        assertThrows(PedidoException.class, () -> pedidoController.realizarCheckout(1L));
//    }
//
//    @Test
//    public void shouldCriarUmPedido() {
//
//        PedidoRequestDTO mock = buildRequest();
//        Optional<Cliente> cliente =  Optional.of(Cliente.builder().id(1l).cpf("123123123").build());
//        Optional<ProdutoEntity> produto = Optional.of(ProdutoEntity.builder().id(1l).preco(BigDecimal.TEN).build());
//        Pedido pedido = Pedido.builder().id(1l).valor(BigDecimal.TEN).build();
//        ProdutoPedido produtoPedido = new ProdutoPedido();
//
//        when(clienteRepository.findByCpf(anyString())).thenReturn(cliente);
//        when(produtoEntityRepository.findById(anyLong())).thenReturn(produto);
//        when(pedidoRepository.saveAndFlush(any())).thenReturn(pedido);
//        when(produtoPedidoRepository.saveAndFlush(any())).thenReturn(produtoPedido);
//
//        PedidoController controller = new PedidoController(pedidoRepository, produtoEntityRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, clienteRepository);
//        ResponseEntity<PedidoResponseDTO> responseEntity = controller.cadastrarPedido(mock);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }
//
//    public PedidoRequestDTO buildRequest() {
//        List<ItemPedidoDTO> lanches = new ArrayList<>();
//        lanches.add(buildItemPedido());
//        lanches.add(buildItemPedido());
//
//        List<ItemPedidoDTO> acompanhamento  = new ArrayList<>();
//        acompanhamento.add(buildItemPedido());
//        acompanhamento.add(buildItemPedido());
//
//        List<ItemPedidoDTO> bebida  = new ArrayList<>();
//        bebida.add(buildItemPedido());
//        bebida.add(buildItemPedido());
//
//        List<ItemPedidoDTO> sobremesa  = new ArrayList<>();
//        sobremesa.add(buildItemPedido());
//        sobremesa.add(buildItemPedido());
//        return PedidoRequestDTO
//                .builder()
//                .items( ItemDTO
//                        .builder()
//                        .lanches(lanches)
//                        .acompanhamento(acompanhamento)
//                        .bebida(bebida)
//                        .sobremesa(sobremesa)
//                        .build())
//                .cliente("12345678910")
//                .build();
//
//    }
//
//    public ItemPedidoDTO buildItemPedido() {
//        return ItemPedidoDTO
//                .builder()
//                .id(new Random().nextLong())
//                .quantidade(new Random().nextLong())
//                .build();
//    }
//
//}

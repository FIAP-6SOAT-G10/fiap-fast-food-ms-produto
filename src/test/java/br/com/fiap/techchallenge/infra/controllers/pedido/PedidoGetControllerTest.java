//package br.com.fiap.techchallenge.apis.pedido;
//
//import br.com.fiap.techchallenge.naousar.apis.PedidoController;
//import br.com.fiap.techchallenge.infra.persistence.entities.Pedido;
//import br.com.fiap.techchallenge.infra.persistence.entities.StatusPedido;
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
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class PedidoGetControllerTest {
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
//    @Autowired
//    private ProdutoEntityRepository produtoEntityRepository;
//
//    @Autowired
//    private ProdutoPedidoRepository produtoPedidoRepository;
//
//    @Autowired
//    private ClienteRepository clienteRepository;
//
//    @Test
//    void shouldListarPedidoPorIdReturnsPedidoDTOWhenIdExists() {
//        PedidoDTO pedidoDTO = new PedidoDTO();
//        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(new Pedido()));
//
//        PedidoController pedidoController = new PedidoController(pedidoRepository, produtoEntityRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
//        ResponseEntity<PedidoDTO> response = pedidoController.listarPedidoPorId(1L);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(pedidoDTO, response.getBody());
//    }
//
//    @Test
//    void mustListarPedidoPorIdReturnsNotFoundWhenIdDoesNotExist() {
//        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());
//
//        PedidoController pedidoController = new PedidoController(pedidoRepository, produtoEntityRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
//        ResponseEntity<PedidoDTO> response = pedidoController.listarPedidoPorId(1L);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//
//    @Test
//    void shouldReturnOkWhenListarTodosClientesAndClientesExist() {
//        when(pedidoRepository.findAll(Pageable.ofSize(2))).thenReturn(new PageImpl<>(List.of(new Pedido())));
//
//        PedidoController controller =  new PedidoController(pedidoRepository, produtoEntityRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
//        ResponseEntity<List<PedidoDTO>> response = controller.listarTodosPedidos(0, 2);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void mustListarTodosPedidosReturnsNotFoundWhenNoPedidosExist() {
//        Page<Pedido> page = new PageImpl<>(Collections.emptyList());
//        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);
//
//        PedidoController pedidoController =  new PedidoController(pedidoRepository, produtoEntityRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
//        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarTodosPedidos(0, 2);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//
//    @Test
//    void shouldListarPedidosComStatusRecebido() {
//        Pedido pedidoUm = new Pedido();
//        pedidoUm.setStatus(new StatusPedido("recebido"));
//
//        Pedido pedidoDois = new Pedido();
//        pedidoDois.setStatus(new StatusPedido("recebido"));
//
//        Page<Pedido> page = new PageImpl<>(Arrays.asList(pedidoUm, pedidoDois));
//        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);
//
//        PedidoController pedidoController =  new PedidoController(pedidoRepository, produtoEntityRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
//        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarPedidosPorStatus("recebido", 1, 25);
//
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//        assertEquals(2, response.getBody().size());
//    }
//
//    @Test
//    void shouldListarPedidosComStatusEmPreparacao() {
//        Pedido pedidoUm = new Pedido();
//        pedidoUm.setStatus(new StatusPedido("preparacao"));
//
//        Pedido pedidoDois = new Pedido();
//        pedidoDois.setStatus(new StatusPedido("preparacao"));
//
//        Page<Pedido> page = new PageImpl<>(Arrays.asList(pedidoUm, pedidoDois));
//        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);
//
//        PedidoController pedidoController =  new PedidoController(pedidoRepository, produtoEntityRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
//        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarPedidosPorStatus("preparacao", 1, 25);
//
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//        assertEquals(2, response.getBody().size());
//    }
//
//    @Test
//    void shouldListarPedidosComStatusPronto() {
//        Pedido pedidoUm = new Pedido();
//        pedidoUm.setStatus(new StatusPedido("pronto"));
//
//        Pedido pedidoDois = new Pedido();
//        pedidoDois.setStatus(new StatusPedido("pronto"));
//
//        Page<Pedido> page = new PageImpl<>(Arrays.asList(pedidoUm, pedidoDois));
//        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);
//
//        PedidoController pedidoController =  new PedidoController(pedidoRepository, produtoEntityRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
//        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarPedidosPorStatus("pronto", 1, 25);
//
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//        assertEquals(2, response.getBody().size());
//    }
//
//    @Test
//    void shouldListarPedidosComStatusFinalizado() {
//        Pedido pedidoUm = new Pedido();
//        pedidoUm.setStatus(new StatusPedido("finalizado"));
//
//        Pedido pedidoDois = new Pedido();
//        pedidoDois.setStatus(new StatusPedido("finalizado"));
//
//        Page<Pedido> page = new PageImpl<>(Arrays.asList(pedidoUm, pedidoDois));
//        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);
//
//        PedidoController pedidoController =  new PedidoController(pedidoRepository, produtoEntityRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
//        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarPedidosPorStatus("finalizado", 1, 25);
//
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//        assertEquals(2, response.getBody().size());
//    }
//
//    @Test
//    void shouldListarPedidosVazios() {
//        Page<Pedido> page = new PageImpl<>(Collections.emptyList());
//        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);
//
//        PedidoController pedidoController = new PedidoController(pedidoRepository, produtoEntityRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
//        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarPedidosPorStatus("finalizado", 1, 25);
//
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//        assertEquals(0, response.getBody().size());
//    }
//
//    @Test
//    void mustLancarPedidoExceptionQuandoInformarUmStatusInexistente() {
//        PedidoController pedidoController =  new PedidoController(pedidoRepository, produtoEntityRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
//
//        assertThrows(PedidoException.class, () -> pedidoController.listarPedidosPorStatus("reaberto", 1, 25));
//    }
//
//}

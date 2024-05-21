package br.com.fiap.techchallenge.apis.pedido;

import br.com.fiap.techchallenge.apis.PedidoController;
import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.entities.StatusPedido;
import br.com.fiap.techchallenge.domain.model.mapper.ProdutoPedidoMapper;
import br.com.fiap.techchallenge.domain.model.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.infra.exception.PedidoException;
import br.com.fiap.techchallenge.infra.repositories.ClienteRepository;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import br.com.fiap.techchallenge.infra.repositories.ProdutoPedidoRepository;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PedidoGetControllerTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private ProdutoPedidoMapper produtoPedidoMapper;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoPedidoRepository produtoPedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void shouldListarPedidoPorIdReturnsPedidoDTOWhenIdExists() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(new Pedido()));

        PedidoController pedidoController = new PedidoController(pedidoRepository, produtoRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
        ResponseEntity<PedidoDTO> response = pedidoController.listarPedidoPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidoDTO, response.getBody());
    }

    @Test
    void mustListarPedidoPorIdReturnsNotFoundWhenIdDoesNotExist() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        PedidoController pedidoController = new PedidoController(pedidoRepository, produtoRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
        ResponseEntity<PedidoDTO> response = pedidoController.listarPedidoPorId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldReturnOkWhenListarTodosClientesAndClientesExist() {
        when(pedidoRepository.findAll(Pageable.ofSize(2))).thenReturn(new PageImpl<>(List.of(new Pedido())));

        PedidoController controller =  new PedidoController(pedidoRepository, produtoRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
        ResponseEntity<List<PedidoDTO>> response = controller.listarTodosPedidos(0, 2);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void mustListarTodosPedidosReturnsNotFoundWhenNoPedidosExist() {
        Page<Pedido> page = new PageImpl<>(Collections.emptyList());
        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);

        PedidoController pedidoController =  new PedidoController(pedidoRepository, produtoRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarTodosPedidos(0, 2);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldListarPedidosComStatusRecebido() {
        Pedido pedidoUm = new Pedido();
        pedidoUm.setStatus(new StatusPedido("recebido"));

        Pedido pedidoDois = new Pedido();
        pedidoDois.setStatus(new StatusPedido("recebido"));

        Page<Pedido> page = new PageImpl<>(Arrays.asList(pedidoUm, pedidoDois));
        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);

        PedidoController pedidoController =  new PedidoController(pedidoRepository, produtoRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarPedidosPorStatus("recebido", 1, 25);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void shouldListarPedidosComStatusEmPreparacao() {
        Pedido pedidoUm = new Pedido();
        pedidoUm.setStatus(new StatusPedido("preparacao"));

        Pedido pedidoDois = new Pedido();
        pedidoDois.setStatus(new StatusPedido("preparacao"));

        Page<Pedido> page = new PageImpl<>(Arrays.asList(pedidoUm, pedidoDois));
        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);

        PedidoController pedidoController =  new PedidoController(pedidoRepository, produtoRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarPedidosPorStatus("preparacao", 1, 25);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void shouldListarPedidosComStatusPronto() {
        Pedido pedidoUm = new Pedido();
        pedidoUm.setStatus(new StatusPedido("pronto"));

        Pedido pedidoDois = new Pedido();
        pedidoDois.setStatus(new StatusPedido("pronto"));

        Page<Pedido> page = new PageImpl<>(Arrays.asList(pedidoUm, pedidoDois));
        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);

        PedidoController pedidoController =  new PedidoController(pedidoRepository, produtoRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarPedidosPorStatus("pronto", 1, 25);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void shouldListarPedidosComStatusFinalizado() {
        Pedido pedidoUm = new Pedido();
        pedidoUm.setStatus(new StatusPedido("finalizado"));

        Pedido pedidoDois = new Pedido();
        pedidoDois.setStatus(new StatusPedido("finalizado"));

        Page<Pedido> page = new PageImpl<>(Arrays.asList(pedidoUm, pedidoDois));
        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);

        PedidoController pedidoController =  new PedidoController(pedidoRepository, produtoRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarPedidosPorStatus("finalizado", 1, 25);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void shouldListarPedidosVazios() {
        Page<Pedido> page = new PageImpl<>(Collections.emptyList());
        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);

        PedidoController pedidoController = new PedidoController(pedidoRepository, produtoRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);
        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarPedidosPorStatus("finalizado", 1, 25);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void mustLancarPedidoExceptionQuandoInformarUmStatusInexistente() {
        PedidoController pedidoController =  new PedidoController(pedidoRepository, produtoRepository, produtoPedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper,clienteRepository);

        assertThrows(PedidoException.class, () -> pedidoController.listarPedidosPorStatus("reaberto", 1, 25));
    }

}
package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.pedido.GetPedidoAdapter;
import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.entities.StatusPagamento;
import br.com.fiap.techchallenge.domain.entities.StatusPedido;
import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.domain.usecases.pedido.GetPedidoUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GetPedidoControllerTest {

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private GetPedidoAdapter getPedidoAdapter;
    @Mock
    private GetPedidoUseCase getPedidoUseCase;

    @Mock
    private PedidoMapper pedidoMapper;

    @BeforeEach
    public void setup() {
        GetPedidoAdapter getPedidoAdapter = new GetPedidoAdapter(pedidoRepository, pedidoMapper);
        GetPedidoUseCase getPedidoUseCase = new GetPedidoUseCase(getPedidoAdapter);
    }

    @Test
    public void shouldListarPedidoPorIdReturnsPedidoDTOWhenIdExists() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(new Pedido()));
        when(pedidoMapper.toDTO(any(Pedido.class))).thenReturn(pedidoDTO);

        ResponseEntity<PedidoDTO> response = pedidoController.listarPedidoPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidoDTO, response.getBody());
    }

    @Test
    public void mustListarPedidoPorIdReturnsNotFoundWhenIdDoesNotExist() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<PedidoDTO> response = pedidoController.listarPedidoPorId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldReturnOkWhenListarTodosClientesAndClientesExist() {
        when(pedidoRepository.findAll(Pageable.ofSize(2))).thenReturn(new PageImpl<>(List.of(new Pedido())));
        when(pedidoMapper.fromListEntityToListDTO(anyList())).thenReturn(Arrays.asList(new PedidoDTO(), new PedidoDTO()));

        PedidoController controller = new PedidoController(pedidoRepository, pedidoMapper);
        ResponseEntity<List<PedidoDTO>> response = controller.listarTodosPedidos(0, 2);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void mustListarTodosPedidosReturnsNotFoundWhenNoPedidosExist() {
        Page<Pedido> page = new PageImpl<>(Arrays.asList(new Pedido(), new Pedido()));
        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(pedidoMapper.toDTO(any(Pedido.class))).thenReturn(null);

        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarTodosPedidos(0, 2);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.cliente.PutClienteAdapter;
import br.com.fiap.techchallenge.adapters.pedido.GetPedidoAdapter;
import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.domain.usecases.pedido.GetPedidoUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        MockitoAnnotations.openMocks(this);
        GetPedidoAdapter getPedidoAdapter = new GetPedidoAdapter(pedidoRepository, pedidoMapper);
        GetPedidoUseCase getPedidoUseCase = new GetPedidoUseCase(getPedidoAdapter);
    }

    @Test
    public void listarPedidoPorId_ReturnsPedidoDTO_WhenIdExists() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(new Pedido()));
        when(pedidoMapper.toDTO(any(Pedido.class))).thenReturn(pedidoDTO);

        ResponseEntity<PedidoDTO> response = pedidoController.listarPedidoPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidoDTO, response.getBody());
    }

    @Test
    public void listarPedidoPorId_ReturnsNotFound_WhenIdDoesNotExist() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<PedidoDTO> response = pedidoController.listarPedidoPorId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void listarTodosPedidos_ReturnsListOfPedidoDTO_WhenPedidosExist() {
        // Arrange
        PedidoDTO pedidoDTO1 = new PedidoDTO();
        PedidoDTO pedidoDTO2 = new PedidoDTO();
        List<PedidoDTO> pedidosDTO = Arrays.asList(pedidoDTO1, pedidoDTO2);
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();
        List<Pedido> pedidos = Arrays.asList(pedido1, pedido2);
        Page<Pedido> page = new PageImpl<>(pedidos);

        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(pedidoMapper.toDTO(pedido1)).thenReturn(pedidoDTO1);
        when(pedidoMapper.toDTO(pedido2)).thenReturn(pedidoDTO2);

        GetPedidoAdapter getPedidoAdapter = new GetPedidoAdapter(pedidoRepository, pedidoMapper);
        GetPedidoUseCase getPedidoUseCase = new GetPedidoUseCase(getPedidoAdapter);
        when(getPedidoUseCase.listarPedidos(0, 2)).thenReturn(pedidosDTO);

        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarTodosPedidos(0, 2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidosDTO.size(), response.getBody().size());
    }


    @Test
    public void listarTodosPedidos_ReturnsNotFound_WhenNoPedidosExist() {
        Page<Pedido> page = new PageImpl<>(Arrays.asList(new Pedido(), new Pedido()));
        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(pedidoMapper.toDTO(any(Pedido.class))).thenReturn(null);

        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarTodosPedidos(0, 2);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
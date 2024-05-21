package br.com.fiap.techchallenge.apis.pedido;

import br.com.fiap.techchallenge.adapters.pedido.PostPedidoAdapter;
import br.com.fiap.techchallenge.apis.PedidoController;
import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.entities.ProdutoPedido;
import br.com.fiap.techchallenge.domain.model.mapper.ProdutoPedidoMapper;
import br.com.fiap.techchallenge.domain.model.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.domain.usecases.pedido.PostPedidoUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoRequestDTO;
import br.com.fiap.techchallenge.domain.valueobjects.response.PedidoResponseDTO;
import br.com.fiap.techchallenge.infra.exception.PedidoException;
import br.com.fiap.techchallenge.infra.repositories.ClienteRepository;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import br.com.fiap.techchallenge.infra.repositories.ProdutoPedidoRepository;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PedidoPostControllerTest {

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PostPedidoUseCase postPedidoUseCase;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
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

    @BeforeEach
    public void setup() {
        PostPedidoAdapter postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository,clienteRepository);
        PostPedidoUseCase postPedidoUseCase = new PostPedidoUseCase(postPedidoAdapter);
    }

    @Test
    public void shouldReturnCreatedWhenCheckoutIsSuccessful() throws InterruptedException {

        Pedido pedido = new Pedido();
        PedidoDTO pedidoDTO = new PedidoDTO();
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoMapper.toDTO(any(Pedido.class))).thenReturn(pedidoDTO);
        when(pedidoMapper.toEntity(any(PedidoDTO.class))).thenReturn(pedido);
        when(pedidoRepository.saveAndFlush(any(Pedido.class))).thenReturn(pedido);

        when(postPedidoUseCase.realizarCheckout(1L)).thenReturn(new PedidoDTO());

        ResponseEntity<Void> response = pedidoController.realizarCheckout(1L);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestWhenCheckoutFails() throws InterruptedException {
        when(postPedidoUseCase.realizarCheckout(1L)).thenReturn(null);

        assertThrows(PedidoException.class, () -> pedidoController.realizarCheckout(1L));
    }

//    @Test
//    public void deveCriarUmPedido() {
//        PedidoRequestDTO mock = Mockito.mock(PedidoRequestDTO.class);
//
//        Cliente cliente = new Cliente();
//        Produto produto = new Produto();
//        PedidoDTO pedidoDTO = Mockito.mock(PedidoDTO.class);
//        Pedido pedido = Mockito.mock(Pedido.class);
//        ProdutoPedido produtoPedido = Mockito.mock(ProdutoPedido.class);
//        PedidoResponseDTO pedidoResponseDTO = Mockito.mock(PedidoResponseDTO.class);
//
//        Mockito.when(clienteRepository.findByCpf(mock.getCliente())).thenReturn(Optional.of(cliente));
//        Mockito.when(produtoRepository.findById(any())).thenReturn(Optional.of(produto));
//        Mockito.when(pedidoRepository.saveAndFlush(pedido)).thenReturn(pedido);
//        Mockito.when(pedidoRepository.saveAndFlush(pedido)).thenReturn(pedido);
//        Mockito.when(produtoPedidoRepository.saveAndFlush(produtoPedido)).thenReturn(produtoPedido);
//
//        ResponseEntity<PedidoResponseDTO> response = pedidoController.cadastrarPedido(mock)
//        ;
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
 //   }
}
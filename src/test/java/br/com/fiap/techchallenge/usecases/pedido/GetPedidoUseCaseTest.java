//package br.com.fiap.techchallenge.usecases.pedido;
//
//import br.com.fiap.techchallenge.naousar.adapters.pedido.GetPedidoAdapter;
//import br.com.fiap.techchallenge.infra.persistence.entities.Cliente;
//import br.com.fiap.techchallenge.infra.persistence.entities.Pedido;
//import br.com.fiap.techchallenge.infra.persistence.entities.StatusPagamento;
//import br.com.fiap.techchallenge.infra.persistence.entities.StatusPedido;
//import br.com.fiap.techchallenge.infra.mapper.pedido.PedidoMapper;
//import br.com.fiap.techchallenge.naousar.domain.usecases.pedido.GetPedidoUseCase;
//import br.com.fiap.techchallenge.naousar.domain.valueobjects.PedidoDTO;
//import br.com.fiap.techchallenge.infra.persistence.PedidoRepository;
//import br.com.fiap.techchallenge.naousar.ports.pedido.GetPedidoOutboundPort;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//public class GetPedidoUseCaseTest {
//
//    @Mock
//    private PedidoRepository pedidoRepository;
//
//    @Autowired
//    private PedidoMapper pedidoMapper;
//
//    private GetPedidoOutboundPort port;
//
//    private GetPedidoUseCase getPedidoUseCase;
//
//    @BeforeEach
//    void setUp() {
//        port = new GetPedidoAdapter(pedidoRepository, pedidoMapper);
//        getPedidoUseCase = new GetPedidoUseCase(port);
//    }
//
//    @Test
//    void itShouldRetornarNenhumPedido(){
//        Long id = 15L;
//        when(pedidoRepository.findById(id)).thenReturn(null);
//
//        PedidoDTO pedido = getPedidoUseCase.buscarPedidoPorId(id);
//        assertNull(pedido);
//    }
//
//    @Test
//    void itShouldRetornarPedido(){
//        Long id = 15L;
//        Pedido pedidoMock = new Pedido(
//                id,
//                new Cliente(1L, "123", "Nome", "email@email.com"),
//                new StatusPedido(1L, "Em Espera"),
//                new BigDecimal("10.5"),
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                new StatusPagamento(1L, "Aprovado"),
//                new ArrayList<>()
//        );
//        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedidoMock));
//
//        PedidoDTO pedidoDTO = getPedidoUseCase.buscarPedidoPorId(id);
//        assertEquals(pedidoMock.getId(), pedidoDTO.getId());
////        assertEquals(pedidoMock.getCliente().getId(), pedidoDTO.getCliente().getId());
//    }
//
//    @Test
//    void shouldReturnEmptyListWhenNoPedidosExist() {
//        when(pedidoRepository.findAll(any(PageRequest.class))).thenReturn(Page.empty());
//
//        List<PedidoDTO> result = getPedidoUseCase.listarPedidos(0, 2);
//
//        assertTrue(result.isEmpty());
//    }
//
//}

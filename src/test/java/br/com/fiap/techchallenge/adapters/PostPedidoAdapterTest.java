package br.com.fiap.techchallenge.adapters;

import br.com.fiap.techchallenge.adapters.pedido.PostPedidoAdapter;
import br.com.fiap.techchallenge.apis.PedidoController;
import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.entities.ProdutoPedido;
import br.com.fiap.techchallenge.domain.model.mapper.ProdutoPedidoMapper;
import br.com.fiap.techchallenge.domain.model.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ItemDTO;
import br.com.fiap.techchallenge.domain.valueobjects.ItemPedidoDTO;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoDTO;
import br.com.fiap.techchallenge.domain.valueobjects.PedidoRequestDTO;
import br.com.fiap.techchallenge.domain.valueobjects.response.PedidoResponseDTO;
import br.com.fiap.techchallenge.infra.exception.PedidoException;
import br.com.fiap.techchallenge.infra.repositories.ClienteRepository;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import br.com.fiap.techchallenge.infra.repositories.ProdutoPedidoRepository;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostPedidoAdapterTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private ProdutoPedidoMapper produtoPedidoMapper;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoPedidoRepository produtoPedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    public void shouldRetornarPedidoDtoWhenCheckoutIsOk() throws InterruptedException {
        Pedido pedido = new Pedido();
        when(pedidoRepository.findById(any())).thenReturn(Optional.of(pedido));
        when(pedidoRepository.saveAndFlush(any())).thenReturn(pedido);
        when(pedidoRepository.findById(any())).thenReturn(Optional.of(pedido));

        PostPedidoAdapter postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        PedidoDTO result = postPedidoAdapter.realizarCheckout(1L);

        assertNotNull(result);
    }

    @Test
    public void mustThrowPedidoExceptionWhenPedidoNaoExiste() {
        when(pedidoRepository.findById(any())).thenReturn(Optional.empty());
        PostPedidoAdapter postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        assertThrows(PedidoException.class, () -> postPedidoAdapter.realizarCheckout(1L));
    }
}
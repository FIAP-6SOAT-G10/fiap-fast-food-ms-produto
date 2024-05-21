package br.com.fiap.techchallenge.usecases.pedido;

import br.com.fiap.techchallenge.adapters.pedido.PostPedidoAdapter;
import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.entities.ProdutoPedido;
import br.com.fiap.techchallenge.domain.model.mapper.ProdutoPedidoMapper;
import br.com.fiap.techchallenge.domain.model.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.domain.model.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.domain.usecases.pedido.PostPedidoUseCase;
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
import br.com.fiap.techchallenge.ports.cliente.PostPedidoInboundPort;
import br.com.fiap.techchallenge.ports.cliente.PostPedidoOutboundPort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostPedidoUseCaseTest {

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
    public void shouldReturnPedidoDtoWhenCheckoutIsOk() throws InterruptedException {

        when(pedidoRepository.findById(any())).thenReturn(Optional.of(new Pedido()));
        when(pedidoRepository.saveAndFlush(any())).thenReturn(new Pedido());
        PostPedidoOutboundPort postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        PostPedidoInboundPort postPedidoUseCase = new PostPedidoUseCase(postPedidoAdapter);
        assertNotNull(postPedidoUseCase.realizarCheckout(1L));
    }

    @Test
    public void shouldRetornarNullWhenCheckoutFalhar() throws InterruptedException {

        when(pedidoRepository.findById(any())).thenReturn(Optional.empty());

        PostPedidoOutboundPort postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        PostPedidoInboundPort postPedidoUseCase = new PostPedidoUseCase(postPedidoAdapter);
        assertThrows(PedidoException.class, ()-> postPedidoUseCase.realizarCheckout(1L));
    }

    @Test
    public void shouldCriarUmPedido() {

        PedidoRequestDTO mock = buildRequest();
        Optional<Cliente> cliente =  Optional.of(Cliente.builder().id(1l).cpf("123123123").build());
        Optional<Produto> produto = Optional.of(Produto.builder().id(1l).preco(BigDecimal.TEN).build());
        Pedido pedido = Pedido.builder().id(1l).valor(BigDecimal.TEN).build();
        ProdutoPedido produtoPedido = new ProdutoPedido();

        when(clienteRepository.findByCpf(anyString())).thenReturn(cliente);
        when(produtoRepository.findById(anyLong())).thenReturn(produto);
        when(pedidoRepository.saveAndFlush(any())).thenReturn(pedido);
        when(produtoPedidoRepository.saveAndFlush(any())).thenReturn(produtoPedido);

        PostPedidoOutboundPort postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        PostPedidoInboundPort postPedidoUseCase = new PostPedidoUseCase(postPedidoAdapter);

        PedidoResponseDTO pedidoResponseDTO = postPedidoUseCase.criarPedido(mock);
        assertNotNull(pedidoResponseDTO);
    }

    @Test
    public void shouldCriarUmPedidoSemIdentificarUmCliente() {

        PedidoRequestDTO mock = buildRequest();
        mock.setCliente(null);
        Optional<Produto> produto = Optional.of(Produto.builder().id(1l).preco(BigDecimal.TEN).build());
        Pedido pedido = Pedido.builder().id(1l).valor(BigDecimal.TEN).build();
        ProdutoPedido produtoPedido = new ProdutoPedido();

        when(produtoRepository.findById(anyLong())).thenReturn(produto);
        when(pedidoRepository.saveAndFlush(any())).thenReturn(pedido);
        when(produtoPedidoRepository.saveAndFlush(any())).thenReturn(produtoPedido);

        PostPedidoOutboundPort postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        PostPedidoInboundPort postPedidoUseCase = new PostPedidoUseCase(postPedidoAdapter);

        PedidoResponseDTO pedidoResponseDTO = postPedidoUseCase.criarPedido(mock);
        assertNotNull(pedidoResponseDTO);
    }

    @Test
    public void shouldCriarUmPedidoApenasComLancheEUsuarioIdentificado() {

        PedidoRequestDTO mock = buildRequest();
        mock.getItems().setAcompanhamento(Collections.emptyList());
        mock.getItems().setBebida(Collections.emptyList());
        mock.getItems().setSobremesa(Collections.emptyList());

        Optional<Cliente> cliente =  Optional.of(Cliente.builder().id(1l).cpf("123123123").build());
        Optional<Produto> produto = Optional.of(Produto.builder().id(1l).preco(BigDecimal.TEN).build());
        Pedido pedido = Pedido.builder().id(1l).valor(BigDecimal.TEN).build();
        ProdutoPedido produtoPedido = new ProdutoPedido();

        when(clienteRepository.findByCpf(anyString())).thenReturn(cliente);
        when(produtoRepository.findById(anyLong())).thenReturn(produto);
        when(pedidoRepository.saveAndFlush(any())).thenReturn(pedido);
        when(produtoPedidoRepository.saveAndFlush(any())).thenReturn(produtoPedido);

        PostPedidoOutboundPort postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        PostPedidoInboundPort postPedidoUseCase = new PostPedidoUseCase(postPedidoAdapter);

        PedidoResponseDTO pedidoResponseDTO = postPedidoUseCase.criarPedido(mock);
        assertNotNull(pedidoResponseDTO);
    }

    @Test
    public void shouldCriarUmPedidoApenasComLancheEUsuarioNaoIdentificado() {

        PedidoRequestDTO mock = buildRequest();
        mock.setCliente(null);
        mock.getItems().setAcompanhamento(Collections.emptyList());
        mock.getItems().setBebida(Collections.emptyList());
        mock.getItems().setSobremesa(Collections.emptyList());

        Optional<Produto> produto = Optional.of(Produto.builder().id(1l).preco(BigDecimal.TEN).build());
        Pedido pedido = Pedido.builder().id(1l).valor(BigDecimal.TEN).build();
        ProdutoPedido produtoPedido = new ProdutoPedido();

        when(produtoRepository.findById(anyLong())).thenReturn(produto);
        when(pedidoRepository.saveAndFlush(any())).thenReturn(pedido);
        when(produtoPedidoRepository.saveAndFlush(any())).thenReturn(produtoPedido);

        PostPedidoOutboundPort postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        PostPedidoInboundPort postPedidoUseCase = new PostPedidoUseCase(postPedidoAdapter);

        PedidoResponseDTO pedidoResponseDTO = postPedidoUseCase.criarPedido(mock);
        assertNotNull(pedidoResponseDTO);
    }

    @Test
    public void shouldCriarUmPedidoApenasComAcompanhamentoEUsuarioIdentificado() {

        PedidoRequestDTO mock = buildRequest();
        mock.getItems().setLanches(Collections.emptyList());
        mock.getItems().setBebida(Collections.emptyList());
        mock.getItems().setSobremesa(Collections.emptyList());

        Optional<Cliente> cliente =  Optional.of(Cliente.builder().id(1l).cpf("123123123").build());
        Optional<Produto> produto = Optional.of(Produto.builder().id(1l).preco(BigDecimal.TEN).build());
        Pedido pedido = Pedido.builder().id(1l).valor(BigDecimal.TEN).build();
        ProdutoPedido produtoPedido = new ProdutoPedido();

        when(clienteRepository.findByCpf(anyString())).thenReturn(cliente);
        when(produtoRepository.findById(anyLong())).thenReturn(produto);
        when(pedidoRepository.saveAndFlush(any())).thenReturn(pedido);
        when(produtoPedidoRepository.saveAndFlush(any())).thenReturn(produtoPedido);

        PostPedidoOutboundPort postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        PostPedidoInboundPort postPedidoUseCase = new PostPedidoUseCase(postPedidoAdapter);

        PedidoResponseDTO pedidoResponseDTO = postPedidoUseCase.criarPedido(mock);
        assertNotNull(pedidoResponseDTO);
    }

    @Test
    public void shouldCriarUmPedidoApenasComAcompanhamentoEUsuarioNaoIdentificado() {

        PedidoRequestDTO mock = buildRequest();
        mock.setCliente(null);
        mock.getItems().setLanches(Collections.emptyList());
        mock.getItems().setBebida(Collections.emptyList());
        mock.getItems().setSobremesa(Collections.emptyList());

        Optional<Produto> produto = Optional.of(Produto.builder().id(1l).preco(BigDecimal.TEN).build());
        Pedido pedido = Pedido.builder().id(1l).valor(BigDecimal.TEN).build();
        ProdutoPedido produtoPedido = new ProdutoPedido();

        when(produtoRepository.findById(anyLong())).thenReturn(produto);
        when(pedidoRepository.saveAndFlush(any())).thenReturn(pedido);
        when(produtoPedidoRepository.saveAndFlush(any())).thenReturn(produtoPedido);

        PostPedidoOutboundPort postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        PostPedidoInboundPort postPedidoUseCase = new PostPedidoUseCase(postPedidoAdapter);

        PedidoResponseDTO pedidoResponseDTO = postPedidoUseCase.criarPedido(mock);
        assertNotNull(pedidoResponseDTO);
    }

    @Test
    public void shouldCriarUmPedidoApenasComBebidaEUsuarioIdentificado() {

        PedidoRequestDTO mock = buildRequest();
        mock.getItems().setAcompanhamento(Collections.emptyList());
        mock.getItems().setLanches(Collections.emptyList());
        mock.getItems().setSobremesa(Collections.emptyList());

        Optional<Cliente> cliente =  Optional.of(Cliente.builder().id(1l).cpf("123123123").build());
        Optional<Produto> produto = Optional.of(Produto.builder().id(1l).preco(BigDecimal.TEN).build());
        Pedido pedido = Pedido.builder().id(1l).valor(BigDecimal.TEN).build();
        ProdutoPedido produtoPedido = new ProdutoPedido();

        when(clienteRepository.findByCpf(anyString())).thenReturn(cliente);
        when(produtoRepository.findById(anyLong())).thenReturn(produto);
        when(pedidoRepository.saveAndFlush(any())).thenReturn(pedido);
        when(produtoPedidoRepository.saveAndFlush(any())).thenReturn(produtoPedido);

        PostPedidoOutboundPort postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        PostPedidoInboundPort postPedidoUseCase = new PostPedidoUseCase(postPedidoAdapter);

        PedidoResponseDTO pedidoResponseDTO = postPedidoUseCase.criarPedido(mock);
        assertNotNull(pedidoResponseDTO);
    }

    @Test
    public void shouldCriarUmPedidoApenasComBebidaEUsuarioNaoIdentificado() {

        PedidoRequestDTO mock = buildRequest();
        mock.setCliente(null);
        mock.getItems().setAcompanhamento(Collections.emptyList());
        mock.getItems().setLanches(Collections.emptyList());
        mock.getItems().setSobremesa(Collections.emptyList());

        Optional<Produto> produto = Optional.of(Produto.builder().id(1l).preco(BigDecimal.TEN).build());
        Pedido pedido = Pedido.builder().id(1l).valor(BigDecimal.TEN).build();
        ProdutoPedido produtoPedido = new ProdutoPedido();

        when(produtoRepository.findById(anyLong())).thenReturn(produto);
        when(pedidoRepository.saveAndFlush(any())).thenReturn(pedido);
        when(produtoPedidoRepository.saveAndFlush(any())).thenReturn(produtoPedido);

        PostPedidoOutboundPort postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        PostPedidoInboundPort postPedidoUseCase = new PostPedidoUseCase(postPedidoAdapter);

        PedidoResponseDTO pedidoResponseDTO = postPedidoUseCase.criarPedido(mock);
        assertNotNull(pedidoResponseDTO);
    }

    @Test
    public void shouldCriarUmPedidoApenasComSobremesaEUsuarioIdentificado() {

        PedidoRequestDTO mock = buildRequest();
        mock.getItems().setAcompanhamento(Collections.emptyList());
        mock.getItems().setBebida(Collections.emptyList());
        mock.getItems().setLanches(Collections.emptyList());

        Optional<Cliente> cliente =  Optional.of(Cliente.builder().id(1l).cpf("123123123").build());
        Optional<Produto> produto = Optional.of(Produto.builder().id(1l).preco(BigDecimal.TEN).build());
        Pedido pedido = Pedido.builder().id(1l).valor(BigDecimal.TEN).build();
        ProdutoPedido produtoPedido = new ProdutoPedido();

        when(clienteRepository.findByCpf(anyString())).thenReturn(cliente);
        when(produtoRepository.findById(anyLong())).thenReturn(produto);
        when(pedidoRepository.saveAndFlush(any())).thenReturn(pedido);
        when(produtoPedidoRepository.saveAndFlush(any())).thenReturn(produtoPedido);

        PostPedidoOutboundPort postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        PostPedidoInboundPort postPedidoUseCase = new PostPedidoUseCase(postPedidoAdapter);

        PedidoResponseDTO pedidoResponseDTO = postPedidoUseCase.criarPedido(mock);
        assertNotNull(pedidoResponseDTO);
    }

    @Test
    public void shouldCriarUmPedidoApenasComSobremesaEUsuarioNaoIdentificado() {

        PedidoRequestDTO mock = buildRequest();
        mock.setCliente(null);
        mock.getItems().setAcompanhamento(Collections.emptyList());
        mock.getItems().setBebida(Collections.emptyList());
        mock.getItems().setLanches(Collections.emptyList());

        Optional<Produto> produto = Optional.of(Produto.builder().id(1l).preco(BigDecimal.TEN).build());
        Pedido pedido = Pedido.builder().id(1l).valor(BigDecimal.TEN).build();
        ProdutoPedido produtoPedido = new ProdutoPedido();

        when(produtoRepository.findById(anyLong())).thenReturn(produto);
        when(pedidoRepository.saveAndFlush(any())).thenReturn(pedido);
        when(produtoPedidoRepository.saveAndFlush(any())).thenReturn(produtoPedido);

        PostPedidoOutboundPort postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        PostPedidoInboundPort postPedidoUseCase = new PostPedidoUseCase(postPedidoAdapter);

        PedidoResponseDTO pedidoResponseDTO = postPedidoUseCase.criarPedido(mock);
        assertNotNull(pedidoResponseDTO);
    }

    @Test
    public void mustLancarPedidoExceptionQuandoCriarPedidoSemItensComUsuarioIdentificado() {

        PedidoRequestDTO mock = buildRequest();
        mock.getItems().setAcompanhamento(Collections.emptyList());
        mock.getItems().setBebida(Collections.emptyList());
        mock.getItems().setLanches(Collections.emptyList());
        mock.getItems().setSobremesa(Collections.emptyList());

        Optional<Cliente> cliente =  Optional.of(Cliente.builder().id(1l).cpf("123123123").build());
        Optional<Produto> produto = Optional.of(Produto.builder().id(1l).preco(BigDecimal.TEN).build());
        Pedido pedido = Pedido.builder().id(1l).valor(BigDecimal.TEN).build();
        ProdutoPedido produtoPedido = new ProdutoPedido();

        when(clienteRepository.findByCpf(anyString())).thenReturn(cliente);
        when(produtoRepository.findById(anyLong())).thenReturn(produto);
        when(pedidoRepository.saveAndFlush(any())).thenReturn(pedido);
        when(produtoPedidoRepository.saveAndFlush(any())).thenReturn(produtoPedido);

        PostPedidoOutboundPort postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        PostPedidoInboundPort postPedidoUseCase = new PostPedidoUseCase(postPedidoAdapter);

        assertThrows(PedidoException.class,() -> postPedidoUseCase.criarPedido(mock));
    }

    @Test
    public void mustLancarPedidoExceptionQuandoCriarPedidoSemItensSemUsuarioIdentificado() {

        PedidoRequestDTO mock = buildRequest();
        mock.setCliente(null);
        mock.getItems().setAcompanhamento(Collections.emptyList());
        mock.getItems().setBebida(Collections.emptyList());
        mock.getItems().setLanches(Collections.emptyList());
        mock.getItems().setSobremesa(Collections.emptyList());

        Optional<Produto> produto = Optional.of(Produto.builder().id(1l).preco(BigDecimal.TEN).build());
        Pedido pedido = Pedido.builder().id(1l).valor(BigDecimal.TEN).build();
        ProdutoPedido produtoPedido = new ProdutoPedido();

        when(produtoRepository.findById(anyLong())).thenReturn(produto);
        when(pedidoRepository.saveAndFlush(any())).thenReturn(pedido);
        when(produtoPedidoRepository.saveAndFlush(any())).thenReturn(produtoPedido);

        PostPedidoOutboundPort postPedidoAdapter = new PostPedidoAdapter(pedidoRepository, pedidoMapper, clienteMapper, produtoPedidoMapper, produtoRepository, produtoPedidoRepository, clienteRepository);
        PostPedidoInboundPort postPedidoUseCase = new PostPedidoUseCase(postPedidoAdapter);

        assertThrows(PedidoException.class,() -> postPedidoUseCase.criarPedido(mock));
    }

    public PedidoRequestDTO buildRequest() {
        List<ItemPedidoDTO> lanches = new ArrayList<>();
        lanches.add(buildItemPedido());
        lanches.add(buildItemPedido());

        List<ItemPedidoDTO> acompanhamento  = new ArrayList<>();
        acompanhamento.add(buildItemPedido());
        acompanhamento.add(buildItemPedido());

        List<ItemPedidoDTO> bebida  = new ArrayList<>();
        bebida.add(buildItemPedido());
        bebida.add(buildItemPedido());

        List<ItemPedidoDTO> sobremesa  = new ArrayList<>();
        sobremesa.add(buildItemPedido());
        sobremesa.add(buildItemPedido());
        return PedidoRequestDTO
                .builder()
                .items( ItemDTO
                        .builder()
                        .lanches(lanches)
                        .acompanhamento(acompanhamento)
                        .bebida(bebida)
                        .sobremesa(sobremesa)
                        .build())
                .cliente("12345678910")
                .build();

    }

    public ItemPedidoDTO buildItemPedido() {
        return ItemPedidoDTO
                .builder()
                .id(new Random().nextLong())
                .quantidade(new Random().nextLong())
                .build();
    }
}
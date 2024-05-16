package br.com.fiap.techchallenge.usecases.produto;

import br.com.fiap.techchallenge.adapters.produto.GetProdutoAdapter;
import br.com.fiap.techchallenge.apis.ProdutoController;
import br.com.fiap.techchallenge.domain.entities.Categoria;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.domain.usecases.produto.GetProdutoUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import br.com.fiap.techchallenge.ports.produto.GetProdutoInboundPort;
import br.com.fiap.techchallenge.ports.produto.GetProdutoOutboundPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class GetProdutosUseCaseTest {

    @Mock
    private ProdutoRepository repository;

    @Autowired
    private ProdutoMapper produtoMapper;

    private ProdutoController produtoController;

    private GetProdutoInboundPort getProdutosUseCase;

    @BeforeEach
    public void setUp() {
        GetProdutoOutboundPort getProdutoOutboundPort = new GetProdutoAdapter(repository, produtoMapper);
        getProdutosUseCase = new GetProdutoUseCase(getProdutoOutboundPort);
    }

    @Test
    void itShouldListarTodos10PrimeirosProdutos() {
        int page = 0;
        int size = 10;

        when(repository.findAll(PageRequest.of(page, size))).thenReturn(getMockPageProduto());

        List<ProdutoDTO> produtos = getProdutosUseCase.listarProdutos(page, size, null, null, null);
        assertEquals(10, produtos.size());

    }

    @Test
    void itShouldRetornarSomenteUmProduto() {
        int page = 0;
        int size = 10;

        when(repository.findByNomeOrDescricaoOrPreco("Heimno", null, null)).thenReturn(getListMockedOptionalProdutos());

        List<ProdutoDTO> produtos = getProdutosUseCase.listarProdutos(page, size, "Heimno", null, null);
        assertEquals(1, produtos.size());

    }

    @Test
    void itShouldRetornarNenhumProduto() {
        int page = 0;
        int size = 10;

        when(repository.findByNomeOrDescricaoOrPreco("Rafael", null, null)).thenReturn(getListMockedOptionalProdutos());

        List<ProdutoDTO> produtos = getProdutosUseCase.listarProdutos(page, size, "Heimno", null, null);
        assertEquals(0, produtos.size());

    }

    @Test
    void itShouldRetornarUmProdutoPeloPreco() {
        int page = 0;
        int size = 10;

        when(repository.findByNomeOrDescricaoOrPreco(null, null, new BigDecimal("10.5"))).thenReturn(getListMockedOptionalProdutos());

        List<ProdutoDTO> produtos = getProdutosUseCase.listarProdutos(page, size, null, null, new BigDecimal("10.5"));
        assertEquals(1, produtos.size());

    }

    @Test
    void itShouldRetornarUmProdutoPelaDescricao() {
        int page = 0;
        int size = 10;

        when(repository.findByNomeOrDescricaoOrPreco(null, "TESTE", new BigDecimal("10.5"))).thenReturn(getListMockedOptionalProdutos());

        List<ProdutoDTO> produtos = getProdutosUseCase.listarProdutos(page, size, null, "TESTE", new BigDecimal("10.5"));
        assertEquals(1, produtos.size());

    }

    private Page<Produto> getMockPageProduto() {
        List<Produto> produtos = new ArrayList<>();
        Categoria categoria = new Categoria(1L, "Sanduiche", "Um Sanduiche", new HashSet<>());
        produtos.addAll(List.of(
                new Produto(1L, "Heimno", "Niarhois", categoria, new BigDecimal("10.5"), ""),
                new Produto(2L, "Duerwadak", "Vigauval", categoria, new BigDecimal("12.5"), ""),
                new Produto(3L, "Eduarda", "Galhad", categoria, new BigDecimal("10.5"), ""),
                new Produto(4L, "Niarhois", "Galhad", categoria, new BigDecimal("10.5"), ""),
                new Produto(5L, "Aegweo", "Duerwadak", categoria, new BigDecimal("10.5"), ""),
                new Produto(6L, "Relko", "Duerwadak", categoria, new BigDecimal("10.5"), ""),
                new Produto(7L, "Andreia", "Galhad", categoria, new BigDecimal("10.5"), ""),
                new Produto(8L, "Relkio", "Nocu", categoria, new BigDecimal("10.5"), ""),
                new Produto(9L, "Eita", "Nocu", categoria, new BigDecimal("10.5"), ""),
                new Produto(10L, "Nocu", "Niarhois", categoria, new BigDecimal("10.5"), "")
        ));
        return new PageImpl<>(produtos);
    }
    private Optional<List<Produto>> getListMockedOptionalProdutos(){
        Categoria categoria = new Categoria(1L, "Sanduiche", "Um Sanduiche", new HashSet<>());
        return Optional.of(List.of(
                new Produto(1L, "Heimno", "TESTE", categoria, new BigDecimal("10.5"), ""),
                new Produto(2L, "Duerwadak", "Vigauval", categoria, new BigDecimal("1345.0"), ""),
                new Produto(3L, "Eduarda", "Galhad", categoria, new BigDecimal("28.5"), ""),
                new Produto(4L, "Niarhois", "Galhad", categoria, new BigDecimal("15.99"), ""),
                new Produto(5L, "Aegweo", "Duerwadak", categoria, new BigDecimal("18.36"), ""),
                new Produto(6L, "Relko", "Duerwadak", categoria, new BigDecimal("67.5"), ""),
                new Produto(7L, "Andreia", "Galhad", categoria, new BigDecimal("16.25"), ""),
                new Produto(8L, "Relkio", "Nocu", categoria, new BigDecimal("288.36"), ""),
                new Produto(9L, "Eita", "Nocu", categoria, new BigDecimal("197.5"), ""),
                new Produto(10L, "Nocu", "Niarhois", categoria, new BigDecimal("132.5"), ""),
                new Produto(10L, "Laele", "Niarhois", categoria, new BigDecimal("58.5"), "")
        ));
    }

}

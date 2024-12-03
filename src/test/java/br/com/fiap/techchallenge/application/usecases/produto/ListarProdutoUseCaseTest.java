package br.com.fiap.techchallenge.application.usecases.produto;

import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarProdutoUseCaseTest {

    @Mock
    private IProdutoRepository produtoRepository;

    @InjectMocks
    private ListarProdutoUseCase listarProdutoUseCase;

    private Produto produto1;
    private Produto produto2;
    private Produto produto3;

    @BeforeEach
    void setUp() {
        produto1 = new Produto("Produto 1", "Descrição 1", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(100.0), "imagem.jpg");
        produto2 = new Produto("Produto 2", "Descrição 2", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(200.0), "imagem.jpg");
        produto3 = new Produto("Produto 3", "Descrição 3", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(300.0), "imagem.jpg");
    }

    @Test
    void testListarProdutos_Success() {
        List<Produto> produtosMock = Arrays.asList(produto1, produto2, produto3);
        when(produtoRepository.listarProdutos("Produto", "Descrição", new BigDecimal("100.00"))).thenReturn(produtosMock);
        List<Produto> produtos = listarProdutoUseCase.listarProdutos("Produto", "Descrição", new BigDecimal("100.00"));
        assertNotNull(produtos);
        assertEquals(3, produtos.size());
        assertEquals("Produto 1", produtos.get(0).getNome());
        assertEquals("Produto 2", produtos.get(1).getNome());
        assertEquals("Produto 3", produtos.get(2).getNome());
        verify(produtoRepository, times(1)).listarProdutos("Produto", "Descrição", new BigDecimal("100.00"));
    }

    @Test
    void testListarProdutosPorCategoria_Success() {
        List<Produto> produtosMock = Arrays.asList(produto1, produto2);
        when(produtoRepository.listarProdutosPorCategoria(CategoriaEnum.LANCHE)).thenReturn(produtosMock);
        List<Produto> produtos = listarProdutoUseCase.listarProdutosPorCategoria(CategoriaEnum.LANCHE);
        assertNotNull(produtos);
        assertEquals(2, produtos.size());
        assertEquals("Produto 1", produtos.get(0).getNome());
        assertEquals("Produto 2", produtos.get(1).getNome());
        verify(produtoRepository, times(1)).listarProdutosPorCategoria(CategoriaEnum.LANCHE);
    }

    @Test
    void testListarProdutos_EmptyResult() {
        when(produtoRepository.listarProdutos("Produto Inexistente", "Descrição Inexistente", new BigDecimal("1000.00")))
                .thenReturn(Arrays.asList());
        List<Produto> produtos = listarProdutoUseCase.listarProdutos("Produto Inexistente", "Descrição Inexistente", new BigDecimal("1000.00"));
        assertNotNull(produtos);
        assertTrue(produtos.isEmpty());
        verify(produtoRepository, times(1)).listarProdutos("Produto Inexistente", "Descrição Inexistente", new BigDecimal("1000.00"));
    }

    @Test
    void testBuscaProdutosPorId_Success() {
        long id = anyLong();
        when(produtoRepository.findById(id)).thenReturn(produto1);
        Produto produto = listarProdutoUseCase.buscarProdutoPorId(id);
        assertNotNull(produto);
        assertEquals("Produto 1", produto.getNome());
        verify(produtoRepository, times(1)).findById(id);
    }

    @Test
    void testBuscaProdutoPorIdERetornaExceptionPorNaoExistir() {
        long id = 10L;
        when(produtoRepository.findById(id)).thenThrow(new ProdutoException(ErrosEnum.PRODUTO_NAO_ENCONTRADO));
        ProdutoException thrown = assertThrows(ProdutoException.class, () -> {
            listarProdutoUseCase.buscarProdutoPorId(id);
        });
        assertEquals(ErrosEnum.PRODUTO_NAO_ENCONTRADO, thrown.getError());
        verify(produtoRepository, times(1)).findById(id);
    }
}

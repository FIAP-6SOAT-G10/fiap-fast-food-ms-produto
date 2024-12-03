package br.com.fiap.techchallenge.application.usecases.produto;

import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarProdutoUseCaseTest {

    @Mock
    private IProdutoRepository produtoRepository;

    @InjectMocks
    private CadastrarProdutoUseCase cadastrarProdutoUseCase;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto("Produto Teste", "Descrição do Produto", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(19.99), "imagem.jpg");
    }

    @Test
    void testCriarProduto_Success() {
        Produto produtoCriado  = new Produto("Produto Teste", "Descrição do Produto", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(100.0), "imagem.jpg");
        when(produtoRepository.criarProduto(produto)).thenReturn(produtoCriado);
        Produto result = cadastrarProdutoUseCase.criarProduto(produto);
        assertNotNull(result);
        assertEquals("Produto Teste", result.getNome());
        assertEquals("Descrição do Produto", result.getDescricao());
        assertEquals(100.0, result.getPreco().doubleValue());
        verify(produtoRepository, times(1)).criarProduto(produto);
    }

    @Test
    void testCriarProduto_FailedRepository() {
        when(produtoRepository.criarProduto(produto)).thenThrow(new RuntimeException("Database error"));
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            cadastrarProdutoUseCase.criarProduto(produto);
        });
        assertEquals("Database error", thrown.getMessage());
        verify(produtoRepository, times(1)).criarProduto(produto);
    }
}


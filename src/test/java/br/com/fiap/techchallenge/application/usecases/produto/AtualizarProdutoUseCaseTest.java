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
class AtualizarProdutoUseCaseTest {

    @Mock
    private IProdutoRepository produtoRepository;

    @InjectMocks
    private AtualizarProdutoUseCase atualizarProdutoUseCase;

    private Produto produto;

    @BeforeEach
    public void setUp() {
        // Initialize a Produto object for testing
        produto = new Produto("Produto Teste", "Descrição do Produto", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(100.0), "imagem.jpg");
    }

    @Test
    void testAtualizarProduto_Success() {
        Produto produtoAtualizado = new Produto("Produto Atualizado", "Descrição Atualizada", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(120.0), "imagem.jpg");
        when(produtoRepository.atualizarProduto(anyLong(), eq(produtoAtualizado))).thenReturn(produtoAtualizado);
        Produto result = atualizarProdutoUseCase.atualizarProduto("123", produtoAtualizado);
        assertNotNull(result);
        assertEquals("Produto Atualizado", result.getNome());
        assertEquals("Descrição Atualizada", result.getDescricao());
        assertEquals(120.0, result.getPreco().doubleValue());
        verify(produtoRepository, times(1)).atualizarProduto(anyLong(), eq(produtoAtualizado));
    }

    @Test
    void testAtualizarProduto_InvalidId() {
        String invalidId = "abc"; // Invalid product ID format
        assertThrows(NumberFormatException.class, () -> {
            atualizarProdutoUseCase.atualizarProduto(invalidId, produto);
        });
        verify(produtoRepository, times(0)).atualizarProduto(anyLong(), any());
    }
}

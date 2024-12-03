package br.com.fiap.techchallenge.application.usecases.produto;

import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarProdutoUseCaseTest {

    @Mock
    private IProdutoRepository produtoRepository;

    @InjectMocks
    private DeletarProdutoUseCase deletarProdutoUseCase;

    private String productId;

    @BeforeEach
    void setUp() {
        productId = "123";
    }

    @Test
    void testDeletarProduto_Success() {
        deletarProdutoUseCase.deletarProduto(productId);
        verify(produtoRepository, times(1)).deletarProduto(Long.valueOf(productId));
    }

    @Test
    void testDeletarProduto_InvalidId() {
        String invalidProductId = "abc"; // Invalid ID
        assertThrows(NumberFormatException.class, () -> {
            deletarProdutoUseCase.deletarProduto(invalidProductId);
        });
        verify(produtoRepository, times(0)).deletarProduto(anyLong());
    }
}

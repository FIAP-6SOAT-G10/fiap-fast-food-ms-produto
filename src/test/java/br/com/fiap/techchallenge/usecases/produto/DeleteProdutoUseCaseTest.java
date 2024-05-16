package br.com.fiap.techchallenge.usecases.produto;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.usecases.produto.DeleteProdutoUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.ports.produto.DeleteProdutoOutboundPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class DeleteProdutoUseCaseTest {

    @Mock
    private DeleteProdutoOutboundPort port;

    private DeleteProdutoUseCase deleteProdutoUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        deleteProdutoUseCase = new DeleteProdutoUseCase(port);
    }

    @Test
    public void shouldDeleteProdutoWhenValidIdProvided() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        Produto produto = new Produto();

        when(port.deletarProduto(1L)).thenReturn(produto);

        Produto result = deleteProdutoUseCase.deletarProduto("1");

        assertEquals(produto, result);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidIdProvided() {
        ProdutoDTO produtoDTO = new ProdutoDTO();

        assertThrows(ProdutoException.class, () -> deleteProdutoUseCase.deletarProduto("invalid"));
    }
}
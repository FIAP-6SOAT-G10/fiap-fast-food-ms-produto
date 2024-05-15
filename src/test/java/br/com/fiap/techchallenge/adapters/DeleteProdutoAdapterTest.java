package br.com.fiap.techchallenge.adapters;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeleteProdutoAdapterTest {

    @Mock
    private ProdutoRepository produtoRepository;

    private DeleteProdutoAdapter deleteProdutoAdapter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        deleteProdutoAdapter = new DeleteProdutoAdapter(produtoRepository);
    }

    @Test
    public void shouldDeleteProdutoWhenValidIdProvided() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        Produto produto = new Produto();

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Produto result = deleteProdutoAdapter.deletarProduto(1L);

        assertEquals(produto, result);
        verify(produtoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidIdProvided() {
        ProdutoDTO produtoDTO = new ProdutoDTO();

        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProdutoException.class, () -> deleteProdutoAdapter.deletarProduto(1L));
    }
}
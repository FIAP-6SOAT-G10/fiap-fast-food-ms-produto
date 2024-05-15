package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProdutoDeleteControllerTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoController produtoController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldDeleteProdutoWhenValidIdProvided() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        Produto produto = new Produto();

        when(produtoRepository.findById(Long.valueOf("1"))).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        ResponseEntity<Produto> result = produtoController.deletarProduto("1", produtoDTO);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(produtoRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void shouldReturnNotFoundWhenInvalidIdProvided() {
        ProdutoDTO produtoDTO = new ProdutoDTO();

        when(produtoRepository.findById(Long.valueOf("1"))).thenReturn(Optional.empty());

        assertThrows(ProdutoException.class, () -> produtoController.deletarProduto("1", produtoDTO));
    }
}
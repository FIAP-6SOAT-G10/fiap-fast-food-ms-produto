//package br.com.fiap.techchallenge.adapters;
//
//import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
//import br.com.fiap.techchallenge.naousar.adapters.produto.DeleteProdutoAdapter;
//import br.com.fiap.techchallenge.naousar.domain.valueobjects.ProdutoDTO;
//import br.com.fiap.techchallenge.naousar.infra.exception.ProdutoException;
//import br.com.fiap.techchallenge.infra.persistence.ProdutoEntityRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//public class DeleteProdutoEntityAdapterTest {
//
//    @Mock
//    private ProdutoEntityRepository produtoEntityRepository;
//
//    private DeleteProdutoAdapter deleteProdutoAdapter;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        deleteProdutoAdapter = new DeleteProdutoAdapter(produtoEntityRepository);
//    }
//
//    @Test
//    public void shouldDeleteProdutoWhenValidIdProvided() {
//        ProdutoDTO produtoDTO = new ProdutoDTO();
//        ProdutoEntity produtoEntity = new ProdutoEntity();
//
//        when(produtoEntityRepository.findById(1L)).thenReturn(Optional.of(produtoEntity));
//
//        ProdutoEntity result = deleteProdutoAdapter.deletarProduto(1L);
//
//        assertEquals(produtoEntity, result);
//        verify(produtoEntityRepository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenInvalidIdProvided() {
//        ProdutoDTO produtoDTO = new ProdutoDTO();
//
//        when(produtoEntityRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ProdutoException.class, () -> deleteProdutoAdapter.deletarProduto(1L));
//    }
//}
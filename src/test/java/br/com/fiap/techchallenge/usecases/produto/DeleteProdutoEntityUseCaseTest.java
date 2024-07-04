//package br.com.fiap.techchallenge.usecases.produto;
//
//import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
//import br.com.fiap.techchallenge.naousar.domain.usecases.produto.DeleteProdutoUseCase;
//import br.com.fiap.techchallenge.naousar.domain.valueobjects.ProdutoDTO;
//import br.com.fiap.techchallenge.naousar.infra.exception.ProdutoException;
//import br.com.fiap.techchallenge.naousar.ports.produto.DeleteProdutoOutboundPort;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.when;
//
//public class DeleteProdutoEntityUseCaseTest {
//
//    @Mock
//    private DeleteProdutoOutboundPort port;
//
//    private DeleteProdutoUseCase deleteProdutoUseCase;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        deleteProdutoUseCase = new DeleteProdutoUseCase(port);
//    }
//
//    @Test
//    public void shouldDeleteProdutoWhenValidIdProvided() {
//        ProdutoDTO produtoDTO = new ProdutoDTO();
//        ProdutoEntity produtoEntity = new ProdutoEntity();
//
//        when(port.deletarProduto(1L)).thenReturn(produtoEntity);
//
//        ProdutoEntity result = deleteProdutoUseCase.deletarProduto("1");
//
//        assertEquals(produtoEntity, result);
//    }
//
//    @Test
//    public void shouldThrowExceptionWhenInvalidIdProvided() {
//        ProdutoDTO produtoDTO = new ProdutoDTO();
//
//        assertThrows(ProdutoException.class, () -> deleteProdutoUseCase.deletarProduto("invalid"));
//    }
//}
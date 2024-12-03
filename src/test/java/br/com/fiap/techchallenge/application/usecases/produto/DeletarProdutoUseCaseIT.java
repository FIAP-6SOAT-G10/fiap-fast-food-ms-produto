package br.com.fiap.techchallenge.application.usecases.produto;

import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("integration-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
class DeletarProdutoUseCaseIT {

    @Autowired
    private DeletarProdutoUseCase deletarProdutoUseCase;

    @Autowired
    private IProdutoRepository produtoRepository;

    @Test
    void testDeletarProduto_Success() {
        Produto produto = produtoRepository.findById(1l);
        deletarProdutoUseCase.deletarProduto(produto.getId().toString());
        assertTrue(true);
    }

    @Test
    void testDeletarProduto_InvalidId() {
        String invalidProductId = "abc"; // Invalid ID
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            deletarProdutoUseCase.deletarProduto(invalidProductId);
        });
        assertEquals("For input string: \"abc\"", exception.getMessage());
    }

    @Test
    void testDeletarProduto_NonExistentProduct() {
        Long nonExistentProductId = 9999L;
        ProdutoException exception = assertThrows(ProdutoException.class, () -> {
            produtoRepository.findById(nonExistentProductId);
        });
        assertEquals(ErrosEnum.PRODUTO_NAO_ENCONTRADO.getMessage(), exception.getMessage());
    }
}

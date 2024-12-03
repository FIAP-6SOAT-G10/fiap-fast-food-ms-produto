package br.com.fiap.techchallenge.application.usecases.produto;

import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
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

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("integration-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
class ListarProdutoUseCaseIT {

    @Autowired
    private IProdutoRepository produtoRepository;

    @Autowired
    private ListarProdutoUseCase listarProdutoUseCase;

    @Test
    void testListarProdutos_Success() {
        List<Produto> produtos = listarProdutoUseCase.listarProdutos(null, null, null);
        assertNotNull(produtos);
    }

    @Test
    void testListarProdutosPorCategoria_Success() {
        List<Produto> produtos = listarProdutoUseCase.listarProdutosPorCategoria(CategoriaEnum.LANCHE);
        assertNotNull(produtos);
    }

    @Test
    void testListarProdutos_EmptyResult() {
        List<Produto> produtos = listarProdutoUseCase.listarProdutos("Produto Inexistente", "Descrição Inexistente", new BigDecimal("1000.00"));
        assertNotNull(produtos);
        assertTrue(produtos.isEmpty());
    }

    @Test
    void testBuscaProdutosPorId_Success() {
        Produto produto = produtoRepository.findById(3L);
        assertNotNull(produto);
    }

    @Test
    void testBuscaProdutoPorIdERetornaExceptionPorNaoExistir() {
        ProdutoException thrown = assertThrows(ProdutoException.class, () -> {
            listarProdutoUseCase.buscarProdutoPorId(999L);
        });
        assertEquals(ErrosEnum.PRODUTO_NAO_ENCONTRADO, thrown.getError());
    }
}
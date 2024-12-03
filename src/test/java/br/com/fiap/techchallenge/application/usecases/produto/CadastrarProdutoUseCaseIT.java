package br.com.fiap.techchallenge.application.usecases.produto;

import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("integration-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
class CadastrarProdutoUseCaseIT {

    @Autowired
    private CadastrarProdutoUseCase cadastrarProdutoUseCase;

    @Autowired
    private IProdutoRepository produtoRepository;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto("Produto Teste", "Descrição do Produto", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(19.99), "imagem.jpg");
    }

    @Test
    void testCriarProduto_Success() {

        Produto produtoCriado  = new Produto("Produto Teste", "Descrição do Produto", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(100.0), "imagem.jpg");
        Produto result = cadastrarProdutoUseCase.criarProduto(produtoCriado);
        assertNotNull(result);
        assertEquals("Produto Teste", result.getNome());
        assertEquals("Descrição do Produto", result.getDescricao());
        assertEquals(100.0, result.getPreco().doubleValue());

        Produto produtoPersistido = produtoRepository.findById(result.getId());
        assertEquals(result.getNome(), produtoPersistido.getNome());
        assertEquals(result.getDescricao(), produtoPersistido.getDescricao());
    }

    @Test
    void testCriarProduto_FailedRepository() {

        Produto produtoInvalido = new Produto(1l,"Big Fiap", "Dois hambúrgueres, alface americana, queijo cheddar, maionese, cebola, picles e pão com gergelim", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(26.9), "imagem.jpg");
        Exception thrown = assertThrows(ProdutoException.class, () -> {
            cadastrarProdutoUseCase.criarProduto(produtoInvalido);
        });
        assertEquals(ErrosEnum.PRODUTO_JA_EXISTE.getMessage(), thrown.getMessage());
    }
}

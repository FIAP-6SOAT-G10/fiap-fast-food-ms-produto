package br.com.fiap.techchallenge.application.usecases.produto;

import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("integration-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class AtualizarProdutoUseCaseIT {

    @Autowired
    private IProdutoRepository produtoRepository;

    @Autowired
    private AtualizarProdutoUseCase atualizarProdutoUseCase;

    @Test
    void testAtualizarProduto_Success() {

        Produto produtoCriado  = new Produto("Blister Cajun", "Maionese temperada picante, defumada, com toque de cebola e limão. Produzido com aromatizantes e corantes naturais", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(100.0), "imagem.jpg");
        Produto produto = produtoRepository.criarProduto(produtoCriado);
        Produto produtoAtualizado = new Produto(produto.getId(), "Cajun Blisten", "Maionese apenas", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(100.0), "imagem.jpg");
        Produto result = atualizarProdutoUseCase.atualizarProduto(String.valueOf(produto.getId()), produtoAtualizado);
        assertNotNull(result);
        assertEquals("Cajun Blisten", result.getNome());
        assertEquals("Maionese apenas", result.getDescricao());
        assertEquals(100.0, result.getPreco().doubleValue());

        Produto produtoSalvo = produtoRepository.findById(produto.getId());
        assertEquals("Cajun Blisten", produtoSalvo.getNome());
        assertEquals("Maionese apenas", produtoSalvo.getDescricao());
        assertEquals(100.0, produtoSalvo.getPreco().doubleValue());
    }

    @Test
    void testAtualizarProduto_InvalidId() {
        Produto produtoCriado  = new Produto("Blister Sweet Chilli", "Molho não emulsionado tipo sweet chilli, com sabor adocicado e pedaços de pimenta. Produzido com aromatizantes e corantes naturais.", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(80.0), "imagem.jpg");
        Produto produto = produtoRepository.criarProduto(produtoCriado);
        String invalidId = "999999";
        Produto produtoAtualizado = new Produto(produto.getId(), "Sweet Chilli", "Molho não emulsionado tipo sweet chilli", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(80.0), "imagem.jpg");
        assertThrows(ProdutoException.class, () -> {
            atualizarProdutoUseCase.atualizarProduto(invalidId, produtoAtualizado);
        });
    }
}

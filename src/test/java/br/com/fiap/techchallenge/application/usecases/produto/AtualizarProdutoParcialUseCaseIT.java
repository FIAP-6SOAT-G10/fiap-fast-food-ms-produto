package br.com.fiap.techchallenge.application.usecases.produto;

import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.junit.jupiter.api.BeforeEach;
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
class AtualizarProdutoParcialUseCaseIT {

    @Autowired
    private IProdutoRepository produtoRepository;

    @Autowired
    private AtualizarProdutoParcialUseCase atualizarProdutoParcialUseCase;

    private JsonPatch validPatch;

    Produto produto;

    @BeforeEach
    public void setUp() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String patchJson = "[ {\"op\": \"replace\", \"path\": \"/nome\", \"value\": \"Produto Atualizado\"} ]";
        validPatch = objectMapper.readValue(patchJson, JsonPatch.class);

        Produto produto1 = new Produto("Produto Original", "Descrição Original", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(19.99), "imagem.jpg");
        produto = produtoRepository.criarProduto(produto1);
    }

    @Test
    void testAtualizarProduto_Success()  {
        Produto produtoOriginal = produtoRepository.listarProdutos("Produto Original","Descrição Original", BigDecimal.valueOf(19.99)).get(0);
        Produto result = atualizarProdutoParcialUseCase.atualizarDadosProduto(String.valueOf(produtoOriginal.getId()), validPatch);
        assertNotNull(result);
        assertEquals("Produto Atualizado", result.getNome());
        assertEquals(produtoOriginal.getDescricao(), result.getDescricao());
    }

    @Test
    void testAtualizarProduto_InvalidIdFormat() {
        String invalidId = "abc"; // ID inválido
        ProdutoException thrown = assertThrows(ProdutoException.class, () -> {
            atualizarProdutoParcialUseCase.atualizarDadosProduto(invalidId, validPatch);
        });
        assertEquals(ErrosEnum.PRODUTO_CODIGO_IDENTIFICADOR_INVALIDO.getMessage(), thrown.getMessage());
    }

    @Test
    void testAtualizarProduto_MissingNome() throws Exception {
        String patchJson = "[ {\"op\": \"replace\", \"path\": \"/nome\", \"value\": \"\"} ]";  // Nome vazio
        JsonPatch emptyNomePatch = new ObjectMapper().readValue(patchJson, JsonPatch.class);
        ProdutoException thrown = assertThrows(ProdutoException.class, () -> {
            atualizarProdutoParcialUseCase.atualizarDadosProduto("123", emptyNomePatch);
        });
        assertEquals(ErrosEnum.PRODUTO_NOME_OBRIGATORIO.getMessage(), thrown.getMessage());
    }

    @Test
    void testAtualizarProduto_MissingDescricao() throws Exception {
        String patchJson = "[ {\"op\": \"replace\", \"path\": \"/descricao\", \"value\": \"\"} ]";  // Descrição vazia
        JsonPatch emptyDescricaoPatch = new ObjectMapper().readValue(patchJson, JsonPatch.class);
        ProdutoException thrown = assertThrows(ProdutoException.class, () -> {
            atualizarProdutoParcialUseCase.atualizarDadosProduto("123", emptyDescricaoPatch);
        });
        assertEquals(ErrosEnum.PRODUTO_DESCRICAO_OBRIGATORIO.getMessage(), thrown.getMessage());
    }
}

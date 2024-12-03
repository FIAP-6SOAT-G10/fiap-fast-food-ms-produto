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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarProdutoParcialUseCaseTest {

    @Mock
    private IProdutoRepository produtoRepository;

    @InjectMocks
    private AtualizarProdutoParcialUseCase atualizarProdutoParcialUseCase;

    private JsonPatch validPatch;

    @BeforeEach
    void setUp() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String patchJson = "[ {\"op\": \"replace\", \"path\": \"/nome\", \"value\": \"Produto 1\"} ]";
        validPatch = objectMapper.readValue(patchJson, JsonPatch.class);
    }

    @Test
    void testAtualizarDadosProduto_Success() {
        Produto produto = new Produto("Produto 1", "Descrição do produto", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(19.99), "imagem.jpg");
        when(produtoRepository.atualizarDadosProduto(anyLong(), eq(validPatch))).thenReturn(produto);
        Produto result = atualizarProdutoParcialUseCase.atualizarDadosProduto("123", validPatch);
        assertNotNull(result);
        assertEquals("Produto 1", result.getNome());
        verify(produtoRepository, times(1)).atualizarDadosProduto(anyLong(), eq(validPatch));
    }

    @Test
    void testAtualizarDadosProduto_InvalidIdFormat() {
        String invalidId = "abc";
        ProdutoException thrown = assertThrows(ProdutoException.class, () -> {
            atualizarProdutoParcialUseCase.atualizarDadosProduto(invalidId, validPatch);
        });
        assertEquals(ErrosEnum.PRODUTO_CODIGO_IDENTIFICADOR_INVALIDO, thrown.getError());
        verify(produtoRepository, times(0)).atualizarDadosProduto(anyLong(), any());
    }

    @Test
    void testAtualizarDadosProduto_MissingNome() throws Exception {
        String patchJson = "[ {\"op\": \"replace\", \"path\": \"/nome\", \"value\": \"\"} ]"; // Empty nome
        JsonPatch emptyNomePatch = new ObjectMapper().readValue(patchJson, JsonPatch.class);
        ProdutoException thrown = assertThrows(ProdutoException.class, () -> {
            atualizarProdutoParcialUseCase.atualizarDadosProduto("123", emptyNomePatch);
        });
        assertEquals(ErrosEnum.PRODUTO_NOME_OBRIGATORIO, thrown.getError());
        verify(produtoRepository, times(0)).atualizarDadosProduto(anyLong(), any());
    }

    @Test
    void testAtualizarDadosProduto_MissingDescricao() throws Exception {
        String patchJson = "[ {\"op\": \"replace\", \"path\": \"/descricao\", \"value\": \"\"} ]"; // Empty descricao
        JsonPatch emptyDescricaoPatch = new ObjectMapper().readValue(patchJson, JsonPatch.class);
        ProdutoException thrown = assertThrows(ProdutoException.class, () -> {
            atualizarProdutoParcialUseCase.atualizarDadosProduto("123", emptyDescricaoPatch);
        });
        assertEquals(ErrosEnum.PRODUTO_DESCRICAO_OBRIGATORIO, thrown.getError());
        verify(produtoRepository, times(0)).atualizarDadosProduto(anyLong(), any());
    }
}
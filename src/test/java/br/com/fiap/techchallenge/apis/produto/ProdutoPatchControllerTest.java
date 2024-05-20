package br.com.fiap.techchallenge.apis.produto;

import br.com.fiap.techchallenge.adapters.produto.PatchProdutoAdapter;
import br.com.fiap.techchallenge.apis.ProdutoController;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.repositories.CategoriaRepository;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProdutoPatchControllerTest {

    @Mock
    ProdutoMapper produtoMapper;

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    CategoriaRepository categoriaRepository;

    @Mock
    PatchProdutoAdapter patchProdutoAdapter;

    @InjectMocks
    private ProdutoController controller;

    @Test
    void shouldAtualizarApenasONomeDoProduto() throws JsonPointerException {
        Produto produto = new Produto();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(produtoRepository.saveAndFlush(any())).thenReturn(produto);
        when(patchProdutoAdapter.atualizarDadosProduto(anyLong(), any())).thenReturn(produto);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("Novo nome", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/nome");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        ResponseEntity<Produto> response = controller.atualizarDadosProduto("1", jsonPatch);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldAtualizarApenasADescricaoDoProduto() throws JsonPointerException {
        Produto produto = new Produto();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(produtoRepository.saveAndFlush(any())).thenReturn(produto);
        when(patchProdutoAdapter.atualizarDadosProduto(anyLong(), any())).thenReturn(produto);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("Nova descricao", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/descricao");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        ResponseEntity<Produto> response = controller.atualizarDadosProduto("1", jsonPatch);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldAtualizarApenasACategoriaDoProduto() throws JsonPointerException {
        Produto produto = new Produto();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(produtoRepository.saveAndFlush(any())).thenReturn(produto);
        when(patchProdutoAdapter.atualizarDadosProduto(anyLong(), any())).thenReturn(produto);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue(3, JsonNode.class);

        JsonPointer pointer = new JsonPointer("/categoria");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        ResponseEntity<Produto> response = controller.atualizarDadosProduto("1", jsonPatch);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldAtualizarApenasOPrecoDoProduto() throws JsonPointerException {
        Produto produto = new Produto();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(produtoRepository.saveAndFlush(any())).thenReturn(produto);
        when(patchProdutoAdapter.atualizarDadosProduto(anyLong(), any())).thenReturn(produto);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue(15.9, JsonNode.class);

        JsonPointer pointer = new JsonPointer("/preco");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        ResponseEntity<Produto> response = controller.atualizarDadosProduto("1", jsonPatch);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldAtualizarApenasAImagemDoProduto() throws JsonPointerException {
        Produto produto = new Produto();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(produtoRepository.saveAndFlush(any())).thenReturn(produto);
        when(patchProdutoAdapter.atualizarDadosProduto(anyLong(), any())).thenReturn(produto);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("Nova Imagem", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/imagem");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        ResponseEntity<Produto> response = controller.atualizarDadosProduto("1", jsonPatch);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void mustLancarExcecaoQuandoOIdentificadorForAlfanumerico() throws JsonPointerException {
        Produto produto = new Produto();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(produtoRepository.saveAndFlush(any())).thenReturn(produto);
        when(patchProdutoAdapter.atualizarDadosProduto(anyLong(), any())).thenReturn(produto);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("Nova Imagem", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/imagem");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        assertThrows(ProdutoException.class, () -> controller.atualizarDadosProduto("A", jsonPatch));
    }

    @Test
    void mustLancarExcecaoQuandoONomeForInvalidoOuVazio() throws JsonPointerException {
        Produto produto = new Produto();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(produtoRepository.saveAndFlush(any())).thenReturn(produto);
        when(patchProdutoAdapter.atualizarDadosProduto(anyLong(), any())).thenReturn(produto);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/nome");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        assertThrows(ProdutoException.class, () -> controller.atualizarDadosProduto("1", jsonPatch));
    }

    @Test
    void mustLancarExcecaoQuandoADescricaoForInvalidoOuVazio() throws JsonPointerException {
        Produto produto = new Produto();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(produtoRepository.saveAndFlush(any())).thenReturn(produto);
        when(patchProdutoAdapter.atualizarDadosProduto(anyLong(), any())).thenReturn(produto);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/descricao");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        assertThrows(ProdutoException.class, () -> controller.atualizarDadosProduto("1", jsonPatch));
    }

    @Test
    void mustLancarExcecaoQuandoACategoriaForInvalidoOuVazio() throws JsonPointerException {
        Produto produto = new Produto();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(produtoRepository.saveAndFlush(any())).thenReturn(produto);
        when(patchProdutoAdapter.atualizarDadosProduto(anyLong(), any())).thenReturn(produto);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/categoria");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        assertThrows(ProdutoException.class, () -> controller.atualizarDadosProduto("1", jsonPatch));
    }

    @Test
    void mustLancarExcecaoQuandoOPrecoForInvalidoOuVazio() throws JsonPointerException {
        Produto produto = new Produto();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(produtoRepository.saveAndFlush(any())).thenReturn(produto);
        when(patchProdutoAdapter.atualizarDadosProduto(anyLong(), any())).thenReturn(produto);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/preco");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        assertThrows(ProdutoException.class, () -> controller.atualizarDadosProduto("1", jsonPatch));
    }

    @Test
    void mustLancarExcecaoQuandoAImagemForInvalidoOuVazio() throws JsonPointerException {
        Produto produto = new Produto();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(produtoRepository.saveAndFlush(any())).thenReturn(produto);
        when(patchProdutoAdapter.atualizarDadosProduto(anyLong(), any())).thenReturn(produto);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/imagem");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        assertThrows(ProdutoException.class, () -> controller.atualizarDadosProduto("1", jsonPatch));
    }

}
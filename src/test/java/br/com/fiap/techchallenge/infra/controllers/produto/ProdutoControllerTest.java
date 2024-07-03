package br.com.fiap.techchallenge.infra.controllers.produto;

import br.com.fiap.techchallenge.application.usecases.produto.*;
import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.infra.controllers.CategoriaDTO;
import br.com.fiap.techchallenge.infra.controllers.ProdutoController;
import br.com.fiap.techchallenge.infra.controllers.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.CategoriaException;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.gateways.ProdutoRepository;
import br.com.fiap.techchallenge.infra.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.infra.persistence.CategoriaEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.ProdutoEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;
import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class ProdutoControllerTest {

    @Mock
    ProdutoMapper produtoMapper;

    @Mock
    ProdutoEntityRepository produtoEntityRepository;

    @Mock
    CategoriaEntityRepository categoriaEntityRepository;

    ProdutoRepository produtoRepository;

    ListarProdutoUseCase listarProdutoUseCase;
    CadastrarProdutoUseCase cadastrarProdutoUseCase;
    DeletarProdutoUseCase deletarProdutoUseCase;
    AtualizarProdutoParcialUseCase atualizarProdutoParcialUseCase;
    AtualizarProdutoUseCase atualizarProdutoUseCase;

    @BeforeEach
    void setup() {
        produtoRepository = new ProdutoRepository(produtoEntityRepository, categoriaEntityRepository, produtoMapper);

        listarProdutoUseCase = new ListarProdutoUseCase(produtoRepository);
        cadastrarProdutoUseCase = new CadastrarProdutoUseCase(produtoRepository);
        deletarProdutoUseCase = new DeletarProdutoUseCase(produtoRepository);
        atualizarProdutoParcialUseCase = new AtualizarProdutoParcialUseCase(produtoRepository);
        atualizarProdutoUseCase = new AtualizarProdutoUseCase(produtoRepository);
    }

    @Test
    void shouldListarProdutosPorCategoriaInformada() {
        when(produtoEntityRepository.findAllByCategoriaEntityId(anyInt())).thenReturn(Optional.of(criarListaDeProdutosEntity()));
        when(produtoMapper.fromListEntityToListDomain(anyList())).thenReturn(criarListaDeProdutos());

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        ResponseEntity<List<ProdutoDTO>> response = produtoController.buscarProdutosPorCategoria("lanche");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void mustLancarCategoriaExceptionQuandoACategoriaNaoForValida() {
        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(CategoriaException.class, () -> produtoController.buscarProdutosPorCategoria("inexistente"));
    }

    @Test
    public void shouldDeleteProdutoWhenValidIdProvided() {
        ProdutoEntity produtoEntity = new ProdutoEntity();

        when(produtoEntityRepository.findById(Long.valueOf("1"))).thenReturn(Optional.of(produtoEntity));
        when(produtoEntityRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        ResponseEntity<ProdutoEntity> result = produtoController.deletarProduto("1");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(produtoEntityRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void shouldReturnNotFoundWhenInvalidIdProvided() {
        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.empty());

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(ProdutoException.class, () -> produtoController.deletarProduto("1"));
    }

    @Test
    void shouldCadastrarProdutoComSucesso() {
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        Produto produto = mock(Produto.class);

        when(categoriaEntityRepository.findByNome(anyString())).thenReturn(Optional.of(categoriaEntity));
        when(produtoMapper.fromDomainToEntity(any())).thenReturn(produtoEntity);
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);

        ProdutoDTO produtoDTO = new ProdutoDTO(
                null,
                "Grande Lanche",
                "Um grande lanche",
                new CategoriaDTO(CategoriaEnum.LANCHE.getNome()),
                BigDecimal.ONE,
                "Imagem");

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertEquals(201, produtoController.cadastrarProduto(produtoDTO).getStatusCode().value());
    }

    @Test
    void mustLancarProdutoExceptionAoCadastrarProdutoSemNome() {
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        Produto produto = mock(Produto.class);

        when(categoriaEntityRepository.findByNome(anyString())).thenReturn(Optional.of(categoriaEntity));
        when(produtoMapper.fromDomainToEntity(any())).thenReturn(produtoEntity);
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);

        ProdutoDTO produtoDTO = new ProdutoDTO(
                null,
                null,
                "Um grande lanche",
                new CategoriaDTO(CategoriaEnum.LANCHE.getNome()),
                BigDecimal.ONE,
                "Imagem");

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(IllegalArgumentException.class, () -> produtoController.cadastrarProduto(produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionAoCadastrarProdutoSemDescricao() {
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        Produto produto = mock(Produto.class);

        when(categoriaEntityRepository.findByNome(anyString())).thenReturn(Optional.of(categoriaEntity));
        when(produtoMapper.fromDomainToEntity(any())).thenReturn(produtoEntity);
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);

        ProdutoDTO produtoDTO = new ProdutoDTO(
                null,
                "Grande Lanche",
                null,
                new CategoriaDTO(CategoriaEnum.LANCHE.getNome()),
                BigDecimal.ONE,
                "Imagem");

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(IllegalArgumentException.class, () -> produtoController.cadastrarProduto(produtoDTO));
    }

    @Test
    void mustLancarCategoriaExceptionAoCadastrarProdutoSemCategoria() {
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        Produto produto = mock(Produto.class);

        when(categoriaEntityRepository.findByNome(anyString())).thenReturn(Optional.of(categoriaEntity));
        when(produtoMapper.fromDomainToEntity(any())).thenReturn(produtoEntity);
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);

        ProdutoDTO produtoDTO = new ProdutoDTO(
                null,
                "Grande Lanche",
                "Um grande lanche",
                null,
                BigDecimal.ONE,
                "Imagem");

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(IllegalArgumentException.class, () -> produtoController.cadastrarProduto(produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionAoCadastrarProdutoSemPreco() {
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        Produto produto = mock(Produto.class);

        when(categoriaEntityRepository.findByNome(anyString())).thenReturn(Optional.of(categoriaEntity));
        when(produtoMapper.fromDomainToEntity(any())).thenReturn(produtoEntity);
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);

        ProdutoDTO produtoDTO = new ProdutoDTO(
                null,
                "Grande Lanche",
                "Um grande lanche",
                new CategoriaDTO(CategoriaEnum.LANCHE.getNome()),
                null,
                "Imagem");

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(IllegalArgumentException.class, () -> produtoController.cadastrarProduto(produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionAoCadastrarProdutoSemImagem() {
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        Produto produto = mock(Produto.class);

        when(categoriaEntityRepository.findByNome(anyString())).thenReturn(Optional.of(categoriaEntity));
        when(produtoMapper.fromDomainToEntity(any())).thenReturn(produtoEntity);
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);

        ProdutoDTO produtoDTO = new ProdutoDTO(
                null,
                "Grande Lanche",
                "Um grande lanche",
                new CategoriaDTO(CategoriaEnum.LANCHE.getNome()),
                BigDecimal.ONE,
                null);

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(IllegalArgumentException.class, () -> produtoController.cadastrarProduto(produtoDTO));
    }

    @Test
    void shouldAtualizarApenasONomeDoProduto() throws JsonPointerException {
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        Produto produto = mock(Produto.class);
        Categoria categoria = mock(Categoria.class);

        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(categoriaEntityRepository.findById(anyLong())).thenReturn(Optional.of(categoriaEntity));
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);
        when(produto.getCategoria()).thenReturn(categoria);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("Novo nome", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/nome");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        ResponseEntity<ProdutoDTO> response = produtoController.atualizarDadosProduto("1", jsonPatch);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldAtualizarApenasADescricaoDoProduto() throws JsonPointerException {
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        Produto produto = mock(Produto.class);
        Categoria categoria = mock(Categoria.class);

        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(categoriaEntityRepository.findById(anyLong())).thenReturn(Optional.of(categoriaEntity));
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);
        when(produto.getCategoria()).thenReturn(categoria);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("Nova descricao", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/descricao");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        ResponseEntity<ProdutoDTO> response = produtoController.atualizarDadosProduto("1", jsonPatch);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldAtualizarApenasACategoriaDoProduto() throws JsonPointerException {
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        Produto produto = mock(Produto.class);
        Categoria categoria = mock(Categoria.class);

        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(categoriaEntityRepository.findById(anyLong())).thenReturn(Optional.of(categoriaEntity));
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);
        when(produto.getCategoria()).thenReturn(categoria);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue(3, JsonNode.class);

        JsonPointer pointer = new JsonPointer("/categoria");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        ResponseEntity<ProdutoDTO> response = produtoController.atualizarDadosProduto("1", jsonPatch);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldAtualizarApenasOPrecoDoProduto() throws JsonPointerException {
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        Produto produto = mock(Produto.class);
        Categoria categoria = mock(Categoria.class);

        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(categoriaEntityRepository.findById(anyLong())).thenReturn(Optional.of(categoriaEntity));
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);
        when(produto.getCategoria()).thenReturn(categoria);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue(15.9, JsonNode.class);

        JsonPointer pointer = new JsonPointer("/preco");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        ResponseEntity<ProdutoDTO> response = produtoController.atualizarDadosProduto("1", jsonPatch);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldAtualizarApenasAImagemDoProduto() throws JsonPointerException {
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        Produto produto = mock(Produto.class);
        Categoria categoria = mock(Categoria.class);

        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(categoriaEntityRepository.findById(anyLong())).thenReturn(Optional.of(categoriaEntity));
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);
        when(produto.getCategoria()).thenReturn(categoria);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("Nova Imagem", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/imagem");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        ResponseEntity<ProdutoDTO> response = produtoController.atualizarDadosProduto("1", jsonPatch);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void mustLancarExcecaoQuandoOIdentificadorForAlfanumerico() throws JsonPointerException {
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        Produto produto = mock(Produto.class);
        Categoria categoria = mock(Categoria.class);

        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(categoriaEntityRepository.findById(anyLong())).thenReturn(Optional.of(categoriaEntity));
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);
        when(produto.getCategoria()).thenReturn(categoria);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("Nova Imagem", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/imagem");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(ProdutoException.class, () -> produtoController.atualizarDadosProduto("A", jsonPatch));
    }

    @Test
    void mustLancarExcecaoQuandoONomeForInvalidoOuVazio() throws JsonPointerException {
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        Produto produto = mock(Produto.class);
        Categoria categoria = mock(Categoria.class);

        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(categoriaEntityRepository.findById(anyLong())).thenReturn(Optional.of(categoriaEntity));
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);
        when(produto.getCategoria()).thenReturn(categoria);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/nome");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(ProdutoException.class, () -> produtoController.atualizarDadosProduto("1", jsonPatch));
    }

    @Test
    void mustLancarExcecaoQuandoADescricaoForInvalidoOuVazio() throws JsonPointerException {
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        Produto produto = mock(Produto.class);
        Categoria categoria = mock(Categoria.class);

        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(categoriaEntityRepository.findById(anyLong())).thenReturn(Optional.of(categoriaEntity));
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);
        when(produto.getCategoria()).thenReturn(categoria);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/descricao");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(ProdutoException.class, () -> produtoController.atualizarDadosProduto("1", jsonPatch));
    }

    @Test
    void mustLancarExcecaoQuandoACategoriaForInvalidoOuVazio() throws JsonPointerException {
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        Produto produto = mock(Produto.class);
        Categoria categoria = mock(Categoria.class);

        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(categoriaEntityRepository.findById(anyLong())).thenReturn(Optional.of(categoriaEntity));
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);
        when(produto.getCategoria()).thenReturn(categoria);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/categoria");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(ProdutoException.class, () -> produtoController.atualizarDadosProduto("1", jsonPatch));
    }

    @Test
    void mustLancarExcecaoQuandoOPrecoForInvalidoOuVazio() throws JsonPointerException {
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        Produto produto = mock(Produto.class);
        Categoria categoria = mock(Categoria.class);

        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(categoriaEntityRepository.findById(anyLong())).thenReturn(Optional.of(categoriaEntity));
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);
        when(produto.getCategoria()).thenReturn(categoria);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/preco");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(ProdutoException.class, () -> produtoController.atualizarDadosProduto("1", jsonPatch));
    }

    @Test
    void mustLancarExcecaoQuandoAImagemForInvalidoOuVazio() throws JsonPointerException {
        ProdutoEntity produtoEntity = mock(ProdutoEntity.class);
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);
        Produto produto = mock(Produto.class);
        Categoria categoria = mock(Categoria.class);

        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(categoriaEntityRepository.findById(anyLong())).thenReturn(Optional.of(categoriaEntity));
        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any())).thenReturn(produto);
        when(produto.getCategoria()).thenReturn(categoria);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/imagem");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(ProdutoException.class, () -> produtoController.atualizarDadosProduto("1", jsonPatch));
    }

    @Test
    void shouldAtualizarProdutoComSucesso() {
        ProdutoEntity produtoEntity = ProdutoEntity.builder().build();

        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(produtoMapper.fromDomainToEntity(any())).thenReturn(produtoEntity);

        ProdutoDTO produtoDTO = new ProdutoDTO(
                null,
                "Novo Nome",
                "Nova Descricao",
                new CategoriaDTO(CategoriaEnum.BEBIDA.getNome()),
                BigDecimal.ONE,
                "Nova Imagem"
        );

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        ResponseEntity<Void> response = produtoController.atualizarProduto("1", produtoDTO);

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void mustLancarProdutoExceptionQuandoAtualizarComIdentificadorInvalido() {
        ProdutoDTO produtoDTO = new ProdutoDTO(
                null,
                "Novo Nome",
                "Nova Descricao",
                new CategoriaDTO(CategoriaEnum.BEBIDA.getNome()),
                BigDecimal.ONE,
                "Nova Imagem"
        );

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(IllegalArgumentException.class, () -> produtoController.atualizarProduto("1A", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuandoAtualizarComIdentificadorInexistente() {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        CategoriaEntity categoriaEntity = mock(CategoriaEntity.class);

        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(produtoMapper.fromDomainToEntity(any())).thenReturn(produtoEntity);
        when(categoriaEntityRepository.findByNome(anyString())).thenReturn(Optional.of(categoriaEntity));

        ProdutoDTO produtoDTO = new ProdutoDTO(
                null,
                "Novo Nome",
                "Nova Descricao",
                new CategoriaDTO(CategoriaEnum.BEBIDA.getNome()),
                BigDecimal.ONE,
                "Nova Imagem"
        );

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(ProdutoException.class, () -> produtoController.atualizarProduto("99", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuandoAtualizarComNomeInvalido() {
        ProdutoDTO produtoDTO = new ProdutoDTO(
                null,
                null,
                "Nova Descricao",
                new CategoriaDTO(CategoriaEnum.BEBIDA.getNome()),
                BigDecimal.ONE,
                "Nova Imagem"
        );

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(IllegalArgumentException.class, () -> produtoController.atualizarProduto("99", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuandoAtualizarComDescricaoInvalida() {
        ProdutoDTO produtoDTO = new ProdutoDTO(
                null,
                "Novo Nome",
                null,
                new CategoriaDTO(CategoriaEnum.BEBIDA.getNome()),
                BigDecimal.ONE,
                "Nova Imagem"
        );

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(IllegalArgumentException.class, () -> produtoController.atualizarProduto("99", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuandoAtualizarComCategoriaInvalida() {
        ProdutoDTO produtoDTO = new ProdutoDTO(
                null,
                "Novo Nome",
                "Nova Descricao",
                null,
                BigDecimal.ONE,
                "Nova Imagem"
        );

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(IllegalArgumentException.class, () -> produtoController.atualizarProduto("99", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuandoAtualizarComPrecoInvalido() {
        ProdutoDTO produtoDTO = new ProdutoDTO(
                null,
                "Novo Nome",
                "Nova Descricao",
                new CategoriaDTO(CategoriaEnum.BEBIDA.getNome()),
                null,
                "Nova Imagem"
        );

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(IllegalArgumentException.class, () -> produtoController.atualizarProduto("99", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuandoAtualizarComImagemInvalida() {
        ProdutoDTO produtoDTO = new ProdutoDTO(
                null,
                "Novo Nome",
                "Nova Descricao",
                new CategoriaDTO(CategoriaEnum.BEBIDA.getNome()),
                BigDecimal.ONE,
                null
        );

        ProdutoController produtoController = new ProdutoController(listarProdutoUseCase, cadastrarProdutoUseCase, deletarProdutoUseCase, atualizarProdutoParcialUseCase, atualizarProdutoUseCase);
        assertThrows(IllegalArgumentException.class, () -> produtoController.atualizarProduto("99", produtoDTO));
    }

    private ProdutoEntity criarProdutoRetorno() {
        return ProdutoEntity.builder().id(1L).build();
    }

    private List<ProdutoEntity> criarListaDeProdutosEntity() {
        return List.of(
                ProdutoEntity.builder().categoriaEntity(CategoriaEntity.builder().id(1L).nome("lanche").build()).build(),
                ProdutoEntity.builder().categoriaEntity(CategoriaEntity.builder().id(1L).nome("lanche").build()).build()
        );
    }

    private List<Produto> criarListaDeProdutos() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Nome");
        produto.setDescricao("Descricao");
        produto.setCategoria(new Categoria("lanche", "lanches"));
        produto.setPreco(BigDecimal.ZERO);
        produto.setImagem("Imagem");

        return List.of(
                produto,
                produto
        );
    }

}

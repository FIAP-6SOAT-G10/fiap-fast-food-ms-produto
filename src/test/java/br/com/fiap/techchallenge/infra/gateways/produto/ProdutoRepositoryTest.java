package br.com.fiap.techchallenge.infra.gateways.produto;

import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.infra.persistence.CategoriaEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.ProdutoEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;
import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoRepositoryTest {

    @Mock
    private ProdutoEntityRepository produtoEntityRepository;

    @Mock
    private CategoriaEntityRepository categoriaEntityRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @InjectMocks
    private ProdutoRepository produtoRepository;

    private ProdutoEntity produtoEntity;
    private Produto produto;
    private CategoriaEntity categoriaEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(1L);
        categoriaEntity.setNome(CategoriaEnum.LANCHE.name());

        produtoEntity = new ProdutoEntity();
        produtoEntity.setId(1L);
        produtoEntity.setNome("Produto Teste");
        produtoEntity.setDescricao("Descrição do produto");
        produtoEntity.setPreco(BigDecimal.valueOf(100));
        produtoEntity.setCategoriaEntity(categoriaEntity);

        produto = new Produto("Produto Teste", "Descrição do produto", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(100), "imagem.jpg");

    }

    @Test
    void listarProdutosTest() {
        when(produtoEntityRepository.findAll()).thenReturn(Collections.singletonList(produtoEntity));
        when(produtoMapper.fromListEntityToListDomain(anyList())).thenReturn(Collections.singletonList(produto));
        List<Produto> produtos = produtoRepository.listarProdutos(null, null, null);
        assertNotNull(produtos);
        assertEquals(1, produtos.size());
        assertEquals(produto.getNome(), produtos.get(0).getNome());
        verify(produtoEntityRepository, times(1)).findAll();
    }

    @Test
    void listarProdutosPorCategoriaTest() {
        // Arrange
        when(produtoEntityRepository.findAllByCategoriaEntityId(anyLong())).thenReturn(Optional.of(Collections.singletonList(produtoEntity)));
        when(produtoMapper.fromListEntityToListDomain(anyList())).thenReturn(Collections.singletonList(produto));
        List<Produto> produtos = produtoRepository.listarProdutosPorCategoria(CategoriaEnum.LANCHE);
        assertNotNull(produtos);
        assertEquals(1, produtos.size());
        assertEquals(produto.getNome(), produtos.get(0).getNome());
        verify(produtoEntityRepository, times(1)).findAllByCategoriaEntityId(anyLong());
    }

    @Test
    void criarProdutoTest() {
        when(categoriaEntityRepository.findByNome(anyString())).thenReturn(Optional.of(categoriaEntity));
        when(produtoEntityRepository.findByNome(anyString())).thenReturn(Optional.empty());
        when(produtoMapper.fromDomainToEntity(any(Produto.class))).thenReturn(produtoEntity);
        when(produtoEntityRepository.saveAndFlush(any(ProdutoEntity.class))).thenReturn(produtoEntity);
        when(produtoMapper.fromEntityToDomain(any(ProdutoEntity.class))).thenReturn(produto);
        Produto createdProduto = produtoRepository.criarProduto(produto);
        assertNotNull(createdProduto);
        assertEquals(produto.getNome(), createdProduto.getNome());
        verify(produtoEntityRepository, times(1)).saveAndFlush(any(ProdutoEntity.class));
    }

    @Test
    void deletarProdutoTest() {
        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        produtoRepository.deletarProduto(1L);
        verify(produtoEntityRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void atualizarProdutoTest() {
        ProdutoEntity updatedProdutoEntity = new ProdutoEntity();
        updatedProdutoEntity.setNome("Updated Produto");
        updatedProdutoEntity.setDescricao("Updated Descrição");
        updatedProdutoEntity.setPreco(BigDecimal.valueOf(200));
        updatedProdutoEntity.setCategoriaEntity(categoriaEntity);

        when(produtoMapper.fromDomainToEntity(any(Produto.class))).thenReturn(produtoEntity);
        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(categoriaEntityRepository.findByNome(anyString())).thenReturn(Optional.of(categoriaEntity));
        when(produtoEntityRepository.saveAndFlush(any(ProdutoEntity.class))).thenReturn(updatedProdutoEntity);

        when(produtoMapper.fromEntityToDomain(updatedProdutoEntity)).thenReturn(produto);
        Produto updatedProduto = produtoRepository.atualizarProduto(1L, produto);
        assertNotNull(updatedProduto);
        assertEquals("Produto Teste", updatedProduto.getNome());
        verify(produtoEntityRepository, times(1)).saveAndFlush(any(ProdutoEntity.class));
    }

    @Test
    void atualizarDadosProdutoTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String patchJson = "[ {\"op\": \"replace\", \"path\": \"/nome\", \"value\": \"Produto 1\"} ]";
        JsonPatch validPatch = objectMapper.readValue(patchJson, JsonPatch.class);

        Produto produtoUpdate = new Produto("Updated Produto", "Updated Descrição", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(200), "imagem.jpg");
        ProdutoEntity updatedProdutoEntity = new ProdutoEntity();
        updatedProdutoEntity.setNome("Updated Produto");
        updatedProdutoEntity.setDescricao("Updated Descrição");
        updatedProdutoEntity.setPreco(BigDecimal.valueOf(200));
        updatedProdutoEntity.setCategoriaEntity(categoriaEntity);

        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(produtoEntityRepository.saveAndFlush(any(ProdutoEntity.class))).thenReturn(updatedProdutoEntity);
        when(produtoMapper.fromEntityToDomain(any(ProdutoEntity.class))).thenReturn(produtoUpdate);
        Produto updatedProduto = produtoRepository.atualizarDadosProduto(1L, validPatch);
        assertNotNull(updatedProduto);
        assertEquals("Updated Produto", updatedProduto.getNome());
        assertEquals(BigDecimal.valueOf(200), updatedProduto.getPreco());
        verify(produtoEntityRepository, times(1)).saveAndFlush(any(ProdutoEntity.class));
    }

    @Test
    void findByIdTest() {
        // Arrange
        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(produtoMapper.fromEntityToDomain(any(ProdutoEntity.class))).thenReturn(produto);
        Produto foundProduto = produtoRepository.findById(1L);
        assertNotNull(foundProduto);
        assertEquals(produto.getNome(), foundProduto.getNome());
        verify(produtoEntityRepository, times(1)).findById(anyLong());
    }

    @Test
    void findByIdProdutoNotFoundTest() {
        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.empty());
        ProdutoException exception = assertThrows(ProdutoException.class, () -> {
            produtoRepository.findById(1L);
        });
        assertEquals(ErrosEnum.PRODUTO_NAO_ENCONTRADO.getMessage(), exception.getMessage());
    }

    @Test
    void listarProdutosPorCategoriaNaoEncontradaTest() {
        when(produtoEntityRepository.findAllByCategoriaEntityId(anyLong())).thenReturn(Optional.empty());
        ProdutoException exception = assertThrows(ProdutoException.class, () -> {
            produtoRepository.listarProdutosPorCategoria(CategoriaEnum.LANCHE);
        });
        assertEquals(ErrosEnum.PRODUTO_CATEGORIA_NAO_ENCONTRADO.getMessage(), exception.getMessage());
    }

    @Test
    void criarProdutoDeveraRetornarUmExcecaoDeQueOProdutoJaExiste() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(1L);
        categoriaEntity.setNome(CategoriaEnum.LANCHE.name());

        Produto produtoCriar = new Produto("Big Fiap", "Nova Descrição", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(200), "imagem.jpg");

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(1L);
        produtoEntity.setNome("Produto Teste");
        produtoEntity.setDescricao("Descrição do produto");
        produtoEntity.setPreco(BigDecimal.valueOf(100));
        produtoEntity.setCategoriaEntity(categoriaEntity);

        when(categoriaEntityRepository.findByNome("LANCHE")).thenReturn(Optional.of(categoriaEntity));
        when(produtoEntityRepository.findByNome("Big Fiap")).thenReturn(Optional.of(produtoEntity));

        ProdutoException thrown = assertThrows(ProdutoException.class, () -> {
            produtoRepository.criarProduto(produtoCriar);
        });

        assertEquals(ErrosEnum.PRODUTO_JA_EXISTE.getMessage(), thrown.getError().getMessage());
        verify(categoriaEntityRepository, times(1)).findByNome("LANCHE");
        verify(produtoEntityRepository, times(1)).findByNome("Big Fiap");
    }

    @Test
    void testaExcecaoAoDeletarUmProdutoComIdentificadorInvalido(){
        when(produtoEntityRepository.findById(1l)).thenReturn(Optional.empty());
        ProdutoException thrown = assertThrows(ProdutoException.class, () -> {
            produtoRepository.deletarProduto(1l);
        });

        assertEquals(ErrosEnum.PRODUTO_CODIGO_IDENTIFICADOR_INVALIDO.getMessage(), thrown.getError().getMessage());
        verify(produtoEntityRepository, times(1)).findById(1l);
    }

    @Test
    void testaListarTodosOsProdutosComNomeDescricaoEPreco(){
        String nome = "nome";
        String descricao = "descricao";
        BigDecimal preco = new BigDecimal("10");
        Produto produtoCriar = new Produto("Big Fiap", "Nova Descrição", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(200), "imagem.jpg");
        List<ProdutoEntity> produtoEntityList = List.of(produtoEntity);
        List<Produto> produtoDomain = List.of(produtoCriar);

        when(produtoEntityRepository.findByNomeOrDescricaoOrPreco(nome,descricao,preco)).thenReturn(Optional.of(produtoEntityList));
        when(produtoMapper.fromListEntityToListDomain(produtoEntityList)).thenReturn(produtoDomain);

        produtoRepository.listarProdutos(nome, descricao, preco);
        verify(produtoEntityRepository, times(1)).findByNomeOrDescricaoOrPreco(nome,descricao,preco);
    }

    @Test
    void criarProdutoDeveraRetornarSeACategoriaNaoExists() {

        when(categoriaEntityRepository.findByNome("LANCHE")).thenReturn(Optional.empty());
        Produto produtoCriar = new Produto("Big Fiap", "Nova Descrição", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(200), "imagem.jpg");

        assertNull(produtoRepository.criarProduto(produtoCriar));
        verify(categoriaEntityRepository, times(1)).findByNome("LANCHE");
    }

}

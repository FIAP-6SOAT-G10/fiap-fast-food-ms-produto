package br.com.fiap.techchallenge.infra.controllers.produto;

import br.com.fiap.techchallenge.application.usecases.produto.*;
import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.infra.controllers.categoria.dtos.CategoriaDTO;
import br.com.fiap.techchallenge.infra.controllers.produto.dtos.ProdutoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProdutoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ListarProdutoUseCase listarProdutoUseCase;

    @Mock
    private CadastrarProdutoUseCase cadastrarProdutoUseCase;

    @Mock
    private DeletarProdutoUseCase deletarProdutoUseCase;

    @Mock
    private AtualizarProdutoParcialUseCase atualizarProdutoParcialUseCase;

    @Mock
    private AtualizarProdutoUseCase atualizarProdutoUseCase;

    @InjectMocks
    private ProdutoController produtoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
    }

    @Test
    void listarProdutos_ReturnsOk_WhenProductsExist() throws Exception {
        Produto produto = new Produto("Produto 1", "Descrição do produto", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(19.99), "imagem.jpg");
        List<Produto> produtos = List.of(produto);
        when(listarProdutoUseCase.listarProdutos(null, null, null)).thenReturn(produtos);
        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nome").value("Produto 1"))
                .andExpect(jsonPath("$[0].descricao").value("Descrição do produto"));
    }

    @Test
    void listarProdutos_ReturnsNoContent_WhenNoProductsExist() throws Exception {
        when(listarProdutoUseCase.listarProdutos(null, null, null)).thenReturn(List.of());
        mockMvc.perform(get("/produtos"))
                .andExpect(status().isNotFound());
    }

    @Test
    void cadastrarProduto_ReturnsCreated_WhenProductIsSuccessfullyCreated() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO(1l,"Produto Novo", "Descrição Produto Novo", new CategoriaDTO("LANCHE", "Lanches"), BigDecimal.valueOf(29.99), "imagem.jpg");
        Produto produto = new Produto("Produto Novo", "Descrição Produto Novo", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(29.99), "imagem.jpg");
        when(cadastrarProdutoUseCase.criarProduto(any(Produto.class))).thenReturn(produto);
        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(produtoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void deletarProduto_ReturnsOk_WhenProductIsDeleted() throws Exception {
        String productId = "123";
        doNothing().when(deletarProdutoUseCase).deletarProduto(productId);
        mockMvc.perform(delete("/produtos/{id}", productId))
                .andExpect(status().isOk());
        verify(deletarProdutoUseCase, times(1)).deletarProduto(productId);
    }

    @Test
    void atualizarProduto_ReturnsNoContent_WhenProductIsUpdated() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO(1L, "Produto Atualizado", "Descrição Atualizada", new CategoriaDTO("LANCHE", "Lanches"), BigDecimal.valueOf(39.99), "imagem.jpg");
        Produto produto = new Produto("Produto Atualizado", "Descrição Atualizada", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(39.99), "imagem.jpg");
        when(atualizarProdutoUseCase.atualizarProduto(anyString(), any(Produto.class))).thenReturn(produto);
        mockMvc.perform(put("/produtos/{id}", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(produtoDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarProdutosPorCategoria_ReturnsOk_WhenProductsExist() throws Exception {
        Produto produto = new Produto("Produto 1", "Descrição Produto 1", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(19.99), "imagem.jpg");
        List<Produto> produtos = List.of(produto);
        when(listarProdutoUseCase.listarProdutosPorCategoria(any())).thenReturn(produtos);
        mockMvc.perform(get("/produtos/categoria/{categoria}", "lanche"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nome").value("Produto 1"));
    }

    @Test
    void buscarProdutosPorId() throws Exception {
        Produto produto = new Produto("Produto 1", "Descrição Produto 1", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(19.99), "imagem.jpg");
        when(listarProdutoUseCase.buscarProdutoPorId(any())).thenReturn(produto);
        mockMvc.perform(get("/produtos/{id}", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Produto 1"));
    }
}

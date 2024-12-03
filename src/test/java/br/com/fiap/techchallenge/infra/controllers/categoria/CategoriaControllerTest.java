package br.com.fiap.techchallenge.infra.controllers.categoria;

import br.com.fiap.techchallenge.application.usecases.categoria.ListarCategoriasUseCase;
import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CategoriaControllerTest {

    @Mock
    private ListarCategoriasUseCase categoriasUseCase;

    @InjectMocks
    private CategoriaController categoriaController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoriaController).build();
    }

    @Test
    void testListarTodasCategorias_Success() throws Exception {
        Categoria categoria1 = new Categoria("LANCHE", "Lanches");
        Categoria categoria2 = new Categoria("BEBIDA", "Bebidas");
        when(categoriasUseCase.listarCategorias()).thenReturn(Arrays.asList(categoria1, categoria2));
        mockMvc.perform(get("/categorias")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].nome").value("LANCHE"))
                .andExpect(jsonPath("$[0].descricao").value("Lanches"))
                .andExpect(jsonPath("$[1].nome").value("BEBIDA"))
                .andExpect(jsonPath("$[1].descricao").value("Bebidas"));
    }

    @Test
    void testListarTodasCategorias_NoContent() throws Exception {
        when(categoriasUseCase.listarCategorias()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/categorias")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

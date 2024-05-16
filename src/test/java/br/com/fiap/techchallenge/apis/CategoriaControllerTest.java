package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.categoria.GetCategoriaAdapter;
import br.com.fiap.techchallenge.domain.model.mapper.categoria.CategoriaMapper;
import br.com.fiap.techchallenge.domain.valueobjects.CategoriaDTO;
import br.com.fiap.techchallenge.infra.repositories.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CategoriaControllerTest {

    @Mock
    private GetCategoriaAdapter getCategoriaAdapter;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private CategoriaMapper categoriaMapper;

    @InjectMocks
    private CategoriaController categoriaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnNoContentWhenListarTodasCategoriasAndNoClientesExist() {
        when(getCategoriaAdapter.listarCategorias()).thenReturn(Collections.emptyList());
        ResponseEntity<List<CategoriaDTO>> response = categoriaController.listarTodasCategorias();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldReturnOkWhenListarTodasCategoriasAndCategoriasExist() {
        List<CategoriaDTO> categorias = Collections.singletonList(new CategoriaDTO());
        when(getCategoriaAdapter.listarCategorias()).thenReturn(categorias);
        ResponseEntity<List<CategoriaDTO>> response = categoriaController.listarTodasCategorias();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
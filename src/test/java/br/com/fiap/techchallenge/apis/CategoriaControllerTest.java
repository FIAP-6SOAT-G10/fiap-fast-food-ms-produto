package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.categoria.GetCategoriaAdapter;
import br.com.fiap.techchallenge.domain.entities.Categoria;
import br.com.fiap.techchallenge.domain.model.mapper.categoria.CategoriaMapper;
import br.com.fiap.techchallenge.domain.valueobjects.CategoriaDTO;
import br.com.fiap.techchallenge.infra.repositories.CategoriaRepository;
import br.com.fiap.techchallenge.ports.categoria.GetCategoriaOutboundPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoriaControllerTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Test
    void shouldReturnNoContentWhenListarTodasCategoriasAndNoClientesExist() {
        when(categoriaRepository.findAll()).thenReturn(Collections.emptyList());

        CategoriaController categoriaController = new CategoriaController(categoriaRepository, categoriaMapper);
        ResponseEntity<List<CategoriaDTO>> response = categoriaController.listarTodasCategorias();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldReturnOkWhenListarTodasCategoriasAndCategoriasExist() {
        List<Categoria> categorias = Collections.singletonList(new Categoria());
        when(categoriaRepository.findAll()).thenReturn(categorias);

        CategoriaController categoriaController = new CategoriaController(categoriaRepository, categoriaMapper);
        ResponseEntity<List<CategoriaDTO>> response = categoriaController.listarTodasCategorias();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
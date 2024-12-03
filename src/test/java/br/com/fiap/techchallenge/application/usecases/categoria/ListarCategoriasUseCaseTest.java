package br.com.fiap.techchallenge.application.usecases.categoria;

import br.com.fiap.techchallenge.application.gateways.ICategoriaRepository;
import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarCategoriasUseCaseTest {

    @Mock
    private ICategoriaRepository categoriaRepository;

    @InjectMocks
    private ListarCategoriasUseCase listarCategoriasUseCase;

    private Categoria categoria1;
    private Categoria categoria2;

    @BeforeEach
    public void setUp() {
        categoria1 = new Categoria("LANCHE", "Lanches");
        categoria2 = new Categoria("BEBIDA", "Bebidas");
    }

    @Test
    void testListarCategorias_ReturnsCategories() {
        when(categoriaRepository.listarCategorias()).thenReturn(Arrays.asList(categoria1, categoria2));
        List<Categoria> categorias = listarCategoriasUseCase.listarCategorias();
        assertNotNull(categorias);
        assertEquals(2, categorias.size());
        assertEquals("LANCHE", categorias.get(0).getNome());
        assertEquals("BEBIDA", categorias.get(1).getNome());
        verify(categoriaRepository, times(1)).listarCategorias();
    }

    @Test
    void testListarCategorias_ReturnsEmptyList() {
        when(categoriaRepository.listarCategorias()).thenReturn(Collections.emptyList());
        List<Categoria> categorias = listarCategoriasUseCase.listarCategorias();
        assertNotNull(categorias);
        assertTrue(categorias.isEmpty());
        verify(categoriaRepository, times(1)).listarCategorias();
    }

    @Test
    void testListarCategorias_RepositoryThrowsException() {
        when(categoriaRepository.listarCategorias()).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> listarCategoriasUseCase.listarCategorias());
        verify(categoriaRepository, times(1)).listarCategorias();
    }
}

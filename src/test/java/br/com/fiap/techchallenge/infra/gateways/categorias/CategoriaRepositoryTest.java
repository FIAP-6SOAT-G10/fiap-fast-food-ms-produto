package br.com.fiap.techchallenge.infra.gateways.categorias;


import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import br.com.fiap.techchallenge.infra.persistence.CategoriaEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CategoriaRepositoryTest {

    @Mock
    private CategoriaEntityRepository categoriaEntityRepository;

    private CategoriaRepository categoriaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        categoriaRepository = new CategoriaRepository(categoriaEntityRepository);
    }

    @Test
    public void testListarCategorias() {
        CategoriaEntity categoriaEntity1 = new CategoriaEntity(1L, "LANCHE", "Lanches", null);
        CategoriaEntity categoriaEntity2 = new CategoriaEntity(2L, "BEBIDA", "Bebidas", null);
        when(categoriaEntityRepository.findAll()).thenReturn(Arrays.asList(categoriaEntity1, categoriaEntity2));
        List<Categoria> categorias = categoriaRepository.listarCategorias();
        assertEquals(2, categorias.size());
        assertEquals("LANCHE", categorias.get(0).getNome());
        assertEquals("Lanches", categorias.get(0).getDescricao());

        assertEquals("BEBIDA", categorias.get(1).getNome());
        assertEquals("Bebidas", categorias.get(1).getDescricao());
    }
}

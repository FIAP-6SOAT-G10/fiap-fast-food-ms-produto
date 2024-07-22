package br.com.fiap.techchallenge.infra.controllers.categoria;

import br.com.fiap.techchallenge.application.usecases.categoria.ListarCategoriasUseCase;
import br.com.fiap.techchallenge.infra.gateways.categorias.CategoriaRepository;
import br.com.fiap.techchallenge.infra.persistence.CategoriaEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class CategoriaControllerTest {

    ListarCategoriasUseCase categoriasUseCase;
    @Mock
    CategoriaEntityRepository categoriaEntityRepository;

    @BeforeEach
    void setup() {
        CategoriaRepository categoriaRepository = new CategoriaRepository(categoriaEntityRepository);
        categoriasUseCase = new ListarCategoriasUseCase(categoriaRepository);
    }

    @Test
    void itShouldReturnTodosCategorias() {
        ArrayList<CategoriaEntity> categeorias = new ArrayList<>();
        categeorias.add(new CategoriaEntity(1L));
        when(categoriaEntityRepository.findAll()).thenReturn(categeorias);
        CategoriaController controller = new CategoriaController(categoriasUseCase);
        ResponseEntity<List<CategoriaDTO>> responseCategorias = controller.listarTodasCategorias();
        if (!responseCategorias.hasBody()) {
            Assertions.fail();
        }
        if (responseCategorias.getBody().isEmpty()) {
            Assertions.fail();
        }
        Assertions.assertEquals(1, responseCategorias.getBody().size());
    }


    @Test
    void itShouldReturnCategoriaComValoresCorretos() {
        ArrayList<CategoriaEntity> categeorias = new ArrayList<>();
        Long id = 1L;
        String nome = "Gabs";
        String descricao = "Descricao";
        categeorias.add(new CategoriaEntity(id, nome, descricao, null));
        when(categoriaEntityRepository.findAll()).thenReturn(categeorias);
        CategoriaController controller = new CategoriaController(categoriasUseCase);
        ResponseEntity<List<CategoriaDTO>> responseCategorias = controller.listarTodasCategorias();
        if (!responseCategorias.hasBody()) {
            Assertions.fail();
        }
        if (responseCategorias.getBody().isEmpty()) {
            Assertions.fail();
        }
        Assertions.assertEquals(nome, responseCategorias.getBody().get(0).getNome());
        Assertions.assertEquals(descricao, responseCategorias.getBody().get(0).getDescricao());
    }

}
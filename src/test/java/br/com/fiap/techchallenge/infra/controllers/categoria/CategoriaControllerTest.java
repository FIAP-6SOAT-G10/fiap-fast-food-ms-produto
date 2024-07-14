package br.com.fiap.techchallenge.infra.controllers.categoria;

import br.com.fiap.techchallenge.application.usecases.categoria.ListarCategoriasUseCase;
import br.com.fiap.techchallenge.infra.controllers.categoria.CategoriaController;
import br.com.fiap.techchallenge.infra.dto.CategoriaDTO;
import br.com.fiap.techchallenge.infra.gateways.categorias.CategoriaRepository;
import br.com.fiap.techchallenge.infra.persistence.CategoriaEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class CategoriaEntityControllerTest {

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
        System.out.println(responseCategorias);
    }

}
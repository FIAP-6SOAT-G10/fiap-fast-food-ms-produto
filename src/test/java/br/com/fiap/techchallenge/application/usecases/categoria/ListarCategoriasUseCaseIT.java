package br.com.fiap.techchallenge.application.usecases.categoria;

import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("integration-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
class ListarCategoriasUseCaseIT {

    @Autowired
    ListarCategoriasUseCase listarCategoriasUseCase;

    @Test
    void deveRetornarTodasAsCategoriasCadastradas() {
        List<Categoria> categorias = listarCategoriasUseCase.listarCategorias();
        assertNotNull(categorias);
        assertEquals(4, categorias.size());
        assertEquals("LANCHE", categorias.get(0).getNome());
        assertEquals("BEBIDA", categorias.get(1).getNome());
    }
}

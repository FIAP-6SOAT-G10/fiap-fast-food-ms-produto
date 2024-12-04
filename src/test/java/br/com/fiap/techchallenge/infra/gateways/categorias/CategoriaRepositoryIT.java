package br.com.fiap.techchallenge.infra.gateways.categorias;


import br.com.fiap.techchallenge.infra.persistence.CategoriaEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;
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

@SpringBootTest
@Transactional
@ActiveProfiles("integration-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
class CategoriaRepositoryIT {

    @Autowired
    private CategoriaEntityRepository categoriaEntityRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    void testListarCategorias() {
        List<CategoriaEntity> allCategories = categoriaEntityRepository.findAll();
        assertEquals(4, allCategories.size());
    }
}

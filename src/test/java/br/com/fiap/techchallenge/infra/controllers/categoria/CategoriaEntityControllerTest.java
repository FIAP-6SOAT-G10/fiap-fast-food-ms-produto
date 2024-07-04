//package br.com.fiap.techchallenge.apis.categoria;
//
//import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;
//import br.com.fiap.techchallenge.naousar.apis.CategoriaController;
//import br.com.fiap.techchallenge.infra.mapper.categoria.CategoriaMapper;
//import br.com.fiap.techchallenge.naousar.domain.valueobjects.CategoriaDTO;
//import br.com.fiap.techchallenge.infra.persistence.CategoriaRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class CategoriaEntityControllerTest {
//
//    @Mock
//    private CategoriaRepository categoriaRepository;
//
//    @Autowired
//    private CategoriaMapper categoriaMapper;
//
//    @Test
//    void shouldReturnNoContentWhenListarTodasCategoriasAndNoClientesExist() {
//        when(categoriaRepository.findAll()).thenReturn(Collections.emptyList());
//
//        CategoriaController categoriaController = new CategoriaController(categoriaRepository, categoriaMapper);
//        ResponseEntity<List<CategoriaDTO>> response = categoriaController.listarTodasCategorias();
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//    }
//
//    @Test
//    void shouldReturnOkWhenListarTodasCategoriasAndCategoriasExist() {
//        List<CategoriaEntity> categoriaEntities = Collections.singletonList(new CategoriaEntity());
//        when(categoriaRepository.findAll()).thenReturn(categoriaEntities);
//
//        CategoriaController categoriaController = new CategoriaController(categoriaRepository, categoriaMapper);
//        ResponseEntity<List<CategoriaDTO>> response = categoriaController.listarTodasCategorias();
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//}
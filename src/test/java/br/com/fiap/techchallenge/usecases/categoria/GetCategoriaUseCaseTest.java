package br.com.fiap.techchallenge.usecases.categoria;

import br.com.fiap.techchallenge.domain.usecases.categoria.GetCategoriaUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.CategoriaDTO;
import br.com.fiap.techchallenge.ports.categoria.GetCategoriaOutboundPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCategoriaUseCaseTest {

    @Mock
    private GetCategoriaOutboundPort port;

    private GetCategoriaUseCase getCategoriaUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getCategoriaUseCase = new GetCategoriaUseCase(port);
    }

    @Test
    void ItShouldListarTodasCategorias() {
        List<CategoriaDTO> categorias = new ArrayList<>();
        categorias.add(CategoriaDTO.builder().nome("LANCHE").descricao("Lanche").build());

        when(port.listarCategorias()).thenReturn(categorias);

        List<CategoriaDTO> result = getCategoriaUseCase.listarCategorias();

        assertEquals(1, result.size());
        assertEquals(result.get(0).getNome(), "LANCHE");
        assertEquals(result.get(0).getDescricao(), "Lanche");
    }

}

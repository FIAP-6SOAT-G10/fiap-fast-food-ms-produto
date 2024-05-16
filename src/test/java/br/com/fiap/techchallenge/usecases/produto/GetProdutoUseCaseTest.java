package br.com.fiap.techchallenge.usecases.produto;

import br.com.fiap.techchallenge.adapters.produto.GetProdutoAdapter;
import br.com.fiap.techchallenge.domain.entities.Categoria;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.domain.usecases.produto.GetProdutoUseCase;
import br.com.fiap.techchallenge.infra.exception.CategoriaException;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import br.com.fiap.techchallenge.ports.produto.GetProdutoOutboundPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class GetProdutoUseCaseTest {

    @Mock
    ProdutoRepository produtoRepository;

    @Autowired
    ProdutoMapper produtoMapper;

    @Test
    void shouldListarProdutosPorCategoriaInformada() {
        Optional<List<Produto>> optionalProdutos = Optional.of(criarListaDeProdutos());
        when(produtoRepository.findAllByCategoriaId(anyInt())).thenReturn(optionalProdutos);

        GetProdutoOutboundPort getProdutoAdapter = new GetProdutoAdapter(produtoRepository, produtoMapper);
        GetProdutoUseCase getProdutoUseCase = new GetProdutoUseCase(getProdutoAdapter);
        List<Produto> lanches = getProdutoUseCase.listarProdutosPorCategoria("lanche");

        assertEquals(2, lanches.size());
    }

    @Test
    void mustLancarCategoriaExceptionQuandoACategoriaNaoForValida() {
        GetProdutoOutboundPort getProdutoAdapter = new GetProdutoAdapter(produtoRepository, produtoMapper);
        GetProdutoUseCase getProdutoUseCase = new GetProdutoUseCase(getProdutoAdapter);

        assertThrows(CategoriaException.class, () -> getProdutoUseCase.listarProdutosPorCategoria("inexistente"));
    }

    private List<Produto> criarListaDeProdutos() {
        return List.of(
                Produto.builder().categoria(Categoria.builder().id(1L).nome("lanche").build()).build(),
                Produto.builder().categoria(Categoria.builder().id(1L).nome("lanche").build()).build()
        );
    }

}

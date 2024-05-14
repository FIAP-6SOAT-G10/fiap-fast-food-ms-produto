package br.com.fiap.techchallenge.usecases.produtos;

import br.com.fiap.techchallenge.adapters.produtos.PostProdutoAdapter;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.enums.CategoriaEnum;
import br.com.fiap.techchallenge.domain.model.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.domain.usecases.produtos.PostProdutoUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.exception.CategoriaException;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostProdutoUseCaseTest {

    @Mock
    ProdutoMapper produtoMapper;

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    PostProdutoAdapter adapter;

    @Test
    void shouldCadastrarProdutoComSucesso() throws BaseException {
        when(adapter.criarProduto(any())).thenReturn(criarProdutoRetorno());

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Grande Lanche");
        produtoDTO.setDescricao("Um grande lanche");
        produtoDTO.setCategoria(CategoriaEnum.LANCHE);
        produtoDTO.setPreco(BigDecimal.ONE);
        produtoDTO.setImagem("Imagem");

        PostProdutoUseCase postProdutoUseCase = new PostProdutoUseCase(adapter);
        Produto produto = postProdutoUseCase.criarProduto(produtoDTO);

        assertEquals(1L, produto.getId());
    }

    @Test
    void mustLancarProdutoExceptionAoCadastrarProdutoSemNome() {
        when(adapter.criarProduto(any())).thenReturn(criarProdutoRetorno());

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Grande Lanche");
        produtoDTO.setCategoria(CategoriaEnum.LANCHE);
        produtoDTO.setPreco(BigDecimal.ONE);
        produtoDTO.setImagem("Imagem");

        PostProdutoUseCase postProdutoUseCase = new PostProdutoUseCase(adapter);
        assertThrows(ProdutoException.class, () -> postProdutoUseCase.criarProduto(produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionAoCadastrarProdutoSemCategoria() {
        when(adapter.criarProduto(any())).thenReturn(criarProdutoRetorno());

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Grande Lanche");
        produtoDTO.setDescricao("Um grande lanche");
        produtoDTO.setPreco(BigDecimal.ONE);
        produtoDTO.setImagem("Imagem");

        PostProdutoUseCase postProdutoUseCase = new PostProdutoUseCase(adapter);
        assertThrows(CategoriaException.class, () -> postProdutoUseCase.criarProduto(produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionAoCadastrarProdutoSemPreco() {
        when(adapter.criarProduto(any())).thenReturn(criarProdutoRetorno());

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Grande Lanche");
        produtoDTO.setDescricao("Um grande lanche");
        produtoDTO.setCategoria(CategoriaEnum.LANCHE);
        produtoDTO.setImagem("Imagem");

        PostProdutoUseCase postProdutoUseCase = new PostProdutoUseCase(adapter);
        assertThrows(ProdutoException.class, () -> postProdutoUseCase.criarProduto(produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionAoCadastrarProdutoSemImagem() {
        when(adapter.criarProduto(any())).thenReturn(criarProdutoRetorno());

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Grande Lanche");
        produtoDTO.setDescricao("Um grande lanche");
        produtoDTO.setCategoria(CategoriaEnum.LANCHE);
        produtoDTO.setPreco(BigDecimal.ONE);

        PostProdutoUseCase postProdutoUseCase = new PostProdutoUseCase(adapter);
        assertThrows(ProdutoException.class, () -> postProdutoUseCase.criarProduto(produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionAoCadastrarProdutoSemDescricao() {
        when(adapter.criarProduto(any())).thenReturn(criarProdutoRetorno());

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setDescricao("Um grande lanche");
        produtoDTO.setCategoria(CategoriaEnum.LANCHE);
        produtoDTO.setPreco(BigDecimal.ONE);
        produtoDTO.setImagem("Imagem");

        PostProdutoUseCase postProdutoUseCase = new PostProdutoUseCase(adapter);
        assertThrows(ProdutoException.class, () -> postProdutoUseCase.criarProduto(produtoDTO));
    }

    private Produto criarProdutoRetorno() {
        return Produto.builder().id(1L).build();
    }

}

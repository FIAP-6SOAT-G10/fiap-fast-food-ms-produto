package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.produtos.PostProdutoAdapter;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.enums.CategoriaEnum;
import br.com.fiap.techchallenge.domain.model.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.repositories.CategoriaRepository;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProdutoPutControllerTest {

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    PostProdutoAdapter postProdutoAdapter;

    @Mock
    CategoriaRepository categoriaRepository;

    @Mock
    ProdutoMapper produtoMapper;

    @Test
    void shouldAtualizarProdutoComSucesso() {
        Produto produto = Produto.builder().build();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(produtoMapper.fromDTOToEntity(any())).thenReturn(produto);

        ProdutoDTO produtoDTO = ProdutoDTO.builder()
                .nome("Novo Nome")
                .descricao("Nova Descricao")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.ONE)
                .imagem("Nova Imagem")
                .build();

        ProdutoController produtoController = new ProdutoController(categoriaRepository, produtoRepository, produtoMapper);
        ResponseEntity<Void> response = produtoController.atualizarProduto("1", produtoDTO);

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void mustLancarProdutoExceptionQuandoAtualizarComIdentificadorInvalido() {
        ProdutoDTO produtoDTO = ProdutoDTO.builder()
                .nome("Novo Nome")
                .descricao("Nova Descricao")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.ONE)
                .imagem("Nova Imagem")
                .build();

        ProdutoController produtoController = new ProdutoController(categoriaRepository, produtoRepository, produtoMapper);
        assertThrows(ProdutoException.class, () -> produtoController.atualizarProduto("1A", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuandoAtualizarComIdentificadorInexistente() {
        ProdutoRepository mockProdutoRepository = mock(ProdutoRepository.class);

        Produto produto = new Produto();
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));

        ProdutoDTO produtoDTO = ProdutoDTO.builder()
                .nome("Novo Nome")
                .descricao("Nova Descricao")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.ONE)
                .imagem("Nova Imagem")
                .build();

        ProdutoController produtoController = new ProdutoController(categoriaRepository, mockProdutoRepository, produtoMapper);
        assertThrows(ProdutoException.class, () -> produtoController.atualizarProduto("99", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuandoAtualizarComNomeInvalido() {
        ProdutoDTO produtoDTO = ProdutoDTO.builder()
                .descricao("Nova Descricao")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.ONE)
                .imagem("Nova Imagem")
                .build();

        ProdutoController produtoController = new ProdutoController(categoriaRepository, produtoRepository, produtoMapper);
        assertThrows(ProdutoException.class, () -> produtoController.atualizarProduto("99", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuandoAtualizarComDescricaoInvalida() {
        ProdutoDTO produtoDTO = ProdutoDTO.builder()
                .nome("Novo Nome")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.ONE)
                .imagem("Nova Imagem")
                .build();

        ProdutoController produtoController = new ProdutoController(categoriaRepository, produtoRepository, produtoMapper);
        assertThrows(ProdutoException.class, () -> produtoController.atualizarProduto("99", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuandoAtualizarComCategoriaInvalida() {
        ProdutoDTO produtoDTO = ProdutoDTO.builder()
                .nome("Novo Nome")
                .descricao("Nova Descricao")
                .imagem("Nova Imagem")
                .build();

        ProdutoController produtoController = new ProdutoController(categoriaRepository, produtoRepository, produtoMapper);
        assertThrows(ProdutoException.class, () -> produtoController.atualizarProduto("99", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuandoAtualizarComPrecoInvalido() {
        ProdutoDTO produtoDTO = ProdutoDTO.builder()
                .nome("Novo Nome")
                .descricao("Nova Descricao")
                .preco(BigDecimal.ZERO)
                .categoria(CategoriaEnum.BEBIDA)
                .imagem("Nova Imagem")
                .build();

        ProdutoController produtoController = new ProdutoController(categoriaRepository, produtoRepository, produtoMapper);
        assertThrows(ProdutoException.class, () -> produtoController.atualizarProduto("99", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuandoAtualizarComImagemInvalida() {
        ProdutoDTO produtoDTO = ProdutoDTO.builder()
                .nome("Novo Nome")
                .descricao("Nova Descricao")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.ONE)
                .build();

        ProdutoController produtoController = new ProdutoController(categoriaRepository, produtoRepository, produtoMapper);
        assertThrows(ProdutoException.class, () -> produtoController.atualizarProduto("99", produtoDTO));
    }

}
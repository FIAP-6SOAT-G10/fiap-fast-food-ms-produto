package br.com.fiap.techchallenge.usecases.produto;

import br.com.fiap.techchallenge.adapters.produto.PutProdutoAdapter;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.enums.CategoriaEnum;
import br.com.fiap.techchallenge.domain.model.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.domain.usecases.produto.PutProdutoUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import br.com.fiap.techchallenge.ports.produto.PutProdutoOutboundPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class PutProdutoUseCaseTest {

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    PutProdutoOutboundPort putProdutoAdapter;

    @Test
    void shouldAtualizarProdutoComSucesso() {
        Produto produtoMock = mock(Produto.class);

        when(produtoRepository.saveAndFlush(any())).thenReturn(produtoMock);
        when(putProdutoAdapter.atualizarProduto(anyLong(), any())).thenReturn(produtoMock);

        ProdutoDTO produtoDTO = ProdutoDTO.builder()
                .nome("Novo Nome")
                .descricao("Nova Descricao")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.ONE)
                .imagem("Nova Imagem")
                .build();

        PutProdutoUseCase putProdutoUseCase = new PutProdutoUseCase(putProdutoAdapter);
        Produto produto = putProdutoUseCase.atualizarProduto("1", produtoDTO);

        assertNotNull(produto);
    }

    @Test
    void mustLancarProdutoExceptionQuandoAtualizarProdutoComIdentificadorInvalido() {
        ProdutoDTO produtoDTO = ProdutoDTO.builder()
                .nome("Novo Nome")
                .descricao("Nova Descricao")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.ONE)
                .imagem("Nova Imagem")
                .build();

        PutProdutoUseCase putProdutoUseCase = new PutProdutoUseCase(putProdutoAdapter);

        assertThrows(ProdutoException.class, () -> putProdutoUseCase.atualizarProduto("A", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuandoAtualizarProdutoComIdentificadorInexistente() {
        ProdutoRepository mockProdutoRepository = mock(ProdutoRepository.class);
        ProdutoMapper mockProdutoMapper = mock(ProdutoMapper.class);

        Produto produto = new Produto();
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));

        ProdutoDTO produtoDTO = ProdutoDTO.builder()
                .nome("Novo Nome")
                .descricao("Nova Descricao")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.ONE)
                .imagem("Nova Imagem")
                .build();

        PutProdutoAdapter port = new PutProdutoAdapter(mockProdutoRepository, mockProdutoMapper);
        PutProdutoUseCase putProdutoUseCase = new PutProdutoUseCase(port);
        assertThrows(ProdutoException.class, () -> putProdutoUseCase.atualizarProduto("99", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuantoAtualizarProdutoSemNome() {
        ProdutoDTO produtoDTO = ProdutoDTO.builder()
                .descricao("Nova Descricao")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.ONE)
                .imagem("Nova Imagem")
                .build();

        PutProdutoUseCase putProdutoUseCase = new PutProdutoUseCase(putProdutoAdapter);

        assertThrows(ProdutoException.class, () -> putProdutoUseCase.atualizarProduto("99", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuantoAtualizarProdutoSemDescricao() {
        ProdutoDTO produtoDTO = ProdutoDTO.builder()
                .nome("Novo Nome")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.ONE)
                .imagem("Nova Imagem")
                .build();

        PutProdutoUseCase putProdutoUseCase = new PutProdutoUseCase(putProdutoAdapter);

        assertThrows(ProdutoException.class, () -> putProdutoUseCase.atualizarProduto("99", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuantoAtualizarProdutoSemCategoria() {
        ProdutoDTO produtoDTO = ProdutoDTO.builder()
                .nome("Novo Nome")
                .descricao("Nova Descrição")
                .preco(BigDecimal.ONE)
                .imagem("Nova Imagem")
                .build();

        PutProdutoUseCase putProdutoUseCase = new PutProdutoUseCase(putProdutoAdapter);

        assertThrows(ProdutoException.class, () -> putProdutoUseCase.atualizarProduto("99", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuantoAtualizarProdutoSemPreco() {
        ProdutoDTO produtoDTO = ProdutoDTO.builder()
                .nome("Novo Nome")
                .descricao("Nova Descrição")
                .categoria(CategoriaEnum.BEBIDA)
                .imagem("Nova Imagem")
                .build();

        PutProdutoUseCase putProdutoUseCase = new PutProdutoUseCase(putProdutoAdapter);

        assertThrows(ProdutoException.class, () -> putProdutoUseCase.atualizarProduto("99", produtoDTO));
    }

    @Test
    void mustLancarProdutoExceptionQuantoAtualizarProdutoSemImagem() {
        ProdutoDTO produtoDTO = ProdutoDTO.builder()
                .nome("Novo Nome")
                .descricao("Nova Descrição")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(BigDecimal.ONE)
                .build();

        PutProdutoUseCase putProdutoUseCase = new PutProdutoUseCase(putProdutoAdapter);

        assertThrows(ProdutoException.class, () -> putProdutoUseCase.atualizarProduto("99", produtoDTO));
    }

}

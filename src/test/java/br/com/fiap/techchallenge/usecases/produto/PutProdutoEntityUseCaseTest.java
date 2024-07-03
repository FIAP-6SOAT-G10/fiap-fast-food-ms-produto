//package br.com.fiap.techchallenge.usecases.produto;
//
//import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
//import br.com.fiap.techchallenge.naousar.adapters.produto.PutProdutoAdapter;
//import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
//import br.com.fiap.techchallenge.infra.mapper.produto.ProdutoMapper;
//import br.com.fiap.techchallenge.naousar.domain.usecases.produto.PutProdutoUseCase;
//import br.com.fiap.techchallenge.naousar.domain.valueobjects.ProdutoDTO;
//import br.com.fiap.techchallenge.naousar.infra.exception.ProdutoException;
//import br.com.fiap.techchallenge.infra.persistence.ProdutoEntityRepository;
//import br.com.fiap.techchallenge.naousar.ports.produto.PutProdutoOutboundPort;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class PutProdutoEntityUseCaseTest {
//
//    @Mock
//    ProdutoEntityRepository produtoEntityRepository;
//
//    @Mock
//    PutProdutoOutboundPort putProdutoAdapter;
//
//    @Test
//    void shouldAtualizarProdutoComSucesso() {
//        ProdutoEntity produtoEntityMock = mock(ProdutoEntity.class);
//
//        when(produtoEntityRepository.saveAndFlush(any())).thenReturn(produtoEntityMock);
//        when(putProdutoAdapter.atualizarProduto(anyLong(), any())).thenReturn(produtoEntityMock);
//
//        ProdutoDTO produtoDTO = ProdutoDTO.builder()
//                .nome("Novo Nome")
//                .descricao("Nova Descricao")
//                .categoria(CategoriaEnum.BEBIDA)
//                .preco(BigDecimal.ONE)
//                .imagem("Nova Imagem")
//                .build();
//
//        PutProdutoUseCase putProdutoUseCase = new PutProdutoUseCase(putProdutoAdapter);
//        ProdutoEntity produtoEntity = putProdutoUseCase.atualizarProduto("1", produtoDTO);
//
//        assertNotNull(produtoEntity);
//    }
//
//    @Test
//    void mustLancarProdutoExceptionQuandoAtualizarProdutoComIdentificadorInvalido() {
//        ProdutoDTO produtoDTO = ProdutoDTO.builder()
//                .nome("Novo Nome")
//                .descricao("Nova Descricao")
//                .categoria(CategoriaEnum.BEBIDA)
//                .preco(BigDecimal.ONE)
//                .imagem("Nova Imagem")
//                .build();
//
//        PutProdutoUseCase putProdutoUseCase = new PutProdutoUseCase(putProdutoAdapter);
//
//        assertThrows(ProdutoException.class, () -> putProdutoUseCase.atualizarProduto("A", produtoDTO));
//    }
//
//    @Test
//    void mustLancarProdutoExceptionQuandoAtualizarProdutoComIdentificadorInexistente() {
//        ProdutoEntityRepository mockProdutoEntityRepository = mock(ProdutoEntityRepository.class);
//        ProdutoMapper mockProdutoMapper = mock(ProdutoMapper.class);
//
//        ProdutoEntity produtoEntity = new ProdutoEntity();
//        when(produtoEntityRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
//
//        ProdutoDTO produtoDTO = ProdutoDTO.builder()
//                .nome("Novo Nome")
//                .descricao("Nova Descricao")
//                .categoria(CategoriaEnum.BEBIDA)
//                .preco(BigDecimal.ONE)
//                .imagem("Nova Imagem")
//                .build();
//
//        PutProdutoAdapter port = new PutProdutoAdapter(mockProdutoEntityRepository, mockProdutoMapper);
//        PutProdutoUseCase putProdutoUseCase = new PutProdutoUseCase(port);
//        assertThrows(ProdutoException.class, () -> putProdutoUseCase.atualizarProduto("99", produtoDTO));
//    }
//
//    @Test
//    void mustLancarProdutoExceptionQuantoAtualizarProdutoSemNome() {
//        ProdutoDTO produtoDTO = ProdutoDTO.builder()
//                .descricao("Nova Descricao")
//                .categoria(CategoriaEnum.BEBIDA)
//                .preco(BigDecimal.ONE)
//                .imagem("Nova Imagem")
//                .build();
//
//        PutProdutoUseCase putProdutoUseCase = new PutProdutoUseCase(putProdutoAdapter);
//
//        assertThrows(ProdutoException.class, () -> putProdutoUseCase.atualizarProduto("99", produtoDTO));
//    }
//
//    @Test
//    void mustLancarProdutoExceptionQuantoAtualizarProdutoSemDescricao() {
//        ProdutoDTO produtoDTO = ProdutoDTO.builder()
//                .nome("Novo Nome")
//                .categoria(CategoriaEnum.BEBIDA)
//                .preco(BigDecimal.ONE)
//                .imagem("Nova Imagem")
//                .build();
//
//        PutProdutoUseCase putProdutoUseCase = new PutProdutoUseCase(putProdutoAdapter);
//
//        assertThrows(ProdutoException.class, () -> putProdutoUseCase.atualizarProduto("99", produtoDTO));
//    }
//
//    @Test
//    void mustLancarProdutoExceptionQuantoAtualizarProdutoSemCategoria() {
//        ProdutoDTO produtoDTO = ProdutoDTO.builder()
//                .nome("Novo Nome")
//                .descricao("Nova Descrição")
//                .preco(BigDecimal.ONE)
//                .imagem("Nova Imagem")
//                .build();
//
//        PutProdutoUseCase putProdutoUseCase = new PutProdutoUseCase(putProdutoAdapter);
//
//        assertThrows(ProdutoException.class, () -> putProdutoUseCase.atualizarProduto("99", produtoDTO));
//    }
//
//    @Test
//    void mustLancarProdutoExceptionQuantoAtualizarProdutoSemPreco() {
//        ProdutoDTO produtoDTO = ProdutoDTO.builder()
//                .nome("Novo Nome")
//                .descricao("Nova Descrição")
//                .categoria(CategoriaEnum.BEBIDA)
//                .imagem("Nova Imagem")
//                .build();
//
//        PutProdutoUseCase putProdutoUseCase = new PutProdutoUseCase(putProdutoAdapter);
//
//        assertThrows(ProdutoException.class, () -> putProdutoUseCase.atualizarProduto("99", produtoDTO));
//    }
//
//    @Test
//    void mustLancarProdutoExceptionQuantoAtualizarProdutoSemImagem() {
//        ProdutoDTO produtoDTO = ProdutoDTO.builder()
//                .nome("Novo Nome")
//                .descricao("Nova Descrição")
//                .categoria(CategoriaEnum.BEBIDA)
//                .preco(BigDecimal.ONE)
//                .build();
//
//        PutProdutoUseCase putProdutoUseCase = new PutProdutoUseCase(putProdutoAdapter);
//
//        assertThrows(ProdutoException.class, () -> putProdutoUseCase.atualizarProduto("99", produtoDTO));
//    }
//
//}

//package br.com.fiap.techchallenge.usecases.produto;
//
//import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;
//import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
//import br.com.fiap.techchallenge.infra.mapper.produto.ProdutoMapper;
//import br.com.fiap.techchallenge.naousar.domain.usecases.produto.GetProdutoUseCase;
//import br.com.fiap.techchallenge.naousar.infra.exception.CategoriaException;
//import br.com.fiap.techchallenge.infra.persistence.ProdutoEntityRepository;
//import br.com.fiap.techchallenge.naousar.ports.produto.GetProdutoOutboundPort;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class GetProdutoEntityUseCaseTest {
//
//    @Mock
//    ProdutoEntityRepository produtoEntityRepository;
//
//    @Autowired
//    ProdutoMapper produtoMapper;
//
//    @Test
//    void shouldListarProdutosPorCategoriaInformada() {
//        Optional<List<ProdutoEntity>> optionalProdutos = Optional.of(criarListaDeProdutos());
//        when(produtoEntityRepository.findAllByCategoriaEntityId(anyInt())).thenReturn(optionalProdutos);
//
//        GetProdutoOutboundPort getProdutoAdapter = new GetProdutoAdapter(produtoEntityRepository, produtoMapper);
//        GetProdutoUseCase getProdutoUseCase = new GetProdutoUseCase(getProdutoAdapter);
//        List<ProdutoEntity> lanches = getProdutoUseCase.listarProdutosPorCategoria("lanche");
//
//        assertEquals(2, lanches.size());
//    }
//
//    @Test
//    void mustLancarCategoriaExceptionQuandoACategoriaNaoForValida() {
//        GetProdutoOutboundPort getProdutoAdapter = new GetProdutoAdapter(produtoEntityRepository, produtoMapper);
//        GetProdutoUseCase getProdutoUseCase = new GetProdutoUseCase(getProdutoAdapter);
//
//        assertThrows(CategoriaException.class, () -> getProdutoUseCase.listarProdutosPorCategoria("inexistente"));
//    }
//
//    private List<ProdutoEntity> criarListaDeProdutos() {
//        return List.of(
//                ProdutoEntity.builder().categoriaEntity(CategoriaEntity.builder().id(1L).nome("lanche").build()).build(),
//                ProdutoEntity.builder().categoriaEntity(CategoriaEntity.builder().id(1L).nome("lanche").build()).build()
//        );
//    }
//
//}

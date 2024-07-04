//package br.com.fiap.techchallenge.usecases.produto;
//
//import br.com.fiap.techchallenge.infra.persistence.entities.CategoriaEntity;
//import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
//import br.com.fiap.techchallenge.infra.mapper.produto.ProdutoMapper;
//import br.com.fiap.techchallenge.naousar.domain.usecases.produto.GetProdutoUseCase;
//import br.com.fiap.techchallenge.naousar.domain.valueobjects.ProdutoDTO;
//import br.com.fiap.techchallenge.infra.persistence.ProdutoEntityRepository;
//import br.com.fiap.techchallenge.naousar.ports.produto.GetProdutoInboundPort;
//import br.com.fiap.techchallenge.naousar.ports.produto.GetProdutoOutboundPort;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//public class GetProdutosUseCaseTest {
//
//    @Mock
//    private ProdutoEntityRepository repository;
//
//    @Autowired
//    private ProdutoMapper produtoMapper;
//
//    private GetProdutoInboundPort getProdutosUseCase;
//
//    @BeforeEach
//    public void setUp() {
//        GetProdutoOutboundPort getProdutoOutboundPort = new GetProdutoAdapter(repository, produtoMapper);
//        getProdutosUseCase = new GetProdutoUseCase(getProdutoOutboundPort);
//    }
//
//    @Test
//    void shouldListarTodos10PrimeirosProdutos() {
//        int page = 0;
//        int size = 10;
//
//        when(repository.findAll(PageRequest.of(page, size))).thenReturn(getMockPageProduto());
//
//        List<ProdutoDTO> produtos = getProdutosUseCase.listarProdutos(page, size, null, null, null);
//        assertEquals(10, produtos.size());
//
//    }
//
//    @Test
//    void shouldRetornarSomenteUmProduto() {
//        int page = 0;
//        int size = 10;
//
//        when(repository.findByNomeOrDescricaoOrPreco("Heimno", null, null)).thenReturn(getListMockedOptionalProdutos());
//
//        List<ProdutoDTO> produtos = getProdutosUseCase.listarProdutos(page, size, "Heimno", null, null);
//        assertEquals(1, produtos.size());
//
//    }
//
//    @Test
//    void shouldRetornarNenhumProduto() {
//        int page = 0;
//        int size = 10;
//
//        when(repository.findByNomeOrDescricaoOrPreco("Rafael", null, null)).thenReturn(getListMockedOptionalProdutos());
//
//        List<ProdutoDTO> produtos = getProdutosUseCase.listarProdutos(page, size, "Heimno", null, null);
//        assertEquals(0, produtos.size());
//
//    }
//
//    @Test
//    void shouldRetornarUmProdutoPeloPreco() {
//        int page = 0;
//        int size = 10;
//
//        when(repository.findByNomeOrDescricaoOrPreco(null, null, new BigDecimal("10.5"))).thenReturn(getListMockedOptionalProdutos());
//
//        List<ProdutoDTO> produtos = getProdutosUseCase.listarProdutos(page, size, null, null, new BigDecimal("10.5"));
//        assertEquals(1, produtos.size());
//
//    }
//
//    @Test
//    void shouldRetornarUmProdutoPelaDescricao() {
//        int page = 0;
//        int size = 10;
//
//        when(repository.findByNomeOrDescricaoOrPreco(null, "TESTE", new BigDecimal("10.5"))).thenReturn(getListMockedOptionalProdutos());
//
//        List<ProdutoDTO> produtos = getProdutosUseCase.listarProdutos(page, size, null, "TESTE", new BigDecimal("10.5"));
//        assertEquals(1, produtos.size());
//
//    }
//
//    private Page<ProdutoEntity> getMockPageProduto() {
//        List<ProdutoEntity> produtoEntities = new ArrayList<>();
//        CategoriaEntity categoriaEntity = new CategoriaEntity(1L, "lanche", "Lanches", new HashSet<>());
//        produtoEntities.addAll(List.of(
//                new ProdutoEntity(1L, "Heimno", "Niarhois", categoriaEntity, new BigDecimal("10.5"), ""),
//                new ProdutoEntity(2L, "Duerwadak", "Vigauval", categoriaEntity, new BigDecimal("12.5"), ""),
//                new ProdutoEntity(3L, "Eduarda", "Galhad", categoriaEntity, new BigDecimal("10.5"), ""),
//                new ProdutoEntity(4L, "Niarhois", "Galhad", categoriaEntity, new BigDecimal("10.5"), ""),
//                new ProdutoEntity(5L, "Aegweo", "Duerwadak", categoriaEntity, new BigDecimal("10.5"), ""),
//                new ProdutoEntity(6L, "Relko", "Duerwadak", categoriaEntity, new BigDecimal("10.5"), ""),
//                new ProdutoEntity(7L, "Andreia", "Galhad", categoriaEntity, new BigDecimal("10.5"), ""),
//                new ProdutoEntity(8L, "Relkio", "Nocu", categoriaEntity, new BigDecimal("10.5"), ""),
//                new ProdutoEntity(9L, "Eita", "Nocu", categoriaEntity, new BigDecimal("10.5"), ""),
//                new ProdutoEntity(10L, "Nocu", "Niarhois", categoriaEntity, new BigDecimal("10.5"), "")
//        ));
//        return new PageImpl<>(produtoEntities);
//    }
//    private Optional<List<ProdutoEntity>> getListMockedOptionalProdutos(){
//        CategoriaEntity categoriaEntity = new CategoriaEntity(1L, "lanche", "Lanches", new HashSet<>());
//        return Optional.of(List.of(
//                new ProdutoEntity(1L, "Heimno", "TESTE", categoriaEntity, new BigDecimal("10.5"), ""),
//                new ProdutoEntity(2L, "Duerwadak", "Vigauval", categoriaEntity, new BigDecimal("1345.0"), ""),
//                new ProdutoEntity(3L, "Eduarda", "Galhad", categoriaEntity, new BigDecimal("28.5"), ""),
//                new ProdutoEntity(4L, "Niarhois", "Galhad", categoriaEntity, new BigDecimal("15.99"), ""),
//                new ProdutoEntity(5L, "Aegweo", "Duerwadak", categoriaEntity, new BigDecimal("18.36"), ""),
//                new ProdutoEntity(6L, "Relko", "Duerwadak", categoriaEntity, new BigDecimal("67.5"), ""),
//                new ProdutoEntity(7L, "Andreia", "Galhad", categoriaEntity, new BigDecimal("16.25"), ""),
//                new ProdutoEntity(8L, "Relkio", "Nocu", categoriaEntity, new BigDecimal("288.36"), ""),
//                new ProdutoEntity(9L, "Eita", "Nocu", categoriaEntity, new BigDecimal("197.5"), ""),
//                new ProdutoEntity(10L, "Nocu", "Niarhois", categoriaEntity, new BigDecimal("132.5"), ""),
//                new ProdutoEntity(10L, "Laele", "Niarhois", categoriaEntity, new BigDecimal("58.5"), "")
//        ));
//    }
//
//}

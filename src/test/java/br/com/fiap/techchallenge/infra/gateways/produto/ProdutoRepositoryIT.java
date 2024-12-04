package br.com.fiap.techchallenge.infra.gateways.produto;

import br.com.fiap.techchallenge.domain.entities.produto.Categoria;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import br.com.fiap.techchallenge.infra.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.infra.persistence.CategoriaEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.ProdutoEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("integration-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class ProdutoRepositoryIT {

    @Autowired
    private ProdutoEntityRepository produtoEntityRepository;

    @Autowired
    private CategoriaEntityRepository categoriaEntityRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void listarProdutosTest() {
        List<ProdutoEntity> allProduct = produtoEntityRepository.findAll();
        Produto produto = produtoMapper.fromListEntityToListDomain(allProduct).get(0);
        List<Produto> produtos = produtoRepository.listarProdutos(produto.getNome(), produto.getDescricao(), produto.getPreco());
        assertNotNull(produtos);
        assertEquals(1, produtos.size());
        assertEquals("Big Fiap", produtos.get(0).getNome());
    }

    @Test
    void criarProdutoTest() {
        Produto produto = new Produto("Produto Original", "Descrição Original", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(19.99), "imagem.jpg");
        Produto createdProduto = produtoRepository.criarProduto(produto);
        assertNotNull(createdProduto);
        assertEquals("Produto Original", createdProduto.getNome());
    }

    @Test
    void criarProdutoProdutoExistenteTest() {

        Produto produto = new Produto("Big Fiap", "Dois hambúrgueres, alface americana, queijo cheddar, maionese, cebola, picles e pão com gergelim", new Categoria("LANCHE", "Lanches"), BigDecimal.valueOf(26.9), "imagem.jpg");
        ProdutoException exception = assertThrows(ProdutoException.class, () -> produtoRepository.criarProduto(produto));
        assertEquals("Produto ja existe.", exception.getMessage());
    }
}

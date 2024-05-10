package br.com.fiap.techchallenge.usecases.produtos;

import br.com.fiap.techchallenge.domain.entities.Categoria;
import br.com.fiap.techchallenge.domain.entities.Produto;
import br.com.fiap.techchallenge.domain.model.enums.CategoriaEnum;
import br.com.fiap.techchallenge.domain.usecases.produtos.GetProdutosUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.ProdutoDTO;
import br.com.fiap.techchallenge.infra.repositories.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetProdutosUseCaseTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private GetProdutosUseCase getProdutosUseCase;

    @Test
    void ItShouldListarTodos10PrimeirosProdutos() {
        int page = 0;
        int size = 10;

        when(repository.findAll(PageRequest.of(page, size))).thenReturn(GetMockPageProduto());

        List<ProdutoDTO> produtos = getProdutosUseCase.listarProdutos(page, size, null, null, null);
        assertEquals(10, produtos.size());

    }

    private Page<Produto> GetMockPageProduto() {
        List<Produto> produtos = new ArrayList<>();
        Categoria categoria = new Categoria(1L, "Sanduiche", "Um Sanduiche", new HashSet<>());
        produtos.addAll(List.of(
                new Produto(1L, "Heimno", "Niarhois", categoria, new BigDecimal("10.5"), ""),
                new Produto(2L, "Duerwadak", "Vigauval", categoria, new BigDecimal("10.5"), ""),
                new Produto(3L, "Duerwadak", "Galhad", categoria, new BigDecimal("10.5"), ""),
                new Produto(4L, "Niarhois", "Galhad", categoria, new BigDecimal("10.5"), ""),
                new Produto(5L, "Aegweo", "Duerwadak", categoria, new BigDecimal("10.5"), ""),
                new Produto(6L, "Relko", "Duerwadak", categoria, new BigDecimal("10.5"), ""),
                new Produto(7L, "Niarhois", "Galhad", categoria, new BigDecimal("10.5"), ""),
                new Produto(8L, "Relko", "Nocu", categoria, new BigDecimal("10.5"), ""),
                new Produto(9L, "Aegweo", "Nocu", categoria, new BigDecimal("10.5"), ""),
                new Produto(10L, "Nocu", "Niarhois", categoria, new BigDecimal("10.5"), "")
        ));
        return new PageImpl<>(produtos);
    }
}

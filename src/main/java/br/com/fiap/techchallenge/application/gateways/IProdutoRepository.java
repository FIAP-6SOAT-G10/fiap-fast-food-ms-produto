package br.com.fiap.techchallenge.application.gateways;

import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import com.github.fge.jsonpatch.JsonPatch;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IProdutoRepository {
    List<Produto> listarProdutos(Integer page, Integer size, String nome, String descricao, BigDecimal preco);

    List<Produto> listarProdutosPorCategoria(CategoriaEnum categoriaEnum);

    Produto criarProduto(Produto produto);

    void deletarProduto(Long id);

    Produto atualizarDadosProduto(Long id, JsonPatch patch);

    Produto atualizarProduto(Long id, Produto produto);

    Produto findById(Long id);
}

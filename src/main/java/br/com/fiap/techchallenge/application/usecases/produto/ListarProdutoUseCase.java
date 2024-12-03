package br.com.fiap.techchallenge.application.usecases.produto;

import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.domain.entities.produto.CategoriaEnum;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;

import java.math.BigDecimal;
import java.util.List;

public class ListarProdutoUseCase {

    private final IProdutoRepository produtoRepository;

    public ListarProdutoUseCase(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarProdutos(String nome, String descricao, BigDecimal preco) {
        return this.produtoRepository.listarProdutos(nome, descricao, preco);
    }

    public List<Produto> listarProdutosPorCategoria(CategoriaEnum categoriaEnum) {
        return this.produtoRepository.listarProdutosPorCategoria(categoriaEnum);
    }

    public Produto buscarProdutoPorId(Long id) {
        return this.produtoRepository.findById(id);
    }
}

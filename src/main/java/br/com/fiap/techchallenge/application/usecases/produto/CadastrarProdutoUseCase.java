package br.com.fiap.techchallenge.application.usecases.produto;

import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;

public class CadastrarProdutoUseCase {

    private final IProdutoRepository produtoRepository;

    public CadastrarProdutoUseCase(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto criarProduto(Produto produto) {
        return this.produtoRepository.criarProduto(produto);
    }

}

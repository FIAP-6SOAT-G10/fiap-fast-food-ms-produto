package br.com.fiap.techchallenge.application.usecases.produto;

import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.domain.entities.produto.Produto;

public class AtualizarProdutoUseCase {

    private final IProdutoRepository produtoRepository;

    public AtualizarProdutoUseCase(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto atualizarProduto(String id, Produto produto) {
        return this.produtoRepository.atualizarProduto(Long.valueOf(id), produto);
    }

}

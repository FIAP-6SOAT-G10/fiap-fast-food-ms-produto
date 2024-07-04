package br.com.fiap.techchallenge.application.usecases.produto;

import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;

public class DeletarProdutoUseCase {

    private final IProdutoRepository produtoRepository;

    public DeletarProdutoUseCase(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void deletarProduto(String id) {
        this.produtoRepository.deletarProduto(Long.valueOf(id));
    }
}
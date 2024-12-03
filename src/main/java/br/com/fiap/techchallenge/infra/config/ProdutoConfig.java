package br.com.fiap.techchallenge.infra.config;

import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.application.usecases.produto.*;
import br.com.fiap.techchallenge.infra.gateways.produto.ProdutoRepository;
import br.com.fiap.techchallenge.infra.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.infra.persistence.CategoriaEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.ProdutoEntityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProdutoConfig {

    @Bean
    public ListarProdutoUseCase criarListagemProdutosUseCase(IProdutoRepository produtoRepository) {
        return new ListarProdutoUseCase(produtoRepository);
    }

    @Bean
    public CadastrarProdutoUseCase criarCadastrarProdutoUseCase(IProdutoRepository produtoRepository) {
        return new CadastrarProdutoUseCase(produtoRepository);
    }

    @Bean
    public DeletarProdutoUseCase criarDeletarProdutoUseCase(IProdutoRepository produtoRepository) {
        return new DeletarProdutoUseCase(produtoRepository);
    }

    @Bean
    public AtualizarProdutoParcialUseCase criarAtualizarProdutoParcialUseCase(IProdutoRepository produtoRepository) {
        return new AtualizarProdutoParcialUseCase(produtoRepository);
    }

    @Bean
    public AtualizarProdutoUseCase criarAtualizarProdutoUseCase(IProdutoRepository produtoRepository) {
        return new AtualizarProdutoUseCase(produtoRepository);
    }

    @Bean
    public IProdutoRepository criarProdutoRepository(ProdutoEntityRepository produtoEntityRepository, CategoriaEntityRepository categoriaEntityRepository, ProdutoMapper produtoMapper) {
        return new ProdutoRepository(produtoEntityRepository, categoriaEntityRepository, produtoMapper);
    }

    @Bean
    public ProdutoMapper criarProdutoMapper() {
        return new ProdutoMapper();
    }

}

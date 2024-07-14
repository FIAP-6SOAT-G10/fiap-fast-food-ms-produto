package br.com.fiap.techchallenge.infra.config;

import br.com.fiap.techchallenge.application.gateways.IPedidoRepository;
import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.application.usecases.pedido.AtualizarPedidoParcialUseCase;
import br.com.fiap.techchallenge.application.usecases.produto.*;
import br.com.fiap.techchallenge.infra.gateways.ProdutoRepository;
import br.com.fiap.techchallenge.infra.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.infra.persistence.CategoriaEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.ProdutoEntityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PedidoConfig {

    @Bean
    public AtualizarPedidoParcialUseCase criarListagemPedidoProdutosUseCase(IPedidoRepository pedidoRepository) {
        return new AtualizarPedidoParcialUseCase(pedidoRepository);
    }

    @Bean
    public CadastrarProdutoUseCase criarCadastrarPedidoProdutoUseCase(IProdutoRepository produtoRepository) {
        return new CadastrarProdutoUseCase(produtoRepository);
    }

    @Bean
    public DeletarProdutoUseCase criarDeletarPedidoProdutoUseCase(IProdutoRepository produtoRepository) {
        return new DeletarProdutoUseCase(produtoRepository);
    }

    @Bean
    public AtualizarProdutoParcialUseCase criarAtualizarPedidoProdutoParcialUseCase(IProdutoRepository produtoRepository) {
        return new AtualizarProdutoParcialUseCase(produtoRepository);
    }

    @Bean
    public AtualizarProdutoUseCase criarAtualizarPedidoProdutoUseCase(IProdutoRepository produtoRepository) {
        return new AtualizarProdutoUseCase(produtoRepository);
    }

    @Bean
    public IProdutoRepository criarPedidoProdutoRepository(ProdutoEntityRepository produtoEntityRepository, CategoriaEntityRepository categoriaEntityRepository, ProdutoMapper produtoMapper) {
        return new ProdutoRepository(produtoEntityRepository, categoriaEntityRepository, produtoMapper);
    }

    @Bean
    public ProdutoMapper criarPedidoProdutoMapper() {
        return new ProdutoMapper();
    }

}

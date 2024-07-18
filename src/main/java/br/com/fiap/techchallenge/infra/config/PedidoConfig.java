package br.com.fiap.techchallenge.infra.config;

import br.com.fiap.techchallenge.application.gateways.IPedidoRepository;
import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.application.usecases.pedido.AtualizarPedidoParcialUseCase;
import br.com.fiap.techchallenge.application.usecases.produto.*;
import br.com.fiap.techchallenge.infra.gateways.PedidoEntityRepository;
import br.com.fiap.techchallenge.infra.gateways.ProdutoRepository;
import br.com.fiap.techchallenge.infra.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.infra.mapper.produto.ProdutoMapper;
import br.com.fiap.techchallenge.infra.persistence.CategoriaEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.PedidoRepository;
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
    public IPedidoRepository criarPedidoProdutoRepository(PedidoRepository pedidoRepository) {
        return new PedidoEntityRepository(pedidoRepository);
    }

    @Bean
    public PedidoMapper criarPedidoMapper() {
        return new PedidoMapper();
    }

}

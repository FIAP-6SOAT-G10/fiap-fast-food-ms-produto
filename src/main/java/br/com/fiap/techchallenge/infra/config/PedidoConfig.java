package br.com.fiap.techchallenge.infra.config;

import br.com.fiap.techchallenge.application.gateways.IPedidoRepository;
import br.com.fiap.techchallenge.application.usecases.pedido.AtualizarPedidoParcialUseCase;
import br.com.fiap.techchallenge.application.usecases.pedido.GetPedidoUseCase;
import br.com.fiap.techchallenge.application.usecases.pedido.PatchPedidoUseCase;
import br.com.fiap.techchallenge.infra.gateways.ClienteRepository;
import br.com.fiap.techchallenge.infra.gateways.PedidoRepository;
import br.com.fiap.techchallenge.infra.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.infra.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.infra.mapper.produtopedido.ProdutoPedidoMapper;
import br.com.fiap.techchallenge.infra.persistence.ClienteEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.PedidoEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.ProdutoPedidoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PedidoConfig {

    @Bean
    public AtualizarPedidoParcialUseCase criarListagemPedidoProdutosUseCase(IPedidoRepository pedidoRepository) {
        return new AtualizarPedidoParcialUseCase(pedidoRepository);
    }

    @Bean
    public IPedidoRepository criarPedidoProdutoRepository(PedidoEntityRepository pedidoEntityRepository, PedidoMapper pedidoMapper, ClienteMapper clienteMapper, ClienteEntityRepository clienteRepository, ProdutoPedidoRepository produtoPedidoRepository, ProdutoPedidoMapper produtoPedidoMapper) {
        return new PedidoRepository(pedidoEntityRepository, pedidoMapper, clienteMapper, clienteRepository, produtoPedidoRepository, produtoPedidoMapper);
    }

    @Bean
    public GetPedidoUseCase criarGetPedidoUseCase(IPedidoRepository pedidoRepository) {
        return new GetPedidoUseCase(pedidoRepository);
    }

    @Bean
    public PatchPedidoUseCase criarPatchPedidoUseCase(IPedidoRepository pedidoRepository) {
        return new PatchPedidoUseCase(pedidoRepository);
    }

    @Bean
    public PedidoMapper criarPedidoMapper() {
        return new PedidoMapper();
    }

    @Bean
    public ProdutoPedidoMapper criarProdutoPedidoMapper() {
        return new ProdutoPedidoMapper();
    }

}

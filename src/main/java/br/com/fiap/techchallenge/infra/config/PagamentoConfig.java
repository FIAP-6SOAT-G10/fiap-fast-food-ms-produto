package br.com.fiap.techchallenge.infra.config;

import br.com.fiap.techchallenge.application.gateways.IPedidoRepository;
import br.com.fiap.techchallenge.application.gateways.IProdutoRepository;
import br.com.fiap.techchallenge.application.usecases.pagamento.ConsultarPagamentoUseCase;
import br.com.fiap.techchallenge.application.usecases.pagamento.RealizarPagamentoUseCase;
import br.com.fiap.techchallenge.application.usecases.pedido.PatchPedidoUseCase;
import br.com.fiap.techchallenge.application.usecases.pedido.PostPedidoUseCase;
import br.com.fiap.techchallenge.infra.mapper.pagamento.PagamentoMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagamentoConfig {

    @Value("${mercadopago.access_token}")
    private String mercadoPagoAccessToken;

    @Value("${mercadopago.notification_url}")
    private String mercadoPagoNotificationUrl;

    @Bean
    public ConsultarPagamentoUseCase criarConsultarPagamentoUseCase(PatchPedidoUseCase patchPedidoUseCase) {
        return new ConsultarPagamentoUseCase(patchPedidoUseCase, mercadoPagoAccessToken);
    }

    @Bean
    public RealizarPagamentoUseCase criarRealizarPagamentoUseCase(PatchPedidoUseCase patchPedidoUseCase) {
        return new RealizarPagamentoUseCase(patchPedidoUseCase, mercadoPagoAccessToken, mercadoPagoNotificationUrl);
    }

    @Bean
    public PostPedidoUseCase criarPostPedidoUseCase(IPedidoRepository pedidoRepository, IProdutoRepository produtoRepository, RealizarPagamentoUseCase realizarPagamentUseCase) {
        return new PostPedidoUseCase(pedidoRepository, produtoRepository, realizarPagamentUseCase);
    }

    @Bean
    public PagamentoMapper criarPagamentoMapper() {
        return new PagamentoMapper();
    }

}

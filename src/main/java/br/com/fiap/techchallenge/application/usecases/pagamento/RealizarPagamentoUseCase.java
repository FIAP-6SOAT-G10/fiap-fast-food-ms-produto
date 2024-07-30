package br.com.fiap.techchallenge.application.usecases.pagamento;

import br.com.fiap.techchallenge.application.usecases.pedido.PatchPedidoUseCase;
import br.com.fiap.techchallenge.domain.entities.pagamento.PagamentoResponseDTO;
import br.com.fiap.techchallenge.domain.entities.pedido.Pedido;
import br.com.fiap.techchallenge.infra.exception.MercadoPagoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.exceptions.MPException;

@Service
public class RealizarPagamentoUseCase {

    private final PatchPedidoUseCase patchPedidoUseCase;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${mercadopago.access_token}")
    private String mercadoPagoAccessToken;

    @Value("${mercadopago.notification_url}")
    private String mercadoPagoNotificationUrl;

    public RealizarPagamentoUseCase(PatchPedidoUseCase patchPedidoUseCase) {
        this.patchPedidoUseCase = patchPedidoUseCase;
    }

    public PagamentoResponseDTO efetuarPagamento(Pedido pedido) {
        try {
            MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

            PaymentCreateRequest paymentCreateRequest =
                    PaymentCreateRequest.builder()
                            .externalReference(pedido.getId().toString())
                            .notificationUrl("https://6e91-2804-7f0-bec0-2fa7-4d18-dc53-6657-d0ff.ngrok-free.app/api/webhook/notifications")
                            .transactionAmount(pedido.getValor())
                            .paymentMethodId("pix")
                            .payer(
                                    PaymentPayerRequest.builder()
                                    .email(pedido.getCliente().getEmail() == null ?  "notification@email.com": pedido.getCliente().getEmail())
                                    .build())
                            .build();

            Payment createdPayment = new PaymentClient().create(paymentCreateRequest);
            PagamentoResponseDTO pagamentoResponseDTO = new PagamentoResponseDTO(
                createdPayment.getId(),
                String.valueOf(createdPayment.getStatus()),
                createdPayment.getStatusDetail(),
                createdPayment.getPointOfInteraction().getTransactionData().getQrCodeBase64(),
                createdPayment.getPointOfInteraction().getTransactionData().getQrCode()
            );

            return pagamentoResponseDTO;
        } catch (MPApiException apiException) {
            throw new MercadoPagoException(apiException.getApiResponse().getContent());
        } catch (MPException exception) {
            throw new MercadoPagoException(exception.getMessage());
        }
    }
}

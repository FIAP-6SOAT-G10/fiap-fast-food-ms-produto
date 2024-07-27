package br.com.fiap.techchallenge.infra.controllers.pedido;


import br.com.fiap.techchallenge.infra.controllers.WebhookUseCase;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Webhook", description = "Conjunto de operações que podem ser realizadas no contexto do webhook.")
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookUseCase webhookUseCase;

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evento de confirmação do pagamento aprovado ou pagamento recusado recebido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Caso o pedido ou pagamento com o ID informado não exista")
    })
    @PostMapping("/notifications")
    @ResponseStatus(HttpStatus.OK)
    public void confirmarPagamento(@RequestBody EventoPagamentoInput eventoPagamentoInput) {
        log.info("Recebendo notificação de pagamento id: {} ação: {}", eventoPagamentoInput.getData().getId(), eventoPagamentoInput.getAction());
        if (eventoPagamentoInput.getAction() == "payment.updated") {
            webhookUseCase.consultarPagamento(eventoPagamentoInput.getData().getId());
        }
    }

}

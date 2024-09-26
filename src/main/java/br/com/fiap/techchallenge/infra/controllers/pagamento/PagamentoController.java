package br.com.fiap.techchallenge.infra.controllers.pagamento;

import br.com.fiap.techchallenge.application.usecases.pagamento.ConsultarPagamentoUseCase;
import br.com.fiap.techchallenge.domain.entities.pagamento.EventoPagemento;
import br.com.fiap.techchallenge.infra.mapper.pagamento.PagamentoMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@Tag(name = "Webhook", description = "Conjunto de operações que podem ser realizadas no contexto do webhook.")
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class PagamentoController {

    private final ConsultarPagamentoUseCase consultarPagamentoUseCase;
    private final PagamentoMapper pagamentoMapper;

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evento de confirmação do pagamento aprovado ou pagamento recusado recebido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Caso o pedido ou pagamento com o ID informado não exista")
    })
    @PostMapping("/notifications")
    @ResponseStatus(HttpStatus.OK)
    public void confirmarPagamento(@RequestBody EventoPagamentoDTO eventoPagamentoDTO) {
        log.info("Recebendo notificação de pagamento id: {} ação: {}", eventoPagamentoDTO.getData().getId(), eventoPagamentoDTO.getAction());
        EventoPagemento eventoPagemento = pagamentoMapper.fromDTOToDomain(eventoPagamentoDTO);
        if (Objects.equals(eventoPagamentoDTO.getAction(), "payment.updated")) {
            consultarPagamentoUseCase.consultarPagamento(eventoPagemento);
        }
    }

}

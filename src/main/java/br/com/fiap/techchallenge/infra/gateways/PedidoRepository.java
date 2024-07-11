package br.com.fiap.techchallenge.infra.gateways;

import br.com.fiap.techchallenge.application.gateways.IPedidoRepository;
import br.com.fiap.techchallenge.infra.exception.PedidoException;
import br.com.fiap.techchallenge.naousar.domain.cors.statuspagamento.MudancaPagamentoPedido;
import br.com.fiap.techchallenge.naousar.domain.cors.statuspagamento.MudancaPagamentoPedidoPago;
import br.com.fiap.techchallenge.naousar.domain.cors.statuspagamento.MudancaPagamentoPedidoRecusado;
import br.com.fiap.techchallenge.naousar.domain.cors.statuspedido.*;
import br.com.fiap.techchallenge.infra.persistence.entities.Pedido;
import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.entities.pagamento.PagamentoPedidoEnum;
import br.com.fiap.techchallenge.domain.entities.pedido.StatusPedidoEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class PedidoRepository implements IPedidoRepository {

    private final br.com.fiap.techchallenge.infra.persistence.PedidoRepository pedidoRepository;

    public PedidoRepository(br.com.fiap.techchallenge.infra.persistence.PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public Pedido atualizarStatusDoPedido(String id, JsonPatch patch) {
        log.info("Atualizando status do pedido.");
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(Long.getLong(id));
        if (pedidoOptional.isEmpty()) {
            throw new PedidoException(ErrosEnum.PEDIDO_CODIGO_IDENTIFICADOR_INVALIDO);
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            Pedido pedidoAtual = pedidoOptional.get();
            JsonNode patched = patch.apply(objectMapper.convertValue(pedidoAtual, JsonNode.class));

            Pedido pedidoAtualizado = objectMapper.treeToValue(patched, Pedido.class);
            pedidoAtualizado.setCliente(pedidoAtual.getCliente());

            validarMudancaDeStatus(pedidoAtual, pedidoAtualizado);
            definirDataFinalizacaoPedido(pedidoAtualizado);

            return pedidoRepository.saveAndFlush(pedidoAtualizado);
        } catch (JsonPatchException | JsonProcessingException jsonException) {
            log.error("Erro ao atualizar o registro no banco de dados", jsonException);
            throw new PedidoException(ErrosEnum.PEDIDO_FALHA_DURANTE_ATUALIZACAO);
        }
    }

    private void validarMudancaDeStatus(Pedido atual, Pedido novo) {
        log.info("Validando mudança de status do pedido.");
        StatusPedidoEnum statusAtual = StatusPedidoEnum.byId(atual.getStatus().getId());
        StatusPedidoEnum statusNovo = StatusPedidoEnum.byId(novo.getStatus().getId());

        MudancaStatusPedido mudancaStatusPedido = new MudancaStatusPedidoRecebido(
                new MudancaStatusPedidoEmPreparacao(
                        new MudancaStatusPedidoPronto(
                                new MudancaStatusPedidoFinalizado()
                        )
                )
        );

        mudancaStatusPedido.validarMudancaDeStatus(statusAtual, statusNovo);
    }

    private void definirDataFinalizacaoPedido(Pedido novo) {
        StatusPedidoEnum statusNovo = StatusPedidoEnum.byId(novo.getStatus().getId());
        if (StatusPedidoEnum.FINALIZADO.equals(statusNovo)) {
            novo.setDataFinalizacao(LocalDateTime.now());
        }
    }

    @Override
    public Pedido atualizarPagamentoDoPedido(String id, JsonPatch patch) {
        log.info("Atualizando status de pagamento do pedido.");
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(Long.getLong(id));
        if (pedidoOptional.isEmpty()) {
            throw new PedidoException(ErrosEnum.PEDIDO_CODIGO_IDENTIFICADOR_INVALIDO);
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            Pedido pedidoAtual = pedidoOptional.get();
            JsonNode patched = patch.apply(objectMapper.convertValue(pedidoAtual, JsonNode.class));

            Pedido pedidoAtualizado = objectMapper.treeToValue(patched, Pedido.class);

            validarMudancaDePagamento(pedidoAtual, pedidoAtualizado);

            return pedidoRepository.saveAndFlush(pedidoAtualizado);
        } catch (JsonPatchException | JsonProcessingException jsonException) {
            log.error("Erro ao atualizar o registro no banco de dados", jsonException);
            throw new PedidoException(ErrosEnum.PEDIDO_FALHA_DURANTE_ATUALIZACAO);
        }
    }

    private void validarMudancaDePagamento(Pedido atual, Pedido novo) {
        log.info("Validando mudança de status de pagamento do pedido.");
        PagamentoPedidoEnum statusPagamentoAtual = PagamentoPedidoEnum.byId(atual.getStatusPagamento().getId());
        PagamentoPedidoEnum statusPagamentoNovo = PagamentoPedidoEnum.byId(novo.getStatusPagamento().getId());

        MudancaPagamentoPedido mudancaPagamentoPedido = new MudancaPagamentoPedidoPago(
                new MudancaPagamentoPedidoRecusado()
        );

        mudancaPagamentoPedido.validarMudancaDePagamento(statusPagamentoAtual, statusPagamentoNovo);
    }

}

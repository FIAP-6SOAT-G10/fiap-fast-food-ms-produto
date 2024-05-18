package br.com.fiap.techchallenge.usecases.pedido;

import br.com.fiap.techchallenge.adapters.pedido.PatchPedidoAdapter;
import br.com.fiap.techchallenge.domain.entities.Pedido;
import br.com.fiap.techchallenge.domain.entities.StatusPedido;
import br.com.fiap.techchallenge.domain.model.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.domain.usecases.pedido.PatchPedidoUseCase;
import br.com.fiap.techchallenge.infra.exception.PedidoException;
import br.com.fiap.techchallenge.infra.repositories.PedidoRepository;
import br.com.fiap.techchallenge.ports.pedido.PatchPedidoInboundPort;
import br.com.fiap.techchallenge.ports.pedido.PatchPedidoOutboundPort;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PatchPedidoUseCaseTest {

    @Mock
    PedidoRepository pedidoRepository;

    @Test
    void shouldAtualizarStatusDoPedidoParaEmPreparacao() throws JsonPointerException {
        Pedido retorno = new Pedido();
        retorno.setStatus(new StatusPedido("recebido"));
        when(pedidoRepository.findById(any())).thenReturn(Optional.of(retorno));

        Pedido novoRetorno = new Pedido();
        novoRetorno.setId(2L);
        novoRetorno.setStatus(new StatusPedido("preparacao"));
        when(pedidoRepository.saveAndFlush(any())).thenReturn(novoRetorno);

        PatchPedidoOutboundPort patchPedidoAdapter = new PatchPedidoAdapter(pedidoRepository);
        PatchPedidoInboundPort patchPedidoUseCase = new PatchPedidoUseCase(patchPedidoAdapter);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("preparacao", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/status");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        Pedido pedido = patchPedidoUseCase.atualizarStatusDoPedido("1", jsonPatch);

        assertEquals(StatusPedidoEnum.byStatus("preparacao"), StatusPedidoEnum.byStatus(pedido.getStatus().getNome()));
    }

    @Test
    void shouldAtualizarStatusDoPedidoParaPronto() throws JsonPointerException {
        Pedido retorno = new Pedido();
        retorno.setStatus(new StatusPedido("preparacao"));
        when(pedidoRepository.findById(any())).thenReturn(Optional.of(retorno));

        Pedido novoRetorno = new Pedido();
        novoRetorno.setId(3L);
        novoRetorno.setStatus(new StatusPedido("pronto"));
        when(pedidoRepository.saveAndFlush(any())).thenReturn(novoRetorno);

        PatchPedidoOutboundPort patchPedidoAdapter = new PatchPedidoAdapter(pedidoRepository);
        PatchPedidoInboundPort patchPedidoUseCase = new PatchPedidoUseCase(patchPedidoAdapter);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("pronto", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/status");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        Pedido pedido = patchPedidoUseCase.atualizarStatusDoPedido("1", jsonPatch);

        assertEquals(StatusPedidoEnum.byStatus("pronto"), StatusPedidoEnum.byStatus(pedido.getStatus().getNome()));
    }

    @Test
    void shouldAtualizarStatusDoPedidoParaFinalizado() throws JsonPointerException {
        Pedido retorno = new Pedido();
        retorno.setStatus(new StatusPedido("pronto"));
        when(pedidoRepository.findById(any())).thenReturn(Optional.of(retorno));

        Pedido novoRetorno = new Pedido();
        novoRetorno.setId(3L);
        novoRetorno.setStatus(new StatusPedido("finalizado"));
        when(pedidoRepository.saveAndFlush(any())).thenReturn(novoRetorno);

        PatchPedidoOutboundPort patchPedidoAdapter = new PatchPedidoAdapter(pedidoRepository);
        PatchPedidoInboundPort patchPedidoUseCase = new PatchPedidoUseCase(patchPedidoAdapter);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("finalizado", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/status");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);
        Pedido pedido = patchPedidoUseCase.atualizarStatusDoPedido("1", jsonPatch);

        assertEquals(StatusPedidoEnum.byStatus("finalizado"), StatusPedidoEnum.byStatus(pedido.getStatus().getNome()));
    }

    @Test
    void mustLancarPedidoExceptionQuandoAtualizarPedidoComIdentificadorInvalido() throws JsonPointerException {
        PatchPedidoOutboundPort patchPedidoAdapter = new PatchPedidoAdapter(pedidoRepository);
        PatchPedidoInboundPort patchPedidoUseCase = new PatchPedidoUseCase(patchPedidoAdapter);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("finalizado", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/status");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);

        assertThrows(PedidoException.class, () -> patchPedidoUseCase.atualizarStatusDoPedido("1A", jsonPatch));
    }

    @Test
    void mustLancarPedidoExceptionQuandoAtualizarPedidoComIdentificadorInexistente() throws JsonPointerException {
        when(pedidoRepository.findById(any())).thenReturn(Optional.empty());

        PatchPedidoOutboundPort patchPedidoAdapter = new PatchPedidoAdapter(pedidoRepository);
        PatchPedidoInboundPort patchPedidoUseCase = new PatchPedidoUseCase(patchPedidoAdapter);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeDTO = objectMapper.convertValue("finalizado", JsonNode.class);

        JsonPointer pointer = new JsonPointer("/status");
        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(pointer, nodeDTO));

        JsonPatch jsonPatch = new JsonPatch(operations);

        assertThrows(PedidoException.class, () -> patchPedidoUseCase.atualizarStatusDoPedido("99", jsonPatch));
    }

}
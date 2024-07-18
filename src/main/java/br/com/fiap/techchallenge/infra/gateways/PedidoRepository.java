package br.com.fiap.techchallenge.infra.gateways;

import br.com.fiap.techchallenge.application.gateways.IPedidoRepository;
import br.com.fiap.techchallenge.domain.ErrosEnum;
import br.com.fiap.techchallenge.domain.cors.statuspagamento.IMudancaPagamentoPedido;
import br.com.fiap.techchallenge.domain.cors.statuspagamento.MudancaPagamentoPedidoPago;
import br.com.fiap.techchallenge.domain.cors.statuspagamento.MudancaPagamentoPedidoRecusado;
import br.com.fiap.techchallenge.domain.cors.statuspedido.*;
import br.com.fiap.techchallenge.domain.entities.cliente.Cliente;
import br.com.fiap.techchallenge.domain.entities.pagamento.PagamentoPedidoEnum;
import br.com.fiap.techchallenge.domain.entities.pagamento.StatusPagamento;
import br.com.fiap.techchallenge.domain.entities.pagamento.StatusPagamentoEnum;
import br.com.fiap.techchallenge.domain.entities.pedido.Pedido;
import br.com.fiap.techchallenge.domain.entities.pedido.StatusPedido;
import br.com.fiap.techchallenge.domain.entities.pedido.StatusPedidoEnum;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.infra.exception.PedidoException;
import br.com.fiap.techchallenge.infra.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.infra.mapper.pedido.PedidoMapper;
import br.com.fiap.techchallenge.infra.mapper.produtopedido.ProdutoPedidoMapper;
import br.com.fiap.techchallenge.infra.persistence.ClienteEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.PedidoEntityRepository;
import br.com.fiap.techchallenge.infra.persistence.ProdutoPedidoRepository;
import br.com.fiap.techchallenge.infra.persistence.entities.ClienteEntity;
import br.com.fiap.techchallenge.infra.persistence.entities.PedidoEntity;
import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoPedidoEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@Slf4j
public class PedidoRepository implements IPedidoRepository {

    private final PedidoEntityRepository pedidoEntityRepository;
    private final PedidoMapper pedidoMapper;
    private final ClienteMapper clienteMapper;
    private final ClienteEntityRepository clienteEntityRepository;
    private final ProdutoPedidoRepository produtoPedidoRepository;
    private final ProdutoPedidoMapper produtoPedidoMapper;

    public PedidoRepository(PedidoEntityRepository pedidoEntityRepository, PedidoMapper pedidoMapper, ClienteMapper clienteMapper, ClienteEntityRepository clienteEntityRepository, ProdutoPedidoRepository produtoPedidoRepository, ProdutoPedidoMapper produtoPedidoMapper) {
        this.pedidoEntityRepository = pedidoEntityRepository;
        this.pedidoMapper = pedidoMapper;
        this.clienteMapper = clienteMapper;
        this.clienteEntityRepository = clienteEntityRepository;
        this.produtoPedidoRepository = produtoPedidoRepository;
        this.produtoPedidoMapper = produtoPedidoMapper;
    }

    @Override
    public Pedido criarPedido(Pedido pedido) {

        Cliente cliente = buscarCliente(pedido);
        PedidoEntity pedidoEntity = pedidoEntityRepository.saveAndFlush(pedidoMapper.fromDomainToEntity(Pedido
                .builder()
                .valor(pedido.getValor())
                .cliente(cliente == null ? null : cliente)
                .statusPagamento(StatusPagamento
                        .builder()
                        .id(StatusPagamentoEnum.PENDENTE.getId())
                        .nome(StatusPagamentoEnum.PENDENTE.getStatus())
                        .build())
                .status(StatusPedido
                        .builder()
                        .id(StatusPedidoEnum.RECEBIDO.getId())
                        .nome(StatusPedidoEnum.RECEBIDO.getStatus())
                        .build())
                .build()));

        List<ProdutoPedidoEntity> produtoPedidos = produtoPedidoMapper.fromDomainToEntity(pedido.getProdutoPedidos());
        produtoPedidos.parallelStream().forEach(item -> item.setPedido(pedidoEntity));
        produtoPedidoRepository.saveAll(produtoPedidos);
        return pedidoMapper.fromEntityToDomain(pedidoEntity);
    }

    @Override
    public Pedido atualizarStatusDoPedido(Long id, JsonPatch patch) {
        log.info("Atualizando status do pedido.");
        Optional<PedidoEntity> pedidoOptional = pedidoEntityRepository.findById(id);
        if (pedidoOptional.isEmpty()) {
            throw new PedidoException(ErrosEnum.PEDIDO_CODIGO_IDENTIFICADOR_INVALIDO);
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            Pedido pedidoAtual = pedidoMapper.fromEntityToDomain(pedidoOptional.get());
            JsonNode patched = patch.apply(objectMapper.convertValue(pedidoAtual, JsonNode.class));

            Pedido pedidoAtualizado = objectMapper.treeToValue(patched, Pedido.class);
            pedidoAtualizado.setCliente(pedidoAtual.getCliente());

            validarMudancaDeStatus(pedidoAtual, pedidoAtualizado);
            definirDataFinalizacaoPedido(pedidoAtualizado);

            return pedidoMapper.fromEntityToDomain(pedidoEntityRepository.saveAndFlush( pedidoMapper.fromDomainToEntity(pedidoAtualizado)));
        } catch (JsonPatchException | JsonProcessingException jsonException) {
            log.error("Erro ao atualizar o registro no banco de dados", jsonException);
            throw new PedidoException(ErrosEnum.PEDIDO_FALHA_DURANTE_ATUALIZACAO);
        }
    }

    private Cliente buscarCliente(Pedido request) {
        if(request.getCliente() == null || request.getCliente().getCpf().isBlank() || request.getCliente().getCpf().isEmpty()) {
            return null;
        }
        Optional<ClienteEntity> clienteEntity = clienteEntityRepository.findByCpf(request.getCliente().getCpf());
        if (clienteEntity.isEmpty()){
            throw new ClienteException(ErrosEnum.CLIENTE_CPF_NAO_EXISTE);
        }
        Cliente cliente = new ClienteMapper().fromEntityToDomain(clienteEntity.get());
        return cliente;
    }

    private void validarMudancaDeStatus(Pedido atual, Pedido novo) {
        log.info("Validando mudança de status do pedido.");
        StatusPedidoEnum statusAtual = StatusPedidoEnum.byId(atual.getStatus().getId());
        StatusPedidoEnum statusNovo = StatusPedidoEnum.byId(novo.getStatus().getId());

        IMudancaStatusPedido mudancaStatusPedido = new MudancaStatusPedidoRecebido(
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
    public Pedido atualizarPagamentoDoPedido(Long id, JsonPatch patch) {
        log.info("Atualizando status de pagamento do pedido.");
        Optional<PedidoEntity> pedidoOptional = pedidoEntityRepository.findById(id);
        if (pedidoOptional.isEmpty()) {
            throw new PedidoException(ErrosEnum.PEDIDO_CODIGO_IDENTIFICADOR_INVALIDO);
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            Pedido pedidoAtual = pedidoMapper.fromEntityToDomain(pedidoOptional.get());
            JsonNode patched = patch.apply(objectMapper.convertValue(pedidoAtual, JsonNode.class));

            Pedido pedidoAtualizado = objectMapper.treeToValue(patched, Pedido.class);

            validarMudancaDePagamento(pedidoAtual, pedidoAtualizado);

            return pedidoMapper.fromEntityToDomain(pedidoEntityRepository.saveAndFlush( pedidoMapper.fromDomainToEntity(pedidoAtualizado)));
        } catch (JsonPatchException | JsonProcessingException jsonException) {
            log.error("Erro ao atualizar o registro no banco de dados", jsonException);
            throw new PedidoException(ErrosEnum.PEDIDO_FALHA_DURANTE_ATUALIZACAO);
        }
    }

    private void validarMudancaDePagamento(Pedido atual, Pedido novo) {
        log.info("Validando mudança de status de pagamento do pedido.");
        PagamentoPedidoEnum statusPagamentoAtual = PagamentoPedidoEnum.byId(atual.getStatusPagamento().getId());
        PagamentoPedidoEnum statusPagamentoNovo = PagamentoPedidoEnum.byId(novo.getStatusPagamento().getId());

        IMudancaPagamentoPedido mudancaPagamentoPedido = new MudancaPagamentoPedidoPago(
                new MudancaPagamentoPedidoRecusado()
        );

        mudancaPagamentoPedido.validarMudancaDePagamento(statusPagamentoAtual, statusPagamentoNovo);
    }

    @Override
    public Pedido buscarPedidoPorId(Long id) {
        log.info("buscarPedidoPorId");
        try {
            PedidoEntity pedido = this.pedidoEntityRepository.findById(id).orElseThrow(() -> new PedidoException(ErrosEnum.PEDIDO_CODIGO_IDENTIFICADOR_INVALIDO));
            return pedidoMapper.fromEntityToDomain(pedido);
        } catch (Exception e) {
            log.error("O identificador informado não existe no banco de dados.");
            return null;
        }
    }

    @Override
    public List<Pedido> listarPedidos(Integer page, Integer size) {
        log.info("listarPedidos");
        List<PedidoEntity> listaPedido = new ArrayList<>();

        PageRequest pageable = PageRequest.of(page, size);
        Page<PedidoEntity> pagePedido = pedidoEntityRepository.findAll(pageable);

        if (pagePedido != null) {
            listaPedido.addAll(pagePedido.toList());
        }

        return pedidoMapper.fromListEntityToListDTO(listaPedido);
    }

    @Override
    public List<Pedido> listarPedidosPorStatus(String status, Integer page, Integer size) {
        StatusPedidoEnum statusPedidoEnum = StatusPedidoEnum.byStatus(status);

        List<Pedido> pedidos = this.listarPedidos(page, size);
        if (pedidos == null || pedidos.isEmpty()) {
            return new ArrayList<>();
        }

        Predicate<Pedido> byStatus = sp -> sp.getStatus().getId().equals(statusPedidoEnum.getId());
        return pedidos.stream().filter(byStatus).toList();
    }

    @Override
    public Pedido realizarCheckout(Long id) throws BaseException, InterruptedException {
        log.info("realizarCheckout");

        TimeUnit.SECONDS.sleep(5);

        Optional<PedidoEntity> pedidoOptional = pedidoEntityRepository.findById(id);
        if (pedidoOptional.isEmpty()) {
            throw new PedidoException(ErrosEnum.PEDIDO_CODIGO_IDENTIFICADOR_INVALIDO);
        }

        Pedido pedido = pedidoMapper.fromEntityToDomain(pedidoOptional.get());
        pedido.setStatus(new StatusPedido(StatusPedidoEnum.RECEBIDO.getStatus()));
        pedido.setStatusPagamento(new StatusPagamento(StatusPagamentoEnum.PAGO.getStatus()));

        return pedidoMapper.fromEntityToDomain(pedidoEntityRepository.saveAndFlush( pedidoMapper.fromDomainToEntity(pedido)));
    }
}
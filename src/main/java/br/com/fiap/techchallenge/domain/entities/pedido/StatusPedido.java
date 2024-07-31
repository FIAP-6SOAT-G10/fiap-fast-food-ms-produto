package br.com.fiap.techchallenge.domain.entities.pedido;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Objeto que representa o status do pedido dentro do sistema")
public class StatusPedido {

    @Schema(description = "Campo identificador único de status do pedido", example = "1")
    private Long id;

    @Schema(description = "Campo que informa o nome do status do pedido", example = "Em Preparo")
    private String nome;

    public StatusPedido() {}

    public StatusPedido(Long id) {
        StatusPedidoEnum statusPedidoEnum = StatusPedidoEnum.byId(id);
        this.id = statusPedidoEnum.getId();
        this.nome = statusPedidoEnum.getStatus();
    }

    public StatusPedido(String nome) {
        StatusPedidoEnum statusPedidoEnum = StatusPedidoEnum.byStatus(nome);
        this.id = statusPedidoEnum.getId();
        this.nome = statusPedidoEnum.getStatus();
    }

    public StatusPedido(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

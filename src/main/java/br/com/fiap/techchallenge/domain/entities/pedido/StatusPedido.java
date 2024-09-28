package br.com.fiap.techchallenge.domain.entities.pedido;

public class StatusPedido {

    private Long id;

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

    public String getNome() {
        return nome;
    }

}

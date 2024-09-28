package br.com.fiap.techchallenge.domain.entities.pedido;

public class ItemPedido {
    private Long id;
    private Long quantidade;

    public ItemPedido(Long id, Long quantidade) {
        this.id = id;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }


    public Long getQuantidade() {
        return quantidade;
    }

}
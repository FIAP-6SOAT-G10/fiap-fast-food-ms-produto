package br.com.fiap.techchallenge.domain.entities.pedido;

import br.com.fiap.techchallenge.infra.controllers.ItemDTO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static br.com.fiap.techchallenge.domain.ErrosEnum.PEDIDO_VAZIO;

@JsonPropertyOrder(
        {
            "lanches",
            "acompanhamento",
            "bebida",
            "sobremesa"
        })
@Data
public class Item {

    private List<ItemPedido> lanches = new ArrayList<>();
    private List<ItemPedido> acompanhamento = new ArrayList<>();
    private List<ItemPedido> bebida = new ArrayList<>();
    private List<ItemPedido> sobremesa = new ArrayList<>();

    public Item(ItemDTO itemDTO) {
        this.lanches = itemDTO.getLanches().stream().map(l -> new ItemPedido(l.getId(), l.getQuantidade())).toList();
        this.acompanhamento = itemDTO.getAcompanhamento().stream().map(l -> new ItemPedido(l.getId(), l.getQuantidade())).toList();
        this.bebida = itemDTO.getBebida().stream().map(l -> new ItemPedido(l.getId(), l.getQuantidade())).toList();
        this.sobremesa = itemDTO.getSobremesa().stream().map(l -> new ItemPedido(l.getId(), l.getQuantidade())).toList();
        validarItensPedido();
    }

    private void validarItensPedido() {
        if(this.lanches.isEmpty() &&
                this.acompanhamento.isEmpty() &&
                this.bebida.isEmpty() &&
                this.sobremesa.isEmpty()){
            throw new IllegalArgumentException(PEDIDO_VAZIO.getMessage());
        }
    }
}
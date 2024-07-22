package br.com.fiap.techchallenge.domain.entities.pedido;

import br.com.fiap.techchallenge.infra.controllers.pedido.ItemDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.fiap.techchallenge.domain.ErrosEnum.PEDIDO_VAZIO;

@JsonPropertyOrder(
        {
            "lanches",
            "acompanhamento",
            "bebida",
            "sobremesa"
        })
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    private List<ItemPedido> lanches;
    private List<ItemPedido> acompanhamento;
    private List<ItemPedido> bebida;
    private List<ItemPedido> sobremesa;

    public Item(ItemDTO itemDTO) {
        verificarSePedidoEstaVazio(itemDTO);
        this.lanches = itemDTO.getLanches().stream().map(lanche -> new ItemPedido(lanche.getId(), lanche.getQuantidade())).collect(Collectors.toList());
        this.acompanhamento = itemDTO.getAcompanhamento().stream().map(acompanhamento -> new ItemPedido(acompanhamento.getId(), acompanhamento.getQuantidade())).collect(Collectors.toList());
        this.bebida = itemDTO.getBebida().stream().map(bebida -> new ItemPedido(bebida.getId(), bebida.getQuantidade())).collect(Collectors.toList());
        this.sobremesa = itemDTO.getSobremesa().stream().map(sobremesa -> new ItemPedido(sobremesa.getId(), sobremesa.getQuantidade())).collect(Collectors.toList());
    }

    private void verificarSePedidoEstaVazio(ItemDTO itemDTO) {
        if(itemDTO.getLanches().isEmpty() &&
                itemDTO.getAcompanhamento().isEmpty() &&
                itemDTO.getBebida().isEmpty() &&
                itemDTO.getSobremesa().isEmpty()){
            throw new IllegalArgumentException(PEDIDO_VAZIO.getMessage());
        }
    }

    public List<ItemPedido> getLanches() {
        return lanches;
    }

    public List<ItemPedido> getAcompanhamento() {
        return acompanhamento;
    }

    public List<ItemPedido> getBebida() {
        return bebida;
    }

    public List<ItemPedido> getSobremesa() {
        return sobremesa;
    }
}
package br.com.fiap.techchallenge.domain.entities.cliente;

import br.com.fiap.techchallenge.domain.entities.pedido.Pedido;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Cliente {
    private String cpf;
    private String nome;
    private String email;
    private List<Pedido> pedidos;
}

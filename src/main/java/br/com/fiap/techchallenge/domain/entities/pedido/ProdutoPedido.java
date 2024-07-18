package br.com.fiap.techchallenge.domain.entities.pedido;

import br.com.fiap.techchallenge.domain.entities.produto.Produto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
public class ProdutoPedido {
    private Produto produto;
    private Pedido pedido;
    private BigDecimal valorTotal;
    private BigInteger quantidade;
}

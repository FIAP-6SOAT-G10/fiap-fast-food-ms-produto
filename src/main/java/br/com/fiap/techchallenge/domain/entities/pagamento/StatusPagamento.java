package br.com.fiap.techchallenge.domain.entities.pagamento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Objeto que representa o status do pagamento do pedido dentro do sistema")
public class StatusPagamento {

    @Schema(description = "Campo identificador único de status de pagamento", example = "1")
    private Long id;

    @Schema(description = "Campo que informa o nome do status de pagamento", example = "Pago")
    private String nome;

    public StatusPagamento(Long id) {
        StatusPagamentoEnum statusPagamentoEnum = StatusPagamentoEnum.byId(id);
        this.id = statusPagamentoEnum.getId();
        this.nome = statusPagamentoEnum.getStatus();
    }

    public StatusPagamento(String nome) {
        StatusPagamentoEnum statusPagamentoEnum = StatusPagamentoEnum.byStatus(nome);
        this.id = statusPagamentoEnum.getId();
        this.nome = statusPagamentoEnum.getStatus();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}

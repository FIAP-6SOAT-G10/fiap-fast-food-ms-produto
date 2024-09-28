package br.com.fiap.techchallenge.domain.entities.pagamento;

public class StatusPagamento {

    private Long id;

    private String nome;

    public StatusPagamento() {}

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

    public StatusPagamento(Long id, String nome) {
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

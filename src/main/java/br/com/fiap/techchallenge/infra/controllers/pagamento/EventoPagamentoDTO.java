package br.com.fiap.techchallenge.infra.controllers.pagamento;

public class EventoPagamentoDTO {

    private DadosPagamentoDTO data;
    private String action;
    private String type;

    public DadosPagamentoDTO getData() {
        return data;
    }

    public void setData(DadosPagamentoDTO data) {
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

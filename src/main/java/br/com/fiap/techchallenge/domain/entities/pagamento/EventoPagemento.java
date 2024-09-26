package br.com.fiap.techchallenge.domain.entities.pagamento;

public class EventoPagemento {

    private Long id;
    private String status;

    public EventoPagemento(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}

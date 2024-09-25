package br.com.fiap.techchallenge.domain.exceptions;

public class IntegrationException extends RuntimeException {

    public IntegrationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return String.format("Falha na integração sistemica %s", super.getMessage());
    }
}

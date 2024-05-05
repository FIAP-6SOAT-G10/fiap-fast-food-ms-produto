package br.com.fiap.techchallenge.infra.exception;

import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ClienteException extends RuntimeException {

    private String message;
    private ErrosEnum error;

    public ClienteException(ErrosEnum erro, String... violations) {
        super(String.format(erro.getMessage(), (Object) violations));
        this.error = erro;
        this.message = erro.getMessage();
    }

}

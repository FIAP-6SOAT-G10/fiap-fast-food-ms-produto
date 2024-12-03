package br.com.fiap.techchallenge.infra.exception;

import br.com.fiap.techchallenge.domain.ErrosEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseException extends RuntimeException {
    private final String message;
    private final ErrosEnum error;
    private final HttpStatus httpStatusCode;

    protected BaseException(ErrosEnum erro, String... violations) {
        super(String.format(erro.getMessage(), (Object) violations));
        this.error = erro;
        this.message = erro.getMessage();
        this.httpStatusCode = erro.getHttpStatusCode();
    }
}

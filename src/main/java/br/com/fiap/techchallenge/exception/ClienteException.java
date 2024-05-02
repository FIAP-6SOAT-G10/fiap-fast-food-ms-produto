package br.com.fiap.techchallenge.exception;

import br.com.fiap.techchallenge.model.enums.Erros;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ClienteException extends RuntimeException{

    private String message;
    private Erros error;

    public ClienteException(Erros erro , String... violations) {
        super(String.format(erro.getMessage(),violations));
        this.error = erro;
        this.message = erro.getMessage();
    }

}

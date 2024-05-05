package br.com.fiap.techchallenge.infra.exception;

import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CategoriaException extends BaseException {

    public CategoriaException(ErrosEnum erro, String... violations) {
        super(erro, violations);
    }
}

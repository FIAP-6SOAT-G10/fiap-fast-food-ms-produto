package br.com.fiap.techchallenge.infra.exception;

import br.com.fiap.techchallenge.domain.ErrosEnum;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProdutoException extends BaseException {

    public ProdutoException(ErrosEnum erro, String... violations) {
        super(erro, violations);
    }

}

package br.com.fiap.techchallenge.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;


@Getter
@RequiredArgsConstructor
public enum Erros {

    ERRO_PARAMETROS("001","Parametro mandatorio não foi enviado", Level.ERROR)
    ;

    private final String code;
    private final String message;
    private final Level logLevel;
}

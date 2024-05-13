package br.com.fiap.techchallenge.domain.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum ErrosEnum {

    /* ERROS GENÉRICOS 001 - 99 */
    ERRO_PARAMETROS("001","Parametro mandatorio não foi enviado", Level.ERROR, HttpStatus.BAD_REQUEST),

    /* ERROS DE CATEGORIA 100 - 199 */
    CATEGORIA_INVALIDA("100", "Categoria inválida.", Level.ERROR, HttpStatus.BAD_REQUEST),

    /* ERROS DE PRODUTO 200 - 299 */
    PRODUTO_NOME_OBRIGATORIO("200", "O campo nome é obrigatório na criação de um produto.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PRODUTO_DESCRICAO_OBRIGATORIO("201", "O campo descricao é obrigatório na criação de um produto.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PRODUTO_PRECO_OBRIGATORIO("202", "O campo preco é obrigatório na criação de um produto.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PRODUTO_IMAGEM_OBRIGATORIO("203", "O campo imagem é obrigatório na criação de um produto.", Level.ERROR, HttpStatus.BAD_REQUEST),

    /* ERROS DE CLIENTE  */
    CLIENTE_CPF_OBRIGATORIO("300", "CPF inválido.", Level.ERROR, HttpStatus.NOT_FOUND),
    CLIENTE_CPF_EXISTENTE("304", "O campo cpf é obrigatório na atualização de um cliente.", Level.ERROR, HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final Level logLevel;
    private final HttpStatus httpStatusCode;
}

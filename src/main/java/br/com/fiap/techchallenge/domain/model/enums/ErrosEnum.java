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
    PRODUTO_NOME_OBRIGATORIO("200", "O campo nome é obrigatório.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PRODUTO_DESCRICAO_OBRIGATORIO("201", "O campo descricao é obrigatório.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PRODUTO_PRECO_OBRIGATORIO("202", "O campo preco é obrigatório.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PRODUTO_IMAGEM_OBRIGATORIO("203", "O campo imagem é obrigatório.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PRODUTO_CODIGO_IDENTIFICADOR_INVALIDO("204", "O identificador do produto é inválido", Level.ERROR, HttpStatus.BAD_REQUEST),
    PRODUTO_NAO_ENCONTRADO("205", "O identificador informado não está relacionado a nenhum produto existente.", Level.ERROR, HttpStatus.NOT_FOUND),
    PRODUTO_FALHA_DURANTE_ATUALIZACAO("206", "Erro durante a atualização do produto no banco de dados.", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUTO_FALHA_GENERICA("207", "Erro genérico ao atualizar o produto.", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUTO_CATEGORIA_OBRIGATORIO("208", "O campo categoria é obrigatório.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PRODUTO_CATEGORIA_NAO_ENCONTRADO("209", "Não existem produtos cadastrados para a categoria informada.", Level.ERROR, HttpStatus.NO_CONTENT),
      
    /* ERROS DE CLIENTE 300 - 399 */
    CLIENTE_CPF_INVALIDO("300", "CPF inválido.", Level.ERROR, HttpStatus.NOT_FOUND),
    CLIENTE_JA_CADASTRADO("301", "O cliente com o CPF informado já existe", Level.ERROR, HttpStatus.BAD_REQUEST),
    CLIENTE_EMAIL_OBRIGATORIO("302", "O email do cliente é obrigatório", Level.ERROR, HttpStatus.BAD_REQUEST),
    CLIENTE_CPF_OBRIGATORIO("303", "O CPF do cliente é obrigatório", Level.ERROR, HttpStatus.BAD_REQUEST),
    CLIENTE_CPF_NAO_EXISTENTE("304", "O campo cpf é obrigatório na atualização de um cliente.", Level.ERROR, HttpStatus.BAD_REQUEST),
    CLIENTE_NOME_OBRIGATORIO("305", "O nome do cliente é obrigatório", Level.ERROR, HttpStatus.BAD_REQUEST),

    /* ERROS DE PEDIDO 400 - 499 */
    PEDIDO_INVALIDO("400", "Pedido inválido.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PEDIDO_VAZIO("401", "Todos os itens do pedido vieram vazios por favor escolha algum item e tente novamente.", Level.ERROR, HttpStatus.BAD_REQUEST)

    ;
    private final String code;
    private final String message;
    private final Level logLevel;
    private final HttpStatus httpStatusCode;
}

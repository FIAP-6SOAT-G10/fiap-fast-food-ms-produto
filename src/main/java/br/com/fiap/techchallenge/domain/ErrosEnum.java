package br.com.fiap.techchallenge.domain;

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
    CLIENTE_CPF_NAO_EXISTE("306", "CPF informado não existe", Level.ERROR, HttpStatus.BAD_REQUEST),
    CLIENTE_CODIGO_IDENTIFICADOR_INVALIDO("307", "O identificador do cliente é inválido", Level.ERROR, HttpStatus.BAD_REQUEST),
    CLIENTE_FALHA_DURANTE_ATUALIZACAO("308", "Erro durante a atualização do cliente no banco de dados.", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    CLIENTE_FALHA_GENERICA("309", "Erro genérico ao atualizar o cliente.", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    CLIENTE_NAO_ENCONTRADO("205", "O identificador informado não está relacionado a nenhum cliente existente.", Level.ERROR, HttpStatus.NOT_FOUND),

    /* ERROS DE PEDIDO 400 - 499 */
    PEDIDO_NAO_ENCONTRADO("400", "O pedido informado não foi localizado.", Level.ERROR, HttpStatus.NOT_FOUND),
    PEDIDO_FALHA_DURANTE_ATUALIZACAO("401", "Erro durante a atualização do status do pedido no banco de dados.", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    PEDIDO_CODIGO_IDENTIFICADOR_INVALIDO("402", "O identificador do pedido é inválido.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PEDIDO_STATUS_OBRIGATORIO("403", "O status à ser atualizado é obrigatório.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PEDIDO_PAGAMENTO_PAGAMENTO_OBRIGATORIO("403", "O status de pagamanto à ser atualizado é obrigatório.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PEDIDO_STATUS_NAO_ENCONTRADO("404", "Não é possível atualizar o pedido para o status informado.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PEDIDO_PAGAMENTO_PAGAMENTO_NAO_ENCONTRADO("404", "Não é possível atualizar o pedido para o status de pagamento informado.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PEDIDO_STATUS_RECEBIDO_EM_PREPARACAO("405", "Pedidos no status 'Recebido' só podem avançar para o status 'Em preparação'.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PEDIDO_STATUS_EM_PREPARACAO_PRONTO("406", "Pedidos no status 'Em preparação' só podem avançar para o status 'Pronto'.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PEDIDO_STATUS_PRONTO_FINALIZADO("407", "Pedidos no status 'Pronto' só podem avançar para o status 'Finalizado'.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PEDIDO_PAGAMENTO_PAGO("407", "Pedidos no status 'Pago' não podem ser alterados.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PEDIDO_PAGAMENTO_RECUSADO("407", "Pedidos no status 'Recusado' não podem ser alterados.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PEDIDO_STATUS_FINALIZADO("408", "Pedidos no status 'Finalizado' não podem ser alterados.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PEDIDO_VAZIO("409", "Todos os itens do pedido vieram vazios por favor escolha algum item e tente novamente.", Level.ERROR, HttpStatus.BAD_REQUEST),
    PEDIDO_FALHA_GENERICA("499", "Erro genérico ao atualizar o status do pedido.", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR)

    ;
    private final String code;
    private final String message;
    private final Level logLevel;
    private final HttpStatus httpStatusCode;
}

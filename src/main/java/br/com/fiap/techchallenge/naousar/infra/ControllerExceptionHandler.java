package br.com.fiap.techchallenge.naousar.infra;

import br.com.fiap.techchallenge.domain.ErrorMessage;
import br.com.fiap.techchallenge.domain.ErrorsResponse;
import br.com.fiap.techchallenge.infra.exception.CategoriaException;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.infra.exception.PedidoException;
import br.com.fiap.techchallenge.infra.exception.ProdutoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static br.com.fiap.techchallenge.domain.ErrosEnum.ERRO_PARAMETROS;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class ControllerExceptionHandler {
    private static final String MESSAGE_EXCEPTION = "ControllerExceptionHandler CODE={} MESSAGE={}";

    private static ErrorsResponse buildErrors(String code, String message) {
        return ErrorsResponse
                .builder()
                .errors(Collections.singletonList(
                        ErrorMessage
                                .builder()
                                .code(code)
                                .message(message)
                                .build()
                ))
                .build();
    }
    @ExceptionHandler({ClienteException.class})
    public ResponseEntity<ErrorsResponse> clienteException(ClienteException ex) {
        log.error("exception {}" , ex);
        log.info(MESSAGE_EXCEPTION, ex.getError().getCode(), ex.getError().getMessage());
        return ResponseEntity
                .status(ex.getHttpStatusCode())
                .body(
                        buildErrors(ex.getError().getCode(), ex.getError().getMessage())
                );
    }

    @ExceptionHandler({CategoriaException.class})
    public ResponseEntity<ErrorsResponse> categoriaException(CategoriaException ex) {
        log.error("exception {}" , ex);
        log.info(MESSAGE_EXCEPTION, ex.getError().getCode(), ex.getError().getMessage());
        return ResponseEntity
                .status(ex.getHttpStatusCode())
                .body(
                        buildErrors(ex.getError().getCode(), ex.getError().getMessage())
                );
    }

    @ExceptionHandler({ProdutoException.class})
    public ResponseEntity<ErrorsResponse> produtoException(ProdutoException ex) {
        log.error("exception {}" , ex);
        log.info(MESSAGE_EXCEPTION, ex.getError().getCode(), ex.getError().getMessage());
        return ResponseEntity
                .status(ex.getHttpStatusCode())
                .body(
                        buildErrors(ex.getError().getCode(), ex.getError().getMessage())
                );
    }

    @ExceptionHandler({PedidoException.class})
    public ResponseEntity<ErrorsResponse> pedidoException(PedidoException ex) {
        log.error("exception {}" , ex);
        log.info(MESSAGE_EXCEPTION, ex.getError().getCode(), ex.getError().getMessage());
        return ResponseEntity
                .status(ex.getHttpStatusCode())
                .body(
                        buildErrors(ex.getError().getCode(), ex.getError().getMessage())
                );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorsResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("methodArgumentNotValidException {}" , ex);
        List<ErrorMessage> errorMessages = new ArrayList<>();
        ex.getFieldErrors().stream().forEach(error -> {
            errorMessages.add(ErrorMessage.builder().code(ERRO_PARAMETROS.getCode()).message(error.getDefaultMessage()).build());
        });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorsResponse
                                .builder()
                                .errors(errorMessages)
                                .build()
                );
    }

}

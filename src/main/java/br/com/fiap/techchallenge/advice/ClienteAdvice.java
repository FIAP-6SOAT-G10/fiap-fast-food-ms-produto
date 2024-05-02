package br.com.fiap.techchallenge.advice;

import br.com.fiap.techchallenge.exception.ClienteException;
import br.com.fiap.techchallenge.model.ErrorMessage;
import br.com.fiap.techchallenge.model.ErrorsResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static br.com.fiap.techchallenge.model.enums.Erros.ERRO_PARAMETROS;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@AllArgsConstructor
public class ClienteAdvice {

    private static final String MESSAGE_EXCEPTION = "ClienteExceptoion CODE={} MESSAGE={}";
    private static  ErrorsResponse buildErros(String code , String message) {
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
    public ResponseEntity<ErrorsResponse> exception(ClienteException ex) {
        log.error("exception {}" , ex);
        log.info(MESSAGE_EXCEPTION,ex.getError().getCode(),ex.getError().getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        buildErros(ex.getError().getCode(),ex.getError().getMessage())
                );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorsResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("methodArgumentNotValidException {}" , ex);
        List<ErrorMessage> errorMessages = new ArrayList<>();
        ex.getFieldErrors().stream().forEach(it -> {
            errorMessages.add(ErrorMessage.builder().code(ERRO_PARAMETROS.getCode()).message(it.getDefaultMessage()).build());
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

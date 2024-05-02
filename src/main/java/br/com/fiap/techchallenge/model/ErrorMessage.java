package br.com.fiap.techchallenge.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {

    private String code;
    private String message;
}

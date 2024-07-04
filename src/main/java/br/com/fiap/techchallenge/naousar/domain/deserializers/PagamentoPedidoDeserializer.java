package br.com.fiap.techchallenge.naousar.domain.deserializers;

import br.com.fiap.techchallenge.infra.persistence.entities.StatusPagamento;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class PagamentoPedidoDeserializer extends JsonDeserializer<StatusPagamento> {
    @Override
    public StatusPagamento deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return new StatusPagamento(jsonParser.getText());
    }
}

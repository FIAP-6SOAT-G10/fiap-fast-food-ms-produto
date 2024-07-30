package br.com.fiap.techchallenge.infra.deserializers;

import br.com.fiap.techchallenge.domain.entities.pagamento.StatusPagamento;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class StatusPagamentoDeserializer extends JsonDeserializer<StatusPagamento> {
    @Override
    public StatusPagamento deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return new StatusPagamento(jsonParser.getText());
    }
}

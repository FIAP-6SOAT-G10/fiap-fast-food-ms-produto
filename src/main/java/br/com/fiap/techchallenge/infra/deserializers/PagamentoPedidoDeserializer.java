package br.com.fiap.techchallenge.infra.deserializers;

import br.com.fiap.techchallenge.infra.persistence.entities.StatusPagamentoEntity;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class PagamentoPedidoDeserializer extends JsonDeserializer<StatusPagamentoEntity> {
    @Override
    public StatusPagamentoEntity deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return new StatusPagamentoEntity(jsonParser.getText());
    }
}

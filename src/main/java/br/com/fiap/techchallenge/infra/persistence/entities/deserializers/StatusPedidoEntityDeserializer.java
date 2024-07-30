package br.com.fiap.techchallenge.infra.persistence.entities.deserializers;

import br.com.fiap.techchallenge.infra.persistence.entities.StatusPedidoEntity;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class StatusPedidoEntityDeserializer extends JsonDeserializer<StatusPedidoEntity> {
    @Override
    public StatusPedidoEntity deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException, IOException {
        return new StatusPedidoEntity(jsonParser.getText());
    }
}
package br.com.fiap.techchallenge.infra.deserializers;

import br.com.fiap.techchallenge.domain.entities.pedido.StatusPedido;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class StatusPedidoDeserializer extends JsonDeserializer<StatusPedido> {
    @Override
    public StatusPedido deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException, IOException {
        return new StatusPedido(jsonParser.getText());
    }
}
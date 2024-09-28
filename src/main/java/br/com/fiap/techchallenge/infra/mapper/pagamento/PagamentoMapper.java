package br.com.fiap.techchallenge.infra.mapper.pagamento;

import br.com.fiap.techchallenge.domain.entities.pagamento.EventoPagemento;
import br.com.fiap.techchallenge.infra.controllers.pagamento.DadosPagamentoDTO;
import br.com.fiap.techchallenge.infra.controllers.pagamento.EventoPagamentoDTO;

public class PagamentoMapper {

    public EventoPagemento fromDTOToDomain(EventoPagamentoDTO eventoPagamentoDTO) {
        DadosPagamentoDTO data = eventoPagamentoDTO.getData();
        return new EventoPagemento(data.getId(), data.getStatus());
    }
}

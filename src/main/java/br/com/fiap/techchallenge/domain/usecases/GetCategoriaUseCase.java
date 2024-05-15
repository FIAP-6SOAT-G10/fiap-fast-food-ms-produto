package br.com.fiap.techchallenge.domain.usecases;

import br.com.fiap.techchallenge.domain.valueobjects.CategoriaDTO;
import br.com.fiap.techchallenge.ports.GetCategoriaInboundPort;
import br.com.fiap.techchallenge.ports.GetCategoriaOutboundPort;

import java.util.List;

public class GetCategoriaUseCase implements GetCategoriaInboundPort {

    private final GetCategoriaOutboundPort port;

    public GetCategoriaUseCase(GetCategoriaOutboundPort port) {
        this.port = port;
    }

    @Override
    public List<CategoriaDTO> listarCategorias() {
        return this.port.listarCategorias();
    }
}

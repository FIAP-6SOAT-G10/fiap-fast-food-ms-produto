package br.com.fiap.techchallenge.domain.usecases.categoria;

import br.com.fiap.techchallenge.domain.valueobjects.CategoriaDTO;
import br.com.fiap.techchallenge.ports.categoria.GetCategoriaInboundPort;
import br.com.fiap.techchallenge.ports.categoria.GetCategoriaOutboundPort;

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

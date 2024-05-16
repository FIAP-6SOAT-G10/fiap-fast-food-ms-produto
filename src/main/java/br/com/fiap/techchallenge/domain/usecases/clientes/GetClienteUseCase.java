package br.com.fiap.techchallenge.domain.usecases.clientes;

import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.ports.GetUsuarioInboundPort;
import br.com.fiap.techchallenge.ports.GetUsuarioOutboundPort;

import java.util.List;

public class GetClienteUseCase implements GetUsuarioInboundPort {

    private final GetUsuarioOutboundPort port;

    public GetClienteUseCase(GetUsuarioOutboundPort port) {
        this.port = port;
    }

    @Override
    public List<ClienteDTO> listarClientes(Integer page, Integer size, String email, String cpf) {
        return this.port.listarClientes(page, size, email, cpf);
    }
}

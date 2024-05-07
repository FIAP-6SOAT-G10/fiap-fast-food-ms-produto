package br.com.fiap.techchallenge.ports;

import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;

public interface UpdateUsuarioInboundPort {

    ClienteDTO atualizarClientes(ClienteDTO clienteDTO);
}

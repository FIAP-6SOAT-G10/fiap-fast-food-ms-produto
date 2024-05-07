package br.com.fiap.techchallenge.ports;

import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;

public interface UpdateUsuarioOutboundPort {
    ClienteDTO atualizarClientes(ClienteDTO clienteDTO);
}
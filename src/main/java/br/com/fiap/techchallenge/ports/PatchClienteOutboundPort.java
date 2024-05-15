package br.com.fiap.techchallenge.ports;

import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;

public interface PatchClienteOutboundPort {
    ClienteDTO atualizarClientes(ClienteDTO clienteDTO);
}
package br.com.fiap.techchallenge.ports.cliente;

import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;

public interface PostClienteInboundPort {
    ClienteDTO salvarCliente(ClienteDTO dto);
}

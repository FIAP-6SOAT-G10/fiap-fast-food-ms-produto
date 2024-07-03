package br.com.fiap.techchallenge.naousar.ports.cliente;

import br.com.fiap.techchallenge.naousar.domain.valueobjects.ClienteDTO;

public interface PostClienteInboundPort {
    ClienteDTO salvarCliente(ClienteDTO dto);
}

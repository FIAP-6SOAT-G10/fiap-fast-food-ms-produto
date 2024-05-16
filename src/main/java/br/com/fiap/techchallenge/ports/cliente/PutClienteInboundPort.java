package br.com.fiap.techchallenge.ports.cliente;

import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;

public interface PutClienteInboundPort {

    ClienteDTO atualizarClientes(ClienteDTO clienteDTO);
}

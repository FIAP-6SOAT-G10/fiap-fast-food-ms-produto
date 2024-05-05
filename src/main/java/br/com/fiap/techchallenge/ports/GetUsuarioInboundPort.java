package br.com.fiap.techchallenge.ports;

import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;

import java.util.List;

public interface GetUsuarioInboundPort {
    List<ClienteDTO> listarClientes(Integer page, Integer size, String email, String cpf);
}

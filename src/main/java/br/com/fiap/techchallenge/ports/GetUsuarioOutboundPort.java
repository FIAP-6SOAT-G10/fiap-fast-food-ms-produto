package br.com.fiap.techchallenge.ports;

import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;

import java.util.List;

public interface GetUsuarioOutboundPort {
    List<ClienteDTO> listarClientes(Integer page, Integer size, String email, String cpf);
}
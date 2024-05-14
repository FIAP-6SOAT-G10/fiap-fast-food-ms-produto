package br.com.fiap.techchallenge.domain.usecases;

import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.ports.PostClienteInboundPort;
import br.com.fiap.techchallenge.ports.PostClienteOutboundPort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostClienteUseCase implements PostClienteInboundPort {

    private final PostClienteOutboundPort port;

    public PostClienteUseCase(PostClienteOutboundPort port) {
        this.port = port;
    }

    @Override
    public ClienteDTO salvarCliente(ClienteDTO clienteDTO) {
        log.info("salvarCliente {} " , clienteDTO);
        validarDados(clienteDTO);
        return port.salvarCliente(clienteDTO);
    }

    private void validarDados(ClienteDTO clienteDTO) throws BaseException {
        if (valueIsNullOrEmpty(clienteDTO.getNome())) {
            throw new ClienteException(ErrosEnum.CLIENTE_NOME_OBRIGATORIO);
        }

        if (valueIsNullOrEmpty(clienteDTO.getEmail())) {
            throw new ClienteException(ErrosEnum.CLIENTE_EMAIL_OBRIGATORIO);
        }

        if (valueIsNullOrEmpty(clienteDTO.getCpf())) {
            throw new ClienteException(ErrosEnum.CLIENTE_CPF_INVALIDO);
        }
        port.validarCpfCadastrado(clienteDTO.getCpf());
    }

    public static boolean valueIsNullOrEmpty(String value){
        return (value == null || value.isEmpty());
    }
}

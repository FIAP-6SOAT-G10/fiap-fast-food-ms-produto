package br.com.fiap.techchallenge.domain.usecases;

import br.com.fiap.techchallenge.domain.model.enums.ErrosEnum;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.ports.PatchClienteInboundPort;
import br.com.fiap.techchallenge.ports.PatchClienteOutboundPort;

public class PatchClienteUseCase implements PatchClienteInboundPort {

    private final PatchClienteOutboundPort port;

    public PatchClienteUseCase(PatchClienteOutboundPort port) {
        this.port = port;
    }

    @Override
    public ClienteDTO atualizarClientes(ClienteDTO clienteDTO) {
        validar(clienteDTO);
        return this.port.atualizarClientes(clienteDTO);
    }

    private void validar(ClienteDTO clienteDTO) throws BaseException {
        if (clienteDTO.getCpf() == null || clienteDTO.getCpf().isEmpty()) {
            throw new ClienteException(ErrosEnum.CLIENTE_CPF_INVALIDO, "O cpf do cliente é obrigatório");
        } else if (clienteDTO.getNome() == null) {
            throw new ClienteException(ErrosEnum.CLIENTE_NOME_OBRIGATORIO);
        } else if (clienteDTO.getEmail() == null) {
            throw new ClienteException(ErrosEnum.CLIENTE_EMAIL_OBRIGATORIO);

        }
    }
}

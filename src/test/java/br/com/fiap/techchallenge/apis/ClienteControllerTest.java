package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.PostClienteAdapter;
import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.model.mapper.ClienteMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.infra.repositories.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClienteControllerTest {

    @InjectMocks
    PostClienteAdapter adapter;

    @Mock
    ClienteRepository repository;

    @Mock
    ClienteMapper mapper;

    @InjectMocks
    ClienteController controller;

    @Test
    void shouldCadastrarClienteComSucesso() throws BaseException {
        when(repository.findByCpf("00000000001")).thenReturn(criarClienteOptional());
        when(adapter.salvarCliente(any())).thenReturn(criarClienteRetorno());
        ClienteDTO clienteRequest = ClienteDTO
                .builder()
                .nome("John Doo")
                .email("email@email")
                .cpf("00000000000")
                .build();

        assertEquals(201, controller.cadastrar(clienteRequest).getStatusCode().value());
    }

    @Test
    void mustLancarClienteExceptionAoCadastrarClienteComCPFQueJaExiste() throws BaseException {
        when(repository.findByCpf("00000000000")).thenReturn(criarClienteOptional());
        ClienteDTO clienteRequest = ClienteDTO
                .builder()
                .nome("John Doo")
                .email("email@email")
                .cpf("00000000000")
                .build();

        assertThrows(ClienteException.class, () -> controller.cadastrar(clienteRequest));
    }

    private ClienteDTO criarClienteRetorno() {
        return ClienteDTO
                .builder()
                .nome("John Doo")
                .email("email@email")
                .cpf("00000000000")
                .build();
    }

    private Optional<Cliente> criarClienteOptional() {
        return Optional.of(Cliente
                .builder()
                .nome("John Doo")
                .email("email@email")
                .cpf("00000000000")
                .build());
    }

}

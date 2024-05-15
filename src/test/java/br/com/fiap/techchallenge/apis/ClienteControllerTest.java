package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.GetClienteAdapter;
import br.com.fiap.techchallenge.adapters.PatchClienteAdapter;
import br.com.fiap.techchallenge.adapters.PutClienteAdapter;
import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.model.mapper.ClienteMapper;
import br.com.fiap.techchallenge.domain.usecases.PatchClienteUseCase;
import br.com.fiap.techchallenge.domain.usecases.PutClienteUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.infra.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import br.com.fiap.techchallenge.adapters.PostClienteAdapter;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private GetClienteAdapter getClienteAdapter;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteController clienteController;

    @InjectMocks
    PostClienteAdapter postClienteAdapter;

    @InjectMocks
    ClienteController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCadastrarClienteComSucesso() throws BaseException {
        when(clienteRepository.findByCpf("00000000001")).thenReturn(criarClienteOptional());
        when(postClienteAdapter.salvarCliente(any())).thenReturn(criarClienteRetorno());
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
        when(clienteRepository.findByCpf("00000000000")).thenReturn(criarClienteOptional());
        ClienteDTO clienteRequest = ClienteDTO
                .builder()
                .nome("John Doo")
                .email("email@email")
                .cpf("00000000000")
                .build();

        assertThrows(ClienteException.class, () -> controller.cadastrar(clienteRequest));
    }

    @Test
    void mustLancarClienteExceptionAoCadastrarClienteComNomeVazio() throws BaseException {
        ClienteDTO clienteRequest = ClienteDTO
                .builder()
                .email("email@email")
                .cpf("00000000000")
                .build();
        assertThrows(ClienteException.class, () -> controller.cadastrar(clienteRequest));
    }

    @Test
    void mustLancarClienteExceptionAoCadastrarClienteComEmailVazio() throws BaseException {
        ClienteDTO clienteRequest = ClienteDTO
                .builder()
                .nome("John Doo")
                .cpf("00000000000")
                .build();
        assertThrows(ClienteException.class, () -> controller.cadastrar(clienteRequest));
    }

    @Test
    void mustLancarClienteExceptionAoCadastrarClienteComCPFVazio() throws BaseException {
        ClienteDTO clienteRequest = ClienteDTO
                .builder()
                .nome("John Doo")
                .email("email@email")
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

    @Test
    void shouldReturnNoContentWhenListarTodosClientesAndNoClientesExist() {
        when(getClienteAdapter.listarClientes(0, 10, null, null)).thenReturn(Collections.emptyList());
        ResponseEntity<List<ClienteDTO>> response = clienteController.listarTodosClientes(0, 10, null, null);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldReturnOkWhenListarTodosClientesAndClientesExist() {
        List<ClienteDTO> clientes = Collections.singletonList(new ClienteDTO());
        when(getClienteAdapter.listarClientes(0, 10, null, null)).thenReturn(clientes);
        ResponseEntity<List<ClienteDTO>> response = clienteController.listarTodosClientes(0, 10, null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
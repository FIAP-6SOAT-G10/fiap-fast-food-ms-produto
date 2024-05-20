package br.com.fiap.techchallenge.apis.cliente;

import br.com.fiap.techchallenge.apis.ClienteController;
import br.com.fiap.techchallenge.domain.entities.Cliente;
import br.com.fiap.techchallenge.domain.model.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import br.com.fiap.techchallenge.infra.repositories.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClienteControllerTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Test
    void shouldCadastrarClienteComSucesso() throws BaseException {
        when(clienteRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(clienteRepository.saveAndFlush(any())).thenReturn(new Cliente());
        ClienteDTO clienteRequest = ClienteDTO
                .builder()
                .nome("John Doo")
                .email("email@email")
                .cpf("00000000001")
                .build();

        ClienteController clienteController = new ClienteController(clienteRepository, clienteMapper);
        assertEquals(201, clienteController.cadastrar(clienteRequest).getStatusCode().value());
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

        ClienteController clienteController = new ClienteController(clienteRepository, clienteMapper);
        assertThrows(ClienteException.class, () -> clienteController.cadastrar(clienteRequest));
    }

    @Test
    void mustLancarClienteExceptionAoCadastrarClienteComNomeVazio() throws BaseException {
        ClienteDTO clienteRequest = ClienteDTO
                .builder()
                .email("email@email")
                .cpf("00000000000")
                .build();

        ClienteController clienteController = new ClienteController(clienteRepository, clienteMapper);
        assertThrows(ClienteException.class, () -> clienteController.cadastrar(clienteRequest));
    }

    @Test
    void mustLancarClienteExceptionAoCadastrarClienteComEmailVazio() throws BaseException {
        ClienteDTO clienteRequest = ClienteDTO
                .builder()
                .nome("John Doo")
                .cpf("00000000000")
                .build();

        ClienteController clienteController = new ClienteController(clienteRepository, clienteMapper);
        assertThrows(ClienteException.class, () -> clienteController.cadastrar(clienteRequest));
    }

    @Test
    void mustLancarClienteExceptionAoCadastrarClienteComCPFVazio() throws BaseException {
        ClienteDTO clienteRequest = ClienteDTO
                .builder()
                .nome("John Doo")
                .email("email@email")
                .build();

        ClienteController clienteController = new ClienteController(clienteRepository, clienteMapper);
        assertThrows(ClienteException.class, () -> clienteController.cadastrar(clienteRequest));
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
        when(clienteRepository.findAll(Pageable.ofSize(10))).thenReturn(Page.empty());

        ClienteController clienteController = new ClienteController(clienteRepository, clienteMapper);
        ResponseEntity<List<ClienteDTO>> response = clienteController.listarTodosClientes(0, 10, null, null);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldReturnOkWhenListarTodosClientesAndClientesExist() {
        when(clienteRepository.findAll(Pageable.ofSize(10))).thenReturn(new PageImpl<>(List.of(new Cliente())));

        ClienteController clienteController = new ClienteController(clienteRepository, clienteMapper);
        ResponseEntity<List<ClienteDTO>> response = clienteController.listarTodosClientes(0, 10, null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
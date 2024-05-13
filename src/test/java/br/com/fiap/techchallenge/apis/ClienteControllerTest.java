package br.com.fiap.techchallenge.apis;

import br.com.fiap.techchallenge.adapters.GetClienteAdapter;
import br.com.fiap.techchallenge.adapters.PatchClienteAdapter;
import br.com.fiap.techchallenge.domain.usecases.PatchClienteUseCase;
import br.com.fiap.techchallenge.domain.valueobjects.ClienteDTO;
import br.com.fiap.techchallenge.infra.exception.ClienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClienteControllerTest {

    @Mock
    private GetClienteAdapter getClienteAdapter;

    @Mock
    private PatchClienteAdapter patchClienteAdapter;
    @Mock
    private PatchClienteUseCase patchClienteUseCase;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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

    @Test
    void shouldReturnOkWhenAtualizarClientesAndClienteExists() {
        ClienteDTO clienteDTO = new ClienteDTO("42321973897", "Teste", "email@email.com");
        when(patchClienteAdapter.atualizarClientes(clienteDTO)).thenReturn(clienteDTO);
        ResponseEntity<ClienteDTO> response = clienteController.atualizarClientes(clienteDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldReturnNotFoundWhenAtualizarClientesAndClienteDoesNotExist() {
        ClienteDTO clienteDTO = new ClienteDTO("12345678901", "Teste", "email@email.com");
        when(patchClienteAdapter.atualizarClientes(clienteDTO)).thenReturn(null);
        ResponseEntity<ClienteDTO> response = clienteController.atualizarClientes(clienteDTO);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldReturnNotFoundWhenAtualizarClientesAndClienteCpfIsEmpty() {
        ClienteDTO clienteDTO = new ClienteDTO("", "Teste", "email@email.com");
        when(patchClienteAdapter.atualizarClientes(clienteDTO)).thenReturn(clienteDTO);
        assertThrows(ClienteException.class, () -> clienteController.atualizarClientes(clienteDTO));
    }


}
//package br.com.fiap.techchallenge.usecases.cliente;
//
//import br.com.fiap.techchallenge.naousar.domain.usecases.cliente.PutClienteUseCase;
//import br.com.fiap.techchallenge.naousar.domain.valueobjects.ClienteDTO;
//import br.com.fiap.techchallenge.naousar.infra.exception.ClienteException;
//import br.com.fiap.techchallenge.naousar.ports.cliente.PutClienteOutboundPort;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//class PutClienteUseCaseTest {
//
//    @Mock
//    private PutClienteOutboundPort port;
//
//    private PutClienteUseCase useCase;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        useCase = new PutClienteUseCase(port);
//    }
//
//    @Test
//    void shouldUpdateClienteSuccessfully() {
//        ClienteDTO clienteDTO = new ClienteDTO();
//        clienteDTO.setCpf("12345678901");
//        clienteDTO.setNome("João");
//        clienteDTO.setEmail("email@email.com");
//
//        when(port.atualizarClientes(clienteDTO)).thenReturn(clienteDTO);
//
//        useCase.atualizarClientes(clienteDTO);
//
//        verify(port).atualizarClientes(clienteDTO);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenCpfIsNull() {
//        ClienteDTO clienteDTO = new ClienteDTO();
//        clienteDTO.setCpf(null);
//
//        assertThrows(ClienteException.class, () -> useCase.atualizarClientes(clienteDTO));
//    }
//
//    @Test
//    void shouldThrowExceptionWhenCpfIsEmpty() {
//        ClienteDTO clienteDTO = new ClienteDTO();
//        clienteDTO.setCpf("");
//
//        assertThrows(ClienteException.class, () -> useCase.atualizarClientes(clienteDTO));
//    }
//
//    @Test
//    void shouldThrowExceptionWhenNomeIsNull() {
//        ClienteDTO clienteDTO = new ClienteDTO();
//        clienteDTO.setNome(null);
//
//        assertThrows(ClienteException.class, () -> useCase.atualizarClientes(clienteDTO));
//    }
//
//    @Test
//    void shouldThrowExceptionWhenNomeIsEmpty() {
//        ClienteDTO clienteDTO = new ClienteDTO();
//        clienteDTO.setNome("");
//
//        assertThrows(ClienteException.class, () -> useCase.atualizarClientes(clienteDTO));
//    }
//
//    @Test
//    void shouldThrowExceptionWhenEmailIsNull() {
//        ClienteDTO clienteDTO = new ClienteDTO();
//        clienteDTO.setEmail(null);
//
//        assertThrows(ClienteException.class, () -> useCase.atualizarClientes(clienteDTO));
//    }
//
//    @Test
//    void shouldThrowExceptionWhenEmailIsEmpty() {
//        ClienteDTO clienteDTO = new ClienteDTO();
//        clienteDTO.setEmail("");
//
//        assertThrows(ClienteException.class, () -> useCase.atualizarClientes(clienteDTO));
//    }
//}
//package br.com.fiap.techchallenge.naousar.adapters.cliente;
//
//import br.com.fiap.techchallenge.infra.persistence.entities.Cliente;
//import br.com.fiap.techchallenge.infra.mapper.cliente.ClienteMapper;
//import br.com.fiap.techchallenge.naousar.domain.valueobjects.ClienteDTO;
//import br.com.fiap.techchallenge.naousar.infra.exception.ClienteException;
//import br.com.fiap.techchallenge.infra.persistence.ClienteRepository;
//import br.com.fiap.techchallenge.naousar.ports.cliente.PostClienteOutboundPort;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.Optional;
//
//import static br.com.fiap.techchallenge.domain.ErrosEnum.CLIENTE_JA_CADASTRADO;
//
//@Slf4j
//public class PostClienteAdapter implements PostClienteOutboundPort {
//
//    private final ClienteRepository clienteRepository;
//    private final ClienteMapper mapper;
//
//    public PostClienteAdapter(ClienteRepository clienteRepository, ClienteMapper mapper) {
//        this.clienteRepository = clienteRepository;
//        this.mapper = mapper;
//    }
//
//    @Override
//    public ClienteDTO salvarCliente(ClienteDTO clienteDTO) {
//        log.info("salvarCliente");
//        return mapper.toDTO(clienteRepository.saveAndFlush(mapper.toEntity(clienteDTO)));
//    }
//
//    @Override
//    public void validarCpfCadastrado(String cpf) {
//        log.info("validarCpfCadastrado");
//        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cpf);
//        if(clienteOptional.isPresent()) {
//            throw new ClienteException(CLIENTE_JA_CADASTRADO);
//        }
//    }
//}

package br.com.fiap.techchallenge.infra.config;

import br.com.fiap.techchallenge.application.gateways.IClienteRepository;
import br.com.fiap.techchallenge.application.usecases.cliente.AtualizarClienteParcialUseCase;
import br.com.fiap.techchallenge.application.usecases.cliente.AtualizarClienteUseCase;
import br.com.fiap.techchallenge.application.usecases.cliente.CadastrarClienteUseCase;
import br.com.fiap.techchallenge.application.usecases.cliente.ListarClienteUseCase;
import br.com.fiap.techchallenge.infra.gateways.ClienteRepository;
import br.com.fiap.techchallenge.infra.mapper.cliente.ClienteMapper;
import br.com.fiap.techchallenge.infra.persistence.ClienteEntityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClienteConfig {

    @Bean
    public ListarClienteUseCase listarClienteUseCase(IClienteRepository clienteRepository) {
        return new ListarClienteUseCase(clienteRepository);
    }

    @Bean
    public CadastrarClienteUseCase cadastrarClienteUseCase(IClienteRepository clienteRepository) {
        return new CadastrarClienteUseCase(clienteRepository);
    }

    @Bean
    public AtualizarClienteParcialUseCase atualizarClienteParcialUseCase(IClienteRepository clienteRepository) {
        return new AtualizarClienteParcialUseCase(clienteRepository);
    }

    @Bean
    public AtualizarClienteUseCase atualizarClienteUseCase(IClienteRepository clienteRepository) {
        return new AtualizarClienteUseCase(clienteRepository);
    }

    @Bean
    public IClienteRepository clienteRepository(ClienteEntityRepository clienteEntityRepository, ClienteMapper clienteMapper) {
        return new ClienteRepository(clienteEntityRepository, clienteMapper);
    }

    @Bean
    public ClienteMapper clienteMapper() {
        return new ClienteMapper();
    }

}

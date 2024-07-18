package br.com.fiap.techchallenge.infra.config;

import br.com.fiap.techchallenge.application.gateways.ICategoriaRepository;
import br.com.fiap.techchallenge.application.usecases.categoria.ListarCategoriasUseCase;
import br.com.fiap.techchallenge.infra.gateways.categorias.CategoriaRepository;
import br.com.fiap.techchallenge.infra.persistence.CategoriaEntityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoriaConfig {

    @Bean
    public ICategoriaRepository criarCategoriaRepository(
            CategoriaEntityRepository categoriaEntityRepository
    ) {
        return new CategoriaRepository(categoriaEntityRepository);
    }

    @Bean
    public ListarCategoriasUseCase criarListarCategoriasUseCase(ICategoriaRepository categoriaRepository){
        return new ListarCategoriasUseCase(categoriaRepository);
    }

}

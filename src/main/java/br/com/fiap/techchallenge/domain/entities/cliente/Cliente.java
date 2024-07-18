package br.com.fiap.techchallenge.domain.entities.cliente;

import br.com.fiap.techchallenge.domain.entities.pedido.Pedido;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Cliente {
    private Long id;
    private String cpf;
    private String nome;
    private String email;
    private List<Pedido> pedidos;

    public Cliente() {
    }

    public Cliente(Long id, String cpf, String nome, String email, List<Pedido> pedidos) {
    this(cpf, nome, email);
    this.id = id;
    this.pedidos = pedidos;
}

    public Cliente(String cpf, String nome, String email) {

        if (cpf == null || cpf.isEmpty() || cpf.isBlank()) {
            throw new IllegalArgumentException("Cpf é um campo obrigatório no cadastro de novos clientes.");
        }

        if (nome == null || nome.isEmpty() || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é um campo obrigatório no cadastro de novos clientes.");
        }

        if (email == null || email.isEmpty() || email.isBlank()) {
            throw new IllegalArgumentException("E-mail é um campo obrigatório no cadastro de novos clientes.");
        }

        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }
}

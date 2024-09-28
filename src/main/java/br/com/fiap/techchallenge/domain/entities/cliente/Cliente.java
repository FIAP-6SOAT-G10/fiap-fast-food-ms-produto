package br.com.fiap.techchallenge.domain.entities.cliente;

public class Cliente {
    private Long id;
    private String cpf;
    private String nome;
    private String email;

    public Cliente() {
    }

    public Cliente(String cpf) {
        if (cpf == null || cpf.isEmpty() || cpf.isBlank()) {
            throw new IllegalArgumentException("Cpf é um campo obrigatório no cadastro de novos clientes.");
        }
        this.cpf = cpf;
    }

    public Cliente(Long id, String cpf, String nome, String email) {
        this(cpf, nome, email);
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}


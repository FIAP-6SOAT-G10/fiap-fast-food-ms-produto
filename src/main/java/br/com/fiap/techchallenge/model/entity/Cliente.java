package br.com.fiap.techchallenge.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
@SequenceGenerator(name = "cliente_seq" , sequenceName = "cliente_id_seq" , allocationSize = 1)
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "cliente_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

}
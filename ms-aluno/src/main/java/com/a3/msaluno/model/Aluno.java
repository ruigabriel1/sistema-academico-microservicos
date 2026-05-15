package com.a3.msaluno.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidade JPA que representa um Aluno no sistema acadêmico
// @Entity marca a classe como uma tabela no banco de dados
// @Table define o nome da tabela no MySQL
@Entity
@Table(name = "alunos")
@Data               // Lombok: gera getters, setters, equals, hashCode e toString
@NoArgsConstructor  // Lombok: gera construtor vazio (necessário para o JPA)
@AllArgsConstructor // Lombok: gera construtor com todos os campos
public class Aluno {

    // Chave primária gerada automaticamente pelo banco (auto-increment)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome completo do aluno — campo obrigatório
    @Column(nullable = false)
    private String nome;

    // E-mail do aluno — obrigatório e único no sistema
    @Column(nullable = false, unique = true)
    private String email;

    // CPF do aluno — obrigatório e único no sistema
    @Column(nullable = false, unique = true)
    private String cpf;

    // Data de nascimento no formato "AAAA-MM-DD" (ex: "2000-05-15")
    @Column(nullable = false)
    private String dataNascimento;
}

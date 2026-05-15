package com.a3.msmatricula.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidade JPA que representa uma Matrícula no sistema acadêmico
// Armazena apenas os IDs do Aluno e do Curso — os dados completos são buscados via REST
// quando necessário (desacoplamento de microserviços)
@Entity
@Table(name = "matriculas")
@Data               // Lombok: gera getters, setters, equals, hashCode e toString
@NoArgsConstructor  // Lombok: gera construtor vazio (exigido pelo JPA e Jackson)
@AllArgsConstructor // Lombok: gera construtor com todos os campos
public class Matricula {

    // Identificador único da matrícula, gerado automaticamente pelo banco
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID do aluno no ms-aluno (referência externa — não há FK no banco, pois é microserviço)
    @Column(nullable = false)
    private Long alunoId;

    // ID do curso no ms-curso (referência externa — não há FK no banco, pois é microserviço)
    @Column(nullable = false)
    private Long cursoId;

    // Data em que a matrícula foi realizada, no formato "AAAA-MM-DD"
    @Column(nullable = false)
    private String dataMatricula;

    // Status atual da matrícula: ATIVA, CANCELADA ou CONCLUIDA
    @Column(nullable = false)
    private String status;
}

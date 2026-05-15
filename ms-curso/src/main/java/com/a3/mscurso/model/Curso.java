package com.a3.mscurso.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidade JPA que representa um Curso no sistema acadêmico
// Mapeada para a tabela 'cursos' do banco de dados 'curso_db'
@Entity
@Table(name = "cursos")
@Data               // Lombok: gera getters, setters, equals, hashCode e toString
@NoArgsConstructor  // Lombok: gera construtor vazio (exigido pelo JPA)
@AllArgsConstructor // Lombok: gera construtor com todos os campos
public class Curso {

    // Identificador único do curso gerado automaticamente pelo banco (auto-increment)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome do curso — obrigatório e único no sistema
    @Column(nullable = false, unique = true)
    private String nome;

    // Descrição do curso — informação textual sobre o conteúdo
    @Column(nullable = false, length = 500)
    private String descricao;

    // Carga horária total do curso em horas
    @Column(nullable = false)
    private Integer cargaHoraria;
}

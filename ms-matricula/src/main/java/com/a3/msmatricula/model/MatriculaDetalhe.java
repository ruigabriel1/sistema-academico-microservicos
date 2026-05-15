package com.a3.msmatricula.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Classe de visão que combina os dados da Matrícula com os dados completos do Aluno e do Curso
// Retornada nos endpoints GET para fornecer ao front-end uma resposta rica e completa
// Demonstra a comunicação entre microserviços: ms-matricula consome ms-aluno e ms-curso
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaDetalhe {

    // Dados da própria matrícula
    private Long id;
    private String dataMatricula;
    private String status;

    // Dados do aluno obtidos via chamada REST ao ms-aluno (pode ser null se o serviço estiver indisponível)
    private AlunoInfo aluno;

    // Dados do curso obtidos via chamada REST ao ms-curso (pode ser null se o serviço estiver indisponível)
    private CursoInfo curso;
}

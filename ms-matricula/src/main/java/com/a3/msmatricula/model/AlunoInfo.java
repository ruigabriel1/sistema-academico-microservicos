package com.a3.msmatricula.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Classe de modelo que representa os dados de um Aluno recebidos via REST do ms-aluno
// Utilizada para desserializar o JSON retornado pelo endpoint GET /alunos/{id}
// Possui apenas os campos necessários para exibição no ms-matricula
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoInfo {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String dataNascimento;
}

package com.a3.msmatricula.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Classe de modelo que representa os dados de um Curso recebidos via REST do ms-curso
// Utilizada para desserializar o JSON retornado pelo endpoint GET /cursos/{id}
// Possui apenas os campos necessários para exibição no ms-matricula
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoInfo {

    private Long id;
    private String nome;
    private String descricao;
    private Integer cargaHoraria;
}

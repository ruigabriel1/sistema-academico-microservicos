package com.a3.mscurso.dto;

import com.a3.mscurso.model.Curso;

// Record para DTO de Curso
public record CursoDTO(
        Long id,
        String nome,
        String descricao,
        Integer cargaHoraria
) {
    public static CursoDTO fromEntity(Curso curso) {
        return new CursoDTO(
                curso.getId(),
                curso.getNome(),
                curso.getDescricao(),
                curso.getCargaHoraria()
        );
    }

    public Curso toEntity() {
        Curso curso = new Curso();
        curso.setId(this.id);
        curso.setNome(this.nome);
        curso.setDescricao(this.descricao);
        curso.setCargaHoraria(this.cargaHoraria);
        return curso;
    }
}

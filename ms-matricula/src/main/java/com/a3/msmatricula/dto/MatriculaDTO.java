package com.a3.msmatricula.dto;

import com.a3.msmatricula.model.Matricula;

// Record para entrada de dados de Matrícula (Criação e Atualização)
public record MatriculaDTO(
        Long id,
        Long alunoId,
        Long cursoId,
        String dataMatricula,
        String status
) {
    public static MatriculaDTO fromEntity(Matricula matricula) {
        return new MatriculaDTO(
                matricula.getId(),
                matricula.getAlunoId(),
                matricula.getCursoId(),
                matricula.getDataMatricula(),
                matricula.getStatus()
        );
    }

    public Matricula toEntity() {
        Matricula matricula = new Matricula();
        matricula.setId(this.id);
        matricula.setAlunoId(this.alunoId);
        matricula.setCursoId(this.cursoId);
        matricula.setDataMatricula(this.dataMatricula);
        matricula.setStatus(this.status);
        return matricula;
    }
}

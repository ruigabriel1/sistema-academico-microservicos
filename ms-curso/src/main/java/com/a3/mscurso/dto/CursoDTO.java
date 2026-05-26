package com.a3.mscurso.dto;

import com.a3.mscurso.model.Curso;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Record para DTO de Curso
public record CursoDTO(
        Long id,

        @NotBlank(message = "O nome do curso é obrigatório")
        @Size(min = 3, max = 150, message = "O nome do curso deve ter entre 3 e 150 caracteres")
        String nome,

        String descricao,

        @NotNull(message = "A carga horária é obrigatória")
        @Min(value = 1, message = "A carga horária deve ser de no mínimo 1 hora")
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

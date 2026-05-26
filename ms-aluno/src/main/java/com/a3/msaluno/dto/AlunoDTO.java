package com.a3.msaluno.dto;

import com.a3.msaluno.model.Aluno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// DTO implementado como Record (Java 14+)
// Simplifica a transferência de dados entre o cliente HTTP e a aplicação
public record AlunoDTO(
        Long id,

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String nome, 

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "O e-mail deve ser válido")
        String email, 

        @NotBlank(message = "O CPF é obrigatório")
        String cpf, 

        String dataNascimento
) {
    // Método utilitário para converter de Entity para DTO
    public static AlunoDTO fromEntity(Aluno aluno) {
        return new AlunoDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getCpf(),
                aluno.getDataNascimento()
        );
    }

    // Método utilitário para converter de DTO para Entity
    public Aluno toEntity() {
        Aluno aluno = new Aluno();
        aluno.setId(this.id);
        aluno.setNome(this.nome);
        aluno.setEmail(this.email);
        aluno.setCpf(this.cpf);
        aluno.setDataNascimento(this.dataNascimento);
        return aluno;
    }
}

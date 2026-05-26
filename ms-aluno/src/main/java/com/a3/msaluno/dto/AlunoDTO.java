package com.a3.msaluno.dto;

import com.a3.msaluno.model.Aluno;

// DTO implementado como Record (Java 14+)
// Simplifica a transferência de dados entre o cliente HTTP e a aplicação
public record AlunoDTO(
        Long id,
        String nome, 
        String email, 
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

package com.a3.msaluno.repository;

import com.a3.msaluno.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositório responsável pelo acesso aos dados da tabela 'alunos' no banco
// JpaRepository fornece os métodos CRUD prontos: save, findById, findAll, deleteById, existsById, etc.
// O primeiro parâmetro é a entidade e o segundo é o tipo da chave primária
@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    // Não é necessário declarar métodos básicos de CRUD — o Spring Data JPA os gera automaticamente
}

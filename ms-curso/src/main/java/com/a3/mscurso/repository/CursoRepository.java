package com.a3.mscurso.repository;

import com.a3.mscurso.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositório responsável pelo acesso aos dados da tabela 'cursos' no banco
// JpaRepository fornece os métodos CRUD prontos (save, findById, findAll, deleteById, etc.)
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    // O Spring Data JPA gera a implementação automaticamente em tempo de execução
}

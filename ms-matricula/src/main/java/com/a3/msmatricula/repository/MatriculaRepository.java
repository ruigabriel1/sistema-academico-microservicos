package com.a3.msmatricula.repository;

import com.a3.msmatricula.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositório responsável pelo acesso aos dados da tabela 'matriculas' no banco local
// Herda os métodos CRUD padrão do JpaRepository (save, findAll, findById, deleteById, etc.)
@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    // Métodos CRUD básicos são gerados automaticamente pelo Spring Data JPA
}

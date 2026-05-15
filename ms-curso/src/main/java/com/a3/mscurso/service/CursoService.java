package com.a3.mscurso.service;

import com.a3.mscurso.model.Curso;
import com.a3.mscurso.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Camada de Serviço: contém as regras de negócio do microserviço de Cursos
// Realiza a intermediação entre o Controller e o Repository
@Service
public class CursoService {

    // Injeção de dependência do repositório de cursos
    @Autowired
    private CursoRepository cursoRepository;

    // Retorna a lista com todos os cursos cadastrados no banco
    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    // Busca um curso específico pelo ID
    // Retorna Optional.empty() se o curso não for encontrado
    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    // Persiste um novo curso no banco de dados
    public Curso salvar(Curso curso) {
        return cursoRepository.save(curso);
    }

    // Atualiza os dados de um curso já cadastrado, identificado pelo ID
    // Retorna null se o curso com o ID fornecido não existir
    public Curso atualizar(Long id, Curso dadosAtualizados) {
        return cursoRepository.findById(id).map(cursoExistente -> {
            // Aplica os novos valores em cada campo do curso encontrado
            cursoExistente.setNome(dadosAtualizados.getNome());
            cursoExistente.setDescricao(dadosAtualizados.getDescricao());
            cursoExistente.setCargaHoraria(dadosAtualizados.getCargaHoraria());
            // Salva e retorna o curso atualizado
            return cursoRepository.save(cursoExistente);
        }).orElse(null);
    }

    // Remove um curso do banco pelo ID
    // Retorna true se o curso foi encontrado e excluído, false se não existia
    public boolean deletar(Long id) {
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

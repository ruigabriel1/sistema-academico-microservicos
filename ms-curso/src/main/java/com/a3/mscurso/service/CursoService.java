package com.a3.mscurso.service;

import com.a3.mscurso.model.Curso;
import com.a3.mscurso.repository.CursoRepository;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;

// Camada de Serviço: contém as regras de negócio do microserviço de Cursos
// Realiza a intermediação entre o Controller e o Repository
@Service
@Slf4j
public class CursoService {

    private final CursoRepository cursoRepository;

    // Injeção de dependência via construtor
    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    // Retorna a lista com todos os cursos cadastrados no banco
    public List<Curso> listarTodos() {
        log.info("Buscando listagem de todos os cursos");
        return cursoRepository.findAll();
    }

    // Busca um curso específico pelo ID
    // Retorna Optional.empty() se o curso não for encontrado
    public Optional<Curso> buscarPorId(Long id) {
        log.info("Buscando curso pelo ID: {}", id);
        return cursoRepository.findById(id);
    }

    // Persiste um novo curso no banco de dados
    public Curso salvar(Curso curso) {
        log.info("Cadastrando novo curso: {}", curso.getNome());
        return cursoRepository.save(curso);
    }

    // Atualiza os dados de um curso já cadastrado, identificado pelo ID
    // Retorna null se o curso com o ID fornecido não existir
    public Curso atualizar(Long id, Curso dadosAtualizados) {
        log.info("Tentando atualizar curso ID: {}", id);
        return cursoRepository.findById(id).map(cursoExistente -> {
            log.info("Curso ID {} encontrado. Atualizando dados.", id);
            // Aplica os novos valores em cada campo do curso encontrado
            cursoExistente.setNome(dadosAtualizados.getNome());
            cursoExistente.setDescricao(dadosAtualizados.getDescricao());
            cursoExistente.setCargaHoraria(dadosAtualizados.getCargaHoraria());
            // Salva e retorna o curso atualizado
            return cursoRepository.save(cursoExistente);
        }).orElseGet(() -> {
            log.warn("Falha ao atualizar. Curso com ID {} não encontrado.", id);
            return null;
        });
    }

    // Remove um curso do banco pelo ID
    // Retorna true se o curso foi encontrado e excluído, false se não existia
    public boolean deletar(Long id) {
        log.info("Tentando remover curso com ID: {}", id);
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
            log.info("Curso com ID {} removido com sucesso.", id);
            return true;
        }
        log.warn("Falha ao remover. Curso com ID {} não encontrado.", id);
        return false;
    }
}

package com.a3.msaluno.service;

import com.a3.msaluno.model.Aluno;
import com.a3.msaluno.repository.AlunoRepository;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;

// Camada de Serviço: contém as regras de negócio do microserviço de Alunos
// Faz a intermediação entre o Controller (entrada de dados) e o Repository (banco de dados)
@Service
@Slf4j
public class AlunoService {

    private final AlunoRepository alunoRepository;

    // Injeção de dependência via construtor (Boa prática recomendada pelo Spring)
    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    // Retorna a lista com todos os alunos cadastrados no banco
    public List<Aluno> listarTodos() {
        log.info("Buscando a listagem de todos os alunos");
        return alunoRepository.findAll();
    }

    // Busca um aluno pelo seu ID único
    // Retorna Optional.empty() se o aluno não for encontrado
    public Optional<Aluno> buscarPorId(Long id) {
        log.info("Buscando aluno pelo ID: {}", id);
        return alunoRepository.findById(id);
    }

    // Persiste um novo aluno no banco de dados
    public Aluno salvar(Aluno aluno) {
        log.info("Cadastrando novo aluno no banco de dados: {}", aluno.getNome());
        return alunoRepository.save(aluno);
    }

    // Atualiza os dados de um aluno já existente identificado pelo ID
    // Retorna null caso nenhum aluno seja encontrado com o ID informado
    public Aluno atualizar(Long id, Aluno dadosAtualizados) {
        log.info("Tentando atualizar aluno com ID: {}", id);
        return alunoRepository.findById(id).map(alunoExistente -> {
            log.info("Aluno ID {} encontrado. Atualizando dados.", id);
            // Aplica os novos valores em cada campo do aluno encontrado
            alunoExistente.setNome(dadosAtualizados.getNome());
            alunoExistente.setEmail(dadosAtualizados.getEmail());
            alunoExistente.setCpf(dadosAtualizados.getCpf());
            alunoExistente.setDataNascimento(dadosAtualizados.getDataNascimento());
            // Salva e retorna o aluno com os dados atualizados
            return alunoRepository.save(alunoExistente);
        }).orElseGet(() -> {
            log.warn("Falha ao atualizar. Aluno com ID {} não encontrado.", id);
            return null;
        });
    }

    // Remove um aluno do banco pelo ID
    // Retorna true se o aluno foi encontrado e excluído, false se não existia
    public boolean deletar(Long id) {
        log.info("Tentando remover aluno com ID: {}", id);
        if (alunoRepository.existsById(id)) {
            alunoRepository.deleteById(id);
            log.info("Aluno com ID {} removido com sucesso.", id);
            return true;
        }
        log.warn("Falha ao remover. Aluno com ID {} não encontrado.", id);
        return false;
    }
}

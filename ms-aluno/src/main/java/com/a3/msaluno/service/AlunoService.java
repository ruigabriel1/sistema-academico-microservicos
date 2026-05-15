package com.a3.msaluno.service;

import com.a3.msaluno.model.Aluno;
import com.a3.msaluno.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Camada de Serviço: contém as regras de negócio do microserviço de Alunos
// Faz a intermediação entre o Controller (entrada de dados) e o Repository (banco de dados)
@Service
public class AlunoService {

    // Injeção de dependência do repositório de alunos
    @Autowired
    private AlunoRepository alunoRepository;

    // Retorna a lista com todos os alunos cadastrados no banco
    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    // Busca um aluno pelo seu ID único
    // Retorna Optional.empty() se o aluno não for encontrado
    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    // Persiste um novo aluno no banco de dados
    public Aluno salvar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    // Atualiza os dados de um aluno já existente identificado pelo ID
    // Retorna null caso nenhum aluno seja encontrado com o ID informado
    public Aluno atualizar(Long id, Aluno dadosAtualizados) {
        return alunoRepository.findById(id).map(alunoExistente -> {
            // Aplica os novos valores em cada campo do aluno encontrado
            alunoExistente.setNome(dadosAtualizados.getNome());
            alunoExistente.setEmail(dadosAtualizados.getEmail());
            alunoExistente.setCpf(dadosAtualizados.getCpf());
            alunoExistente.setDataNascimento(dadosAtualizados.getDataNascimento());
            // Salva e retorna o aluno com os dados atualizados
            return alunoRepository.save(alunoExistente);
        }).orElse(null);
    }

    // Remove um aluno do banco pelo ID
    // Retorna true se o aluno foi encontrado e excluído, false se não existia
    public boolean deletar(Long id) {
        if (alunoRepository.existsById(id)) {
            alunoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

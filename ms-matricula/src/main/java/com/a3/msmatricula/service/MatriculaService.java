package com.a3.msmatricula.service;

import com.a3.msmatricula.model.*;
import com.a3.msmatricula.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Camada de Serviço do ms-matricula
// Contém a lógica de negócio e a comunicação com os outros microserviços via REST
@Service
@Slf4j
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final RestTemplate restTemplate;

    // URL base do ms-aluno, configurada no application.properties
    @Value("${ms.aluno.url}")
    private String msAlunoUrl;

    // URL base do ms-curso, configurada no application.properties
    @Value("${ms.curso.url}")
    private String msCursoUrl;

    // Injeção de dependência via construtor
    public MatriculaService(MatriculaRepository matriculaRepository, RestTemplate restTemplate) {
        this.matriculaRepository = matriculaRepository;
        this.restTemplate = restTemplate;
    }

    // Retorna todas as matrículas enriquecidas com os dados de aluno e curso
    public List<MatriculaDetalhe> listarTodas() {
        log.info("Buscando listagem de todas as matrículas");
        List<Matricula> matriculas = matriculaRepository.findAll();
        List<MatriculaDetalhe> detalhes = new ArrayList<>();

        // Para cada matrícula encontrada, busca os dados nos serviços externos
        for (Matricula matricula : matriculas) {
            detalhes.add(construirDetalhe(matricula));
        }

        return detalhes;
    }

    // Busca uma matrícula específica pelo ID, retornando os dados completos
    public Optional<MatriculaDetalhe> buscarPorId(Long id) {
        log.info("Buscando matrícula pelo ID: {}", id);
        return matriculaRepository.findById(id)
                .map(this::construirDetalhe);
    }

    // Salva uma nova matrícula no banco de dados local
    public Matricula salvar(Matricula matricula) {
        log.info("Registrando nova matrícula para Aluno ID {} no Curso ID {}", matricula.getAlunoId(), matricula.getCursoId());
        return matriculaRepository.save(matricula);
    }

    // Atualiza os campos de uma matrícula existente identificada pelo ID
    public Matricula atualizar(Long id, Matricula dadosAtualizados) {
        log.info("Tentando atualizar matrícula ID: {}", id);
        return matriculaRepository.findById(id).map(matriculaExistente -> {
            log.info("Matrícula ID {} encontrada. Atualizando dados.", id);
            // Atualiza cada campo com os novos valores recebidos
            matriculaExistente.setAlunoId(dadosAtualizados.getAlunoId());
            matriculaExistente.setCursoId(dadosAtualizados.getCursoId());
            matriculaExistente.setDataMatricula(dadosAtualizados.getDataMatricula());
            matriculaExistente.setStatus(dadosAtualizados.getStatus());
            return matriculaRepository.save(matriculaExistente);
        }).orElseGet(() -> {
            log.warn("Falha ao atualizar. Matrícula com ID {} não encontrada.", id);
            return null;
        });
    }

    // Remove um matrícula do banco pelo ID
    public boolean deletar(Long id) {
        log.info("Tentando remover matrícula com ID: {}", id);
        if (matriculaRepository.existsById(id)) {
            matriculaRepository.deleteById(id);
            log.info("Matrícula com ID {} removida com sucesso.", id);
            return true;
        }
        log.warn("Falha ao remover. Matrícula com ID {} não encontrada.", id);
        return false;
    }

    // -----------------------------------------------------------------------
    // Método auxiliar: constrói um MatriculaDetalhe combinando os dados locais
    // com informações buscadas via REST nos microserviços de Aluno e Curso
    // Este é o ponto central de comunicação entre microserviços neste projeto
    // -----------------------------------------------------------------------
    private MatriculaDetalhe construirDetalhe(Matricula matricula) {

        // Chama o ms-aluno via REST para obter os dados completos do aluno
        AlunoInfo aluno = null;
        try {
            aluno = restTemplate.getForObject(
                    msAlunoUrl + "/alunos/" + matricula.getAlunoId(),
                    AlunoInfo.class
            );
        } catch (Exception e) {
            // Se o ms-aluno estiver indisponível, o campo 'aluno' ficará nulo na resposta
            log.warn("ms-aluno indisponível ao buscar alunoId={}", matricula.getAlunoId());
        }

        // Chama o ms-curso via REST para obter os dados completos do curso
        CursoInfo curso = null;
        try {
            curso = restTemplate.getForObject(
                    msCursoUrl + "/cursos/" + matricula.getCursoId(),
                    CursoInfo.class
            );
        } catch (Exception e) {
            // Se o ms-curso estiver indisponível, o campo 'curso' ficará nulo na resposta
            log.warn("ms-curso indisponível ao buscar cursoId={}", matricula.getCursoId());
        }

        // Monta e retorna o objeto de detalhe com todos os dados combinados
        MatriculaDetalhe detalhe = new MatriculaDetalhe();
        detalhe.setId(matricula.getId());
        detalhe.setDataMatricula(matricula.getDataMatricula());
        detalhe.setStatus(matricula.getStatus());
        detalhe.setAluno(aluno);
        detalhe.setCurso(curso);

        return detalhe;
    }
}

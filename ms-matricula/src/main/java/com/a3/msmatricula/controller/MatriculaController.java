package com.a3.msmatricula.controller;

import com.a3.msmatricula.model.Matricula;
import com.a3.msmatricula.model.MatriculaDetalhe;
import com.a3.msmatricula.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller REST do microserviço de Matrículas
// Rota base: /matriculas
// Os endpoints GET retornam MatriculaDetalhe (com dados enriquecidos de aluno e curso via REST)
// Os endpoints POST e PUT recebem Matricula (apenas os IDs de aluno e curso)
@RestController
@RequestMapping("/matriculas")
@CrossOrigin(origins = "*") // Permite chamadas do front-end de qualquer origem
public class MatriculaController {

    // Injeção da camada de serviço onde ocorre a lógica e a comunicação entre serviços
    @Autowired
    private MatriculaService matriculaService;

    // GET /matriculas
    // Retorna todas as matrículas com os dados completos de aluno e curso (via REST)
    @GetMapping
    public List<MatriculaDetalhe> listarTodas() {
        return matriculaService.listarTodas();
    }

    // GET /matriculas/{id}
    // Retorna uma matrícula específica com os dados enriquecidos — 404 se não encontrada
    @GetMapping("/{id}")
    public ResponseEntity<MatriculaDetalhe> buscarPorId(@PathVariable Long id) {
        return matriculaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /matriculas
    // Registra uma nova matrícula — recebe apenas alunoId, cursoId, dataMatricula e status
    @PostMapping
    public ResponseEntity<Matricula> cadastrar(@RequestBody Matricula matricula) {
        Matricula salva = matriculaService.salvar(matricula);
        return ResponseEntity.status(201).body(salva);
    }

    // PUT /matriculas/{id}
    // Atualiza os dados de uma matrícula existente — 404 se não encontrada
    @PutMapping("/{id}")
    public ResponseEntity<Matricula> atualizar(@PathVariable Long id, @RequestBody Matricula matricula) {
        Matricula atualizada = matriculaService.atualizar(id, matricula);
        if (atualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizada);
    }

    // DELETE /matriculas/{id}
    // Remove uma matrícula pelo ID — 204 se removida, 404 se não encontrada
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (matriculaService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

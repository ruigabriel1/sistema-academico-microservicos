package com.a3.msaluno.controller;

import com.a3.msaluno.model.Aluno;
import com.a3.msaluno.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Camada de Controller: responsável por expor os endpoints REST do microserviço de Alunos
// Rota base: /alunos
@RestController
@RequestMapping("/alunos")
@CrossOrigin(origins = "*") // Permite chamadas do front-end (React) em qualquer origem
public class AlunoController {

    // Injeção da camada de serviço onde ficam as regras de negócio
    @Autowired
    private AlunoService alunoService;

    // GET /alunos
    // Retorna a lista completa de todos os alunos cadastrados
    @GetMapping
    public List<Aluno> listarTodos() {
        return alunoService.listarTodos();
    }

    // GET /alunos/{id}
    // Retorna um aluno específico pelo ID — 404 se não encontrado
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        return alunoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /alunos
    // Cadastra um novo aluno a partir do corpo da requisição — retorna 201 Created
    @PostMapping
    public ResponseEntity<Aluno> cadastrar(@RequestBody Aluno aluno) {
        Aluno salvo = alunoService.salvar(aluno);
        return ResponseEntity.status(201).body(salvo);
    }

    // PUT /alunos/{id}
    // Atualiza todos os dados de um aluno existente — 404 se não encontrado
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody Aluno aluno) {
        Aluno atualizado = alunoService.atualizar(id, aluno);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    // DELETE /alunos/{id}
    // Remove um aluno pelo ID — 204 No Content se removido, 404 se não encontrado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (alunoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

package com.a3.mscurso.controller;

import com.a3.mscurso.model.Curso;
import com.a3.mscurso.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Camada de Controller: expõe os endpoints REST do microserviço de Cursos
// Rota base: /cursos
@RestController
@RequestMapping("/cursos")
@CrossOrigin(origins = "*") // Libera o acesso ao front-end (React) de qualquer origem
public class CursoController {

    // Injeção da camada de serviço onde ficam as regras de negócio
    @Autowired
    private CursoService cursoService;

    // GET /cursos
    // Retorna todos os cursos cadastrados no sistema
    @GetMapping
    public List<Curso> listarTodos() {
        return cursoService.listarTodos();
    }

    // GET /cursos/{id}
    // Retorna um curso específico pelo ID — 404 se não encontrado
    // Também é utilizado pelo ms-matricula ao consultar dados do curso via REST
    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Long id) {
        return cursoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /cursos
    // Cadastra um novo curso a partir do corpo da requisição — retorna 201 Created
    @PostMapping
    public ResponseEntity<Curso> cadastrar(@RequestBody Curso curso) {
        Curso salvo = cursoService.salvar(curso);
        return ResponseEntity.status(201).body(salvo);
    }

    // PUT /cursos/{id}
    // Atualiza todos os dados de um curso existente — 404 se não encontrado
    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizar(@PathVariable Long id, @RequestBody Curso curso) {
        Curso atualizado = cursoService.atualizar(id, curso);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    // DELETE /cursos/{id}
    // Remove um curso pelo ID — 204 se removido, 404 se não encontrado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (cursoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

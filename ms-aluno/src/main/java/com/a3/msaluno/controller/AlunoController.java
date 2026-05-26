package com.a3.msaluno.controller;

import com.a3.msaluno.dto.AlunoDTO;
import com.a3.msaluno.exception.ResourceNotFoundException;
import com.a3.msaluno.model.Aluno;
import com.a3.msaluno.service.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

// Camada de Controller: responsável por expor os endpoints REST do microserviço de Alunos
// Rota base: /alunos
@RestController
@RequestMapping("/alunos")
@CrossOrigin(origins = "*") // Permite chamadas do front-end (React) em qualquer origem
public class AlunoController {

    private final AlunoService alunoService;

    // Injeção de dependência via construtor (Boa prática recomendada pelo Spring)
    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    // GET /alunos
    @GetMapping
    public List<AlunoDTO> listarTodos() {
        return alunoService.listarTodos().stream()
                .map(AlunoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // GET /alunos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> buscarPorId(@PathVariable Long id) {
        Aluno aluno = alunoService.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com ID: " + id));
        return ResponseEntity.ok(AlunoDTO.fromEntity(aluno));
    }

    // POST /alunos
    @PostMapping
    public ResponseEntity<AlunoDTO> cadastrar(@Valid @RequestBody AlunoDTO dto) {
        Aluno salvo = alunoService.salvar(dto.toEntity());
        return ResponseEntity.status(201).body(AlunoDTO.fromEntity(salvo));
    }

    // PUT /alunos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AlunoDTO dto) {
        Aluno atualizado = alunoService.atualizar(id, dto.toEntity());
        if (atualizado == null) {
            throw new ResourceNotFoundException("Não é possível atualizar. Aluno não encontrado com ID: " + id);
        }
        return ResponseEntity.ok(AlunoDTO.fromEntity(atualizado));
    }

    // DELETE /alunos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!alunoService.deletar(id)) {
            throw new ResourceNotFoundException("Não é possível remover. Aluno não encontrado com ID: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}

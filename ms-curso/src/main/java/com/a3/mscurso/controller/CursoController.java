package com.a3.mscurso.controller;

import com.a3.mscurso.dto.CursoDTO;
import com.a3.mscurso.exception.ResourceNotFoundException;
import com.a3.mscurso.model.Curso;
import com.a3.mscurso.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public List<CursoDTO> listarTodos() {
        return cursoService.listarTodos().stream()
                .map(CursoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> buscarPorId(@PathVariable Long id) {
        Curso curso = cursoService.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado com ID: " + id));
        return ResponseEntity.ok(CursoDTO.fromEntity(curso));
    }

    @PostMapping
    public ResponseEntity<CursoDTO> cadastrar(@RequestBody CursoDTO dto) {
        Curso salvo = cursoService.salvar(dto.toEntity());
        return ResponseEntity.status(201).body(CursoDTO.fromEntity(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> atualizar(@PathVariable Long id, @RequestBody CursoDTO dto) {
        Curso atualizado = cursoService.atualizar(id, dto.toEntity());
        if (atualizado == null) {
            throw new ResourceNotFoundException("Não é possível atualizar. Curso não encontrado com ID: " + id);
        }
        return ResponseEntity.ok(CursoDTO.fromEntity(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!cursoService.deletar(id)) {
            throw new ResourceNotFoundException("Não é possível remover. Curso não encontrado com ID: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}

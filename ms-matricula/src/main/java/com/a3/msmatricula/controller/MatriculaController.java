package com.a3.msmatricula.controller;

import com.a3.msmatricula.dto.MatriculaDTO;
import com.a3.msmatricula.exception.ResourceNotFoundException;
import com.a3.msmatricula.model.Matricula;
import com.a3.msmatricula.model.MatriculaDetalhe;
import com.a3.msmatricula.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas")
@CrossOrigin(origins = "*")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @GetMapping
    public List<MatriculaDetalhe> listarTodas() {
        return matriculaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaDetalhe> buscarPorId(@PathVariable Long id) {
        MatriculaDetalhe matricula = matriculaService.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula não encontrada com ID: " + id));
        return ResponseEntity.ok(matricula);
    }

    @PostMapping
    public ResponseEntity<MatriculaDTO> cadastrar(@RequestBody MatriculaDTO dto) {
        Matricula salva = matriculaService.salvar(dto.toEntity());
        return ResponseEntity.status(201).body(MatriculaDTO.fromEntity(salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatriculaDTO> atualizar(@PathVariable Long id, @RequestBody MatriculaDTO dto) {
        Matricula atualizada = matriculaService.atualizar(id, dto.toEntity());
        if (atualizada == null) {
            throw new ResourceNotFoundException("Não é possível atualizar. Matrícula não encontrada com ID: " + id);
        }
        return ResponseEntity.ok(MatriculaDTO.fromEntity(atualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!matriculaService.deletar(id)) {
            throw new ResourceNotFoundException("Não é possível remover. Matrícula não encontrada com ID: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}

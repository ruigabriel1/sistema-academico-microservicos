package com.a3.msmatricula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Ponto de entrada da aplicação Spring Boot do microserviço de Matrículas
// Este serviço é responsável por vincular Alunos a Cursos e se comunica com os outros microserviços
@SpringBootApplication
public class MsMatriculaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsMatriculaApplication.class, args);
    }
}

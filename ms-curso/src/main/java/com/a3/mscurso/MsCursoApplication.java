package com.a3.mscurso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Ponto de entrada da aplicação Spring Boot
// @SpringBootApplication ativa a auto-configuração, o scan de componentes e a configuração do Spring
@SpringBootApplication
public class MsCursoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCursoApplication.class, args);
    }
}

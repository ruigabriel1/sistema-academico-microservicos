package com.a3.msaluno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Ponto de entrada da aplicação Spring Boot
// @SpringBootApplication ativa a auto-configuração, o scan de componentes e a configuração do Spring
@SpringBootApplication
public class MsAlunoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAlunoApplication.class, args);
    }
}

package com.a3.msmatricula.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// Classe de configuração responsável por registrar beans da aplicação no contexto do Spring
// @Configuration indica que esta classe contém definições de beans
@Configuration
public class RestTemplateConfig {

    // Registra o RestTemplate como um Bean gerenciado pelo Spring
    // O RestTemplate é o cliente HTTP padrão do Spring para comunicação entre microserviços
    // Ele será injetado automaticamente em qualquer componente que o requisitar via @Autowired
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

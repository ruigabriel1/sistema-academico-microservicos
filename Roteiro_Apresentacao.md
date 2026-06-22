# Roteiro de Apresentação - A3 Sistemas Distribuídos

Este documento é um guia (script) para ajudar você e sua equipe a gravar o vídeo para o YouTube ou apresentar o projeto ao vivo para o professor Hélton Ribeiro Nunes.

---

## 1. Introdução (Abertura do Vídeo)

**Apresentador(a):**
> "Olá professor, bom dia/boa tarde/boa noite. Nós somos a equipe formada por [Seu Nome], [Nome 2] e [Nome 3]. 
> Hoje vamos apresentar a nossa atividade A3 da disciplina de Sistemas Distribuídos. 
> Nosso projeto é um **Sistema Acadêmico** que gerencia Alunos, Cursos e Matrículas, e ele foi totalmente pensado para funcionar em uma arquitetura de microsserviços."

---

## 2. Visão Geral e Arquitetura

**Apresentador(a):**
> "Atendendo aos requisitos do trabalho, nós dividimos nosso sistema em **3 microsserviços totalmente independentes**, além de um Front-end em React:
> 
> 1. **ms-aluno:** Fica na porta 8081 e gerencia o cadastro de estudantes. Ele tem seu próprio banco de dados MySQL chamado `aluno_db`.
> 2. **ms-curso:** Fica na porta 8082 e gerencia os cursos oferecidos pela instituição. Seu banco é o `curso_db`.
> 3. **ms-matricula:** Fica na porta 8083 e registra em qual curso o aluno se matriculou. Tem seu próprio banco `matricula_db`.
> 
> *Mostre rapidamente a tela do seu projeto rodando ou a interface interativa do Swagger.*"

---

## 3. A Comunicação REST (Obrigatória na A3)

**Apresentador(a):**
> "O coração do sistema distribuído está em como eles se comunicam. Nós garantimos que **não há banco de dados compartilhado** – cada serviço cuida dos seus próprios dados. 
> 
> Quando o usuário consulta uma matrícula, o `ms-matricula` precisa saber o nome do Aluno e o nome do Curso. Para isso, ele faz chamadas **HTTP REST** (utilizando o `RestTemplate` do Spring Boot) para o `ms-aluno` e para o `ms-curso`, pegando os dados necessários e montando a resposta completa para o Front-end.
> Isso atende o requisito principal de ter comunicação REST e integração entre microsserviços."

---

## 4. Diferenciais (Ganhando Pontos Extras)

**Apresentador(a):**
> "Além dos CRUDS e da comunicação REST, nós focamos em **boas práticas de mercado e diferenciais**, conforme as observações e o bônus da A3:
> 
> - **DTOs (Data Transfer Objects):** Utilizamos `Records` do Java 17, para trafegar os dados de forma leve, imutável e com mais performance.
> - **Swagger (OpenAPI):** Todos os nossos serviços têm uma documentação interativa das APIs, pronta para testes.
> - **Tratamento de Exceções Global:** Implementamos um `@ControllerAdvice`. Se alguém tentar buscar um aluno que não existe, em vez de estourar um erro feio no front-end, retornamos uma resposta padronizada com código HTTP correto (ex: 404 Not Found ou 400 Bad Request).
> - **Automação:** Fizemos scripts (`iniciar.bat` e `parar.bat`) para facilitar a execução de todos os serviços de uma vez."

---

## 5. Demonstração Prática (Mostrando no Front-end)

**Apresentador(a):**
> "Agora vamos demonstrar o funcionamento na prática pela nossa interface React. 
> *[Passos para mostrar na tela]*:
> 1. Vamos cadastrar um Curso.
> 2. Vamos cadastrar um Aluno.
> 3. E agora, vamos no sistema de Matrículas e vincular o Aluno ao Curso. 
> 
> Vejam que o front-end mostra as informações consolidadas. O `ms-matricula` foi lá nos outros serviços e buscou o nome correto do aluno e do curso."

---

## 💡 Perguntas Prováveis do Professor (e como responder!)

Se a apresentação for ao vivo, o professor **Hélton** pode fazer algumas perguntas para validar se vocês realmente entenderam a matéria. Estejam preparados:

### Pergunta 1: *"Por que vocês separaram os bancos de dados em vez de usar um banco único com todas as tabelas?"*
**Resposta Ideal:** "Porque em um sistema distribuído real, microsserviços precisam ter alta coesão e baixo acoplamento. Se a gente dividisse o mesmo banco, caso ele caísse, todos os sistemas ficariam fora do ar. Além disso, separar os bancos permite que cada serviço escale de forma independente ou até use uma tecnologia de banco de dados diferente no futuro."

### Pergunta 2: *"Como o `ms-matricula` consegue o nome do Aluno se ele não tem acesso ao banco de dados do Aluno?"*
**Resposta Ideal:** "Através da comunicação síncrona via HTTP REST. O `ms-matricula` guarda apenas o ID do aluno. Quando ele precisa mostrar os dados completos, ele usa a classe `RestTemplate` do Java para fazer um `GET` no endpoint `/alunos/{id}` do `ms-aluno`. Assim, o dono do dado (ms-aluno) é quem entrega a informação pronta."

### Pergunta 3: *"E o que acontece com a Matrícula se o serviço de Aluno (`ms-aluno`) estiver fora do ar?"*
**Resposta Ideal:** "Como a nossa comunicação atual é síncrona, a consulta de matrícula vai falhar ou dar erro de Timeout ao tentar buscar o aluno. Em uma evolução do projeto, para resolver isso, poderíamos implementar o padrão *Circuit Breaker* ou migrar para uma comunicação assíncrona usando mensageria (como RabbitMQ ou Kafka), mas mantivemos simples conforme o escopo da A3."

### Pergunta 4: *"O que é e por que usaram DTOs (Records) e o ControllerAdvice?"*
**Resposta Ideal:** "Usamos DTOs para não expor diretamente as nossas entidades do banco de dados (Models) na API. Isso garante mais segurança e controle do que estamos enviando e recebendo do usuário. O `ControllerAdvice` nós usamos para centralizar o tratamento de erros. Se algo der errado, garantimos que o front-end vai receber um JSON amigável e um status code correto, evitando vazar a stacktrace do Java."

---

**Apresentador(a) (Encerramento):**
> "É isso, professor. Acreditamos que cobrimos todos os requisitos funcionais e técnicos de forma robusta e organizada. Muito obrigado pela atenção!"

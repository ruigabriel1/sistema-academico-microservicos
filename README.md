# Sistema Acadêmico — A3 Sistemas Distribuídos

Aplicação baseada em **microserviços** desenvolvida com **Java 17 + Spring Boot 3.2** e **React (Vite)**.  
Tema: Sistema Acadêmico (Aluno, Curso, Matrícula). O projeto foca em boas práticas de mercado, como integração de **Swagger** (OpenAPI), uso de **DTOs (Records)**, **Tratamento Global de Exceções** e scripts de automação.

---

## 🎯 Melhorias Técnicas e Profissionais (Diferenciais)

- **Swagger (OpenAPI):** Documentação interativa das APIs de todos os microserviços.
- **Data Transfer Objects (DTOs):** Implementados utilizando `Records` do Java 17 para maior segurança, performance e imutabilidade no tráfego de dados.
- **Tratamento Global de Exceções (@ControllerAdvice):** Padronização das respostas de erro em toda a aplicação (ex: Erros 404, 400), garantindo maior legibilidade para o cliente (Front-end).
- **Validação de Entrada (Bean Validation):** Integração do `spring-boot-starter-validation` com anotações de validação nos DTOs (como `@NotBlank`, `@Email`, `@Size`, `@Min`, `@NotNull`) e validação ativa nos Controllers com `@Valid`.
- **Injeção de Dependência por Construtor:** Refatorado todas as injeções de dependência do Spring (Services e Controllers) de `@Autowired` em atributos privados para injeção via construtor, seguindo as melhores práticas oficiais.
- **Loggers Estruturados com SLF4J (Lombok @Slf4j):** Eliminação de `System.out.println` no fluxo do microsserviço de Matrícula, adotando logs com níveis de severidade adequados (`log.info` e `log.warn`).
- **Automação de Ambiente:** Scripts `.bat` criados para iniciar e parar todos os microserviços e o frontend com apenas um clique (`iniciar.bat` e `parar.bat`), otimizando o tempo de avaliação.
- **Documentação Moderna:** Além deste arquivo `.md`, o projeto conta com uma página `README.html` focada em legibilidade e apresentação profissional para a banca avaliadora.

---

## 🏗️ Arquitetura

```text
┌─────────────────────────────────────────────┐
│          FRONT-END (React + Vite)           │
│     Alunos | Cursos | Matrículas            │
│             porta: 5173                     │
└──────┬──────────────┬───────────────┬───────┘
       │              │               │
       ▼              ▼               ▼
┌───────────┐  ┌───────────┐  ┌──────────────────┐
│ ms-aluno  │  │ ms-curso  │  │  ms-matricula    │
│  :8081    │  │  :8082    │  │     :8083        │
│           │  │           │  │                  │
│ - Aluno   │  │ - Curso   │  │ - Matricula      │
│   (CRUD)  │  │   (CRUD)  │  │   (CRUD)         │
│           │  │           │  │ ► chama ms-aluno │
│           │  │           │  │ ► chama ms-curso │
└─────┬─────┘  └─────┬─────┘  └────────┬─────────┘
      │               │                 │
      ▼               ▼                 ▼
  [aluno_db]      [curso_db]      [matricula_db]
     MySQL           MySQL            MySQL
```

### Microserviços

| Serviço | Porta | Banco | Responsabilidade | Documentação API (Swagger) |
|---|---|---|---|---|
| `ms-aluno` | 8081 | `aluno_db` | CRUD de Alunos | `http://localhost:8081/swagger-ui.html` |
| `ms-curso` | 8082 | `curso_db` | CRUD de Cursos | `http://localhost:8082/swagger-ui.html` |
| `ms-matricula` | 8083 | `matricula_db` | CRUD de Matrículas | `http://localhost:8083/swagger-ui.html` |

### Comunicação entre Microserviços

O `ms-matricula` utiliza `RestTemplate` para chamadas HTTP REST:
- `GET http://localhost:8081/alunos/{id}` → busca dados do aluno
- `GET http://localhost:8082/cursos/{id}` → busca dados do curso

Ao consultar uma matrícula, o serviço monta uma resposta enriquecida com nome e e-mail do aluno e nome do curso, sem depender de um banco de dados compartilhado.

---

## 🛠️ Pré-requisitos

- **Java 17+**
- **Maven 3.8+**
- **MySQL 8+** rodando localmente na porta 3306 (Usuário: `root`, Senha: `root`)
- **Node.js 18+** e **npm**

---

## 🚀 Como Executar

### 1. Configure o MySQL

Crie um usuário `root` com senha `root` (ou ajuste o `application.properties` de cada serviço).  
Os bancos (`aluno_db`, `curso_db`, `matricula_db`) são criados automaticamente na primeira execução através do Hibernate.

### 2. Execução Automatizada (Recomendado)

Na raiz do projeto, basta dar um duplo clique nos scripts (Windows):
- **`iniciar.bat`**: Instala as dependências, inicia o Front-end e sobe todos os microserviços.
- **`parar.bat`**: Encerra os processos nas portas 8081, 8082, 8083 e 5173 de forma limpa.

### 3. Execução Manual

**Microserviços (em terminais separados):**
```bash
# Terminal 1 — ms-aluno
cd ms-aluno
mvn spring-boot:run

# Terminal 2 — ms-curso
cd ms-curso
mvn spring-boot:run

# Terminal 3 — ms-matricula
cd ms-matricula
mvn spring-boot:run
```

**Front-end:**
```bash
cd frontend
npm install
npm run dev
```

Acesse a interface da aplicação em: **http://localhost:5173**

---

## 💻 Stack Utilizada

- **Backend:** Java 17, Spring Boot 3.2, Spring Data JPA, Spring Web, Lombok, OpenAPI (Swagger)
- **Banco de dados:** MySQL 8
- **Comunicação entre serviços:** RestTemplate (HTTP REST)
- **Frontend:** React 18, Vite 5, React Router DOM 6, Axios
- **Design de Interface:** Custom CSS / Documentação (`README.html`)

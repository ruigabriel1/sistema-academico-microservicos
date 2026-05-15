# Sistema Acadêmico — A3 Sistemas Distribuídos

Aplicação baseada em **microserviços** desenvolvida com **Java + Spring Boot** e **React (Vite)**.  
Tema: Sistema Acadêmico (Aluno, Curso, Matrícula).

---

## Arquitetura

```
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

| Serviço | Porta | Banco | Responsabilidade |
|---|---|---|---|
| `ms-aluno` | 8081 | `aluno_db` | CRUD de Alunos |
| `ms-curso` | 8082 | `curso_db` | CRUD de Cursos |
| `ms-matricula` | 8083 | `matricula_db` | CRUD de Matrículas + consome ms-aluno e ms-curso |

### Comunicação entre Microserviços

O `ms-matricula` utiliza `RestTemplate` para chamadas HTTP REST:
- `GET http://localhost:8081/alunos/{id}` → busca dados do aluno
- `GET http://localhost:8082/cursos/{id}` → busca dados do curso

Ao consultar uma matrícula, o serviço monta uma resposta enriquecida com nome e e-mail do aluno e nome do curso, sem depender de um banco de dados compartilhado.

---

## Pré-requisitos

- **Java 17+**
- **Maven 3.8+**
- **MySQL 8+** rodando localmente na porta 3306
- **Node.js 18+** e **npm**

---

## Como Executar

### 1. Configure o MySQL

Crie um usuário `root` com senha `root` (ou ajuste o `application.properties` de cada serviço).  
Os bancos (`aluno_db`, `curso_db`, `matricula_db`) são criados automaticamente na primeira execução.

### 2. Inicie os microserviços (em terminais separados)

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

### 3. Inicie o front-end

```bash
cd frontend
npm install
npm run dev
```

Acesse: **http://localhost:5173**

---

## Endpoints das APIs

### ms-aluno — `http://localhost:8081`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/alunos` | Lista todos os alunos |
| GET | `/alunos/{id}` | Busca aluno por ID |
| POST | `/alunos` | Cadastra novo aluno |
| PUT | `/alunos/{id}` | Atualiza aluno |
| DELETE | `/alunos/{id}` | Remove aluno |

### ms-curso — `http://localhost:8082`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/cursos` | Lista todos os cursos |
| GET | `/cursos/{id}` | Busca curso por ID |
| POST | `/cursos` | Cadastra novo curso |
| PUT | `/cursos/{id}` | Atualiza curso |
| DELETE | `/cursos/{id}` | Remove curso |

### ms-matricula — `http://localhost:8083`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/matriculas` | Lista matrículas com dados de aluno e curso |
| GET | `/matriculas/{id}` | Busca matrícula por ID com dados enriquecidos |
| POST | `/matriculas` | Registra nova matrícula |
| PUT | `/matriculas/{id}` | Atualiza matrícula |
| DELETE | `/matriculas/{id}` | Remove matrícula |

---

## Stack Utilizada

- **Backend:** Java 17, Spring Boot 3.2, Spring Data JPA, Spring Web, Lombok
- **Banco de dados:** MySQL 8
- **Comunicação entre serviços:** RestTemplate (HTTP REST)
- **Frontend:** React 18, Vite 5, React Router DOM 6, Axios

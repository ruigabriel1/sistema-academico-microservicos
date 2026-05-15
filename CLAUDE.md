# CLAUDE.md — Contexto do Projeto A3 (Sistemas Distribuídos)

Este arquivo restaura o contexto completo do projeto para um novo chat de IA.

---

## Objetivo do Projeto

Trabalho A3 da disciplina **Sistemas Distribuídos**. Sistema acadêmico com arquitetura de **microserviços** usando Java Spring Boot + React frontend. O tema é **Sistema Acadêmico**: Alunos, Cursos e Matrículas.

---

## Estrutura do Projeto

```
c:\Users\ruiga\OneDrive\Documents\A3\
├── ms-aluno/          → Microserviço de Alunos (porta 8081)
├── ms-curso/          → Microserviço de Cursos (porta 8082)
├── ms-matricula/      → Microserviço de Matrículas (porta 8083)
├── frontend/          → React + Vite (porta 5173)
├── iniciar.bat        → Inicia todos os serviços (duplo clique)
├── parar.bat          → Para todos os serviços (duplo clique)
└── CLAUDE.md          → Este arquivo
```

---

## Stack Tecnológica

| Componente     | Tecnologia                              |
|----------------|-----------------------------------------|
| Backend        | Java 17 + Spring Boot 3.2.5             |
| Persistência   | Spring Data JPA + Hibernate             |
| Banco de dados | MySQL 8.4.9                             |
| Comunicação    | RestTemplate (HTTP entre microserviços) |
| Frontend       | React 18 + Vite + Axios                 |
| Build backend  | Maven 3.9.15                            |

---

## Ferramentas Instaladas (caminhos exatos)

| Ferramenta   | Versão   | Caminho de Instalação                                                |
|--------------|----------|----------------------------------------------------------------------|
| Java (JDK)   | 17.0.19  | `C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot`          |
| Maven        | 3.9.15   | `C:\Program Files\Maven`                                             |
| Node.js      | 24.15.0  | `C:\Program Files\nodejs`                                            |
| npm          | 11.12.1  | (junto com Node.js)                                                  |
| MySQL        | 8.4.9    | `C:\Program Files\MySQL\MySQL Server 8.4`                            |

**JAVA_HOME** e **MAVEN_HOME** estão configurados nas variáveis de ambiente Machine (System).

---

## MySQL

- **Serviço Windows:** `MySQL84`
- **Porta:** 3306
- **Usuário:** `root`
- **Senha:** `root`
- **Bancos criados automaticamente** (pelo Hibernate na primeira inicialização):
  - `aluno_db`
  - `curso_db`
  - `matricula_db`

Comandos úteis:
```powershell
Get-Service MySQL84          # verificar status
Start-Service MySQL84        # iniciar manualmente
Stop-Service MySQL84         # parar manualmente
```

---

## Microserviços

### ms-aluno (porta 8081)
- **Banco:** `aluno_db`, tabela `alunos`
- **Entidade:** `Aluno` — campos: `id`, `nome`, `email` (único), `cpf` (único), `dataNascimento`
- **Endpoints REST:**
  - `GET    /alunos`        → lista todos
  - `GET    /alunos/{id}`   → busca por ID
  - `POST   /alunos`        → cria aluno
  - `PUT    /alunos/{id}`   → atualiza aluno
  - `DELETE /alunos/{id}`   → remove aluno

### ms-curso (porta 8082)
- **Banco:** `curso_db`, tabela `cursos`
- **Entidade:** `Curso` — campos: `id`, `nome` (único), `descricao`, `cargaHoraria`
- **Endpoints REST:**
  - `GET    /cursos`
  - `GET    /cursos/{id}`
  - `POST   /cursos`
  - `PUT    /cursos/{id}`
  - `DELETE /cursos/{id}`

### ms-matricula (porta 8083)
- **Banco:** `matricula_db`, tabela `matriculas`
- **Entidade:** `Matricula` — campos: `id`, `alunoId`, `cursoId`, `dataMatricula`, `status`
- **Comunicação:** usa `RestTemplate` para buscar dados de ms-aluno e ms-curso
  - URL aluno:  `http://localhost:8081`
  - URL curso:  `http://localhost:8082`
- **Endpoints REST:**
  - `GET    /matriculas`               → lista (retorna `MatriculaDetalhe` com dados completos)
  - `GET    /matriculas/{id}`
  - `POST   /matriculas`               → body: `{ "alunoId": 1, "cursoId": 1 }`
  - `PUT    /matriculas/{id}`
  - `DELETE /matriculas/{id}`

---

## Frontend (React + Vite)

- **Porta:** 5173
- **Páginas:** Alunos, Cursos, Matrículas (cada uma com tabela + formulário CRUD)
- **API calls via Axios** em `src/services/api.js`
- **`node_modules/`** já instalado (não precisa rodar `npm install` novamente)

---

## Como Rodar o Sistema

### Iniciar tudo (forma prática)
Clique duplo em `iniciar.bat` na raiz do projeto.
- Verifica/inicia MySQL automaticamente
- Abre 4 janelas CMD (ms-aluno, ms-curso, ms-matricula, frontend)
- Abre `http://localhost:5173` no navegador após 40s

### Parar tudo (forma prática)
Clique duplo em `parar.bat` na raiz do projeto.
- Mata todos os processos `java.exe` (microserviços)
- Mata todos os processos `node.exe` (frontend)
- Para o serviço MySQL84

### Iniciar manualmente (PowerShell)
```powershell
# Configurar PATH (fazer isso em cada terminal novo)
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot"
$env:Path = "$env:JAVA_HOME\bin;C:\Program Files\Maven\bin;C:\Program Files\nodejs;" + $env:Path

# ms-aluno
cd "c:\Users\ruiga\OneDrive\Documents\A3\ms-aluno"
mvn spring-boot:run

# ms-curso (novo terminal)
cd "c:\Users\ruiga\OneDrive\Documents\A3\ms-curso"
mvn spring-boot:run

# ms-matricula (novo terminal)
cd "c:\Users\ruiga\OneDrive\Documents\A3\ms-matricula"
mvn spring-boot:run

# frontend (novo terminal)
cd "c:\Users\ruiga\OneDrive\Documents\A3\frontend"
npm run dev
```

---

## Estrutura de Arquivos (detalhada)

```
ms-aluno/src/main/java/com/a3/msaluno/
├── MsAlunoApplication.java
├── controller/AlunoController.java
├── model/Aluno.java
├── repository/AlunoRepository.java
└── service/AlunoService.java

ms-curso/src/main/java/com/a3/mscurso/
├── MsCursoApplication.java
├── controller/CursoController.java
├── model/Curso.java
├── repository/CursoRepository.java
└── service/CursoService.java

ms-matricula/src/main/java/com/a3/msmatricula/
├── MsMatriculaApplication.java
├── config/RestTemplateConfig.java
├── controller/MatriculaController.java
├── model/
│   ├── Matricula.java
│   ├── AlunoInfo.java       ← DTO para dados do ms-aluno
│   ├── CursoInfo.java       ← DTO para dados do ms-curso
│   └── MatriculaDetalhe.java ← DTO retornado ao frontend
├── repository/MatriculaRepository.java
└── service/MatriculaService.java

frontend/src/
├── App.jsx
├── App.css
├── main.jsx
├── services/api.js
└── pages/
    ├── Alunos.jsx
    ├── Cursos.jsx
    └── Matriculas.jsx
```

---

## Troubleshooting

**`mvn` não reconhecido no terminal:**
```powershell
$env:Path = "C:\Program Files\Maven\bin;C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot\bin;" + $env:Path
```

**`npm` não reconhecido no terminal:**
```powershell
$env:Path = "C:\Program Files\nodejs;" + $env:Path
```

**npm com erro de script bloqueado:**
```powershell
Set-ExecutionPolicy -Scope CurrentUser -ExecutionPolicy RemoteSigned
```

**Frontend mostra "Erro ao carregar dados":**
→ Os microserviços ainda não subiram. Aguarde aparecer `Started MsXxxApplication` nos terminais Java.

**Erro de conexão MySQL ao iniciar microserviço:**
```powershell
Get-Service MySQL84    # deve estar Running
Start-Service MySQL84  # se estiver parado
```

**Resetar banco de dados (apagar todos os dados):**
```sql
-- conectar: mysql -u root -proot
DROP DATABASE aluno_db;
DROP DATABASE curso_db;
DROP DATABASE matricula_db;
-- os bancos são recriados automaticamente na próxima inicialização
```

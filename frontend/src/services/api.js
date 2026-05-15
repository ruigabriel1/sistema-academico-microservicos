import axios from 'axios';

// =============================================
// URLs base de cada microserviço do backend
// =============================================
const ALUNO_URL     = 'http://localhost:8081/alunos';
const CURSO_URL     = 'http://localhost:8082/cursos';
const MATRICULA_URL = 'http://localhost:8083/matriculas';

// =============================================
// Funções de acesso à API do ms-aluno (porta 8081)
// =============================================
export const alunoApi = {
    // Busca todos os alunos cadastrados
    listar: () => axios.get(ALUNO_URL),

    // Busca um aluno específico pelo ID
    buscarPorId: (id) => axios.get(`${ALUNO_URL}/${id}`),

    // Cadastra um novo aluno
    criar: (data) => axios.post(ALUNO_URL, data),

    // Atualiza os dados de um aluno existente
    atualizar: (id, data) => axios.put(`${ALUNO_URL}/${id}`, data),

    // Remove um aluno pelo ID
    deletar: (id) => axios.delete(`${ALUNO_URL}/${id}`),
};

// =============================================
// Funções de acesso à API do ms-curso (porta 8082)
// =============================================
export const cursoApi = {
    // Busca todos os cursos cadastrados
    listar: () => axios.get(CURSO_URL),

    // Busca um curso específico pelo ID
    buscarPorId: (id) => axios.get(`${CURSO_URL}/${id}`),

    // Cadastra um novo curso
    criar: (data) => axios.post(CURSO_URL, data),

    // Atualiza os dados de um curso existente
    atualizar: (id, data) => axios.put(`${CURSO_URL}/${id}`, data),

    // Remove um curso pelo ID
    deletar: (id) => axios.delete(`${CURSO_URL}/${id}`),
};

// =============================================
// Funções de acesso à API do ms-matricula (porta 8083)
// =============================================
export const matriculaApi = {
    // Busca todas as matrículas (com dados enriquecidos de aluno e curso)
    listar: () => axios.get(MATRICULA_URL),

    // Busca uma matrícula específica pelo ID
    buscarPorId: (id) => axios.get(`${MATRICULA_URL}/${id}`),

    // Registra uma nova matrícula
    criar: (data) => axios.post(MATRICULA_URL, data),

    // Atualiza os dados de uma matrícula existente
    atualizar: (id, data) => axios.put(`${MATRICULA_URL}/${id}`, data),

    // Remove uma matrícula pelo ID
    deletar: (id) => axios.delete(`${MATRICULA_URL}/${id}`),
};

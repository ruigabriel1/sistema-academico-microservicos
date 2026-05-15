import { useState, useEffect } from 'react';
import { matriculaApi, alunoApi, cursoApi } from '../services/api';

// Estado inicial do formulário de matrícula
const FORM_VAZIO = { alunoId: '', cursoId: '', dataMatricula: '', status: 'ATIVA' };

// Página de gerenciamento de Matrículas
// Demonstra a comunicação entre microserviços:
// - Carrega alunos do ms-aluno (porta 8081) para o dropdown
// - Carrega cursos do ms-curso (porta 8082) para o dropdown
// - Gerencia matrículas no ms-matricula (porta 8083), que por sua vez enriquece
//   a resposta buscando nomes de aluno e curso nos outros serviços via REST
function Matriculas() {
    // Lista de matrículas enriquecidas (com dados de aluno e curso) do ms-matricula
    const [matriculas, setMatriculas] = useState([]);

    // Lista de alunos para preencher o dropdown do formulário
    const [alunos, setAlunos] = useState([]);

    // Lista de cursos para preencher o dropdown do formulário
    const [cursos, setCursos] = useState([]);

    // Dados do formulário (nova matrícula ou matrícula em edição)
    const [form, setForm] = useState(FORM_VAZIO);

    // ID da matrícula em modo de edição (null = modo de cadastro)
    const [editandoId, setEditandoId] = useState(null);

    // Carrega todos os dados necessários ao montar a página
    useEffect(() => {
        carregarTudo();
    }, []);

    // Carrega matrículas, alunos e cursos em paralelo para melhor desempenho
    const carregarTudo = async () => {
        try {
            const [resMatriculas, resAlunos, resCursos] = await Promise.all([
                matriculaApi.listar(),
                alunoApi.listar(),
                cursoApi.listar(),
            ]);
            setMatriculas(resMatriculas.data);
            setAlunos(resAlunos.data);
            setCursos(resCursos.data);
        } catch {
            alert('Erro ao carregar dados. Verifique se todos os microsserviços estão em execução.');
        }
    };

    // Atualiza o estado do formulário conforme o usuário interage
    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    // Envia o formulário para criar ou atualizar uma matrícula
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // Garante que os IDs são enviados como números inteiros
            const payload = {
                ...form,
                alunoId: Number(form.alunoId),
                cursoId: Number(form.cursoId),
            };

            if (editandoId) {
                // Modo edição: chama o endpoint PUT do ms-matricula
                await matriculaApi.atualizar(editandoId, payload);
            } else {
                // Modo cadastro: chama o endpoint POST do ms-matricula
                await matriculaApi.criar(payload);
            }
            // Reseta formulário e atualiza a tabela após salvar
            setForm(FORM_VAZIO);
            setEditandoId(null);
            carregarTudo();
        } catch {
            alert('Erro ao salvar matrícula. Verifique os dados e tente novamente.');
        }
    };

    // Preenche o formulário com os dados da matrícula selecionada para edição
    const handleEditar = (m) => {
        setEditandoId(m.id);
        setForm({
            // Se os dados do aluno/curso estiverem disponíveis, usa o ID do objeto; senão, mantém vazio
            alunoId:        m.aluno ? m.aluno.id : '',
            cursoId:        m.curso ? m.curso.id : '',
            dataMatricula:  m.dataMatricula,
            status:         m.status,
        });
    };

    // Exclui uma matrícula após confirmação do usuário
    const handleDeletar = async (id) => {
        if (window.confirm('Tem certeza que deseja excluir esta matrícula?')) {
            try {
                await matriculaApi.deletar(id);
                carregarTudo();
            } catch {
                alert('Erro ao excluir matrícula.');
            }
        }
    };

    // Cancela a edição e limpa o formulário
    const handleCancelar = () => {
        setEditandoId(null);
        setForm(FORM_VAZIO);
    };

    return (
        <div>
            <h2>Gerenciamento de Matrículas</h2>

            {/* Formulário de cadastro ou edição de matrícula */}
            <div className="form-card">
                <h3>{editandoId ? 'Editar Matrícula' : 'Nova Matrícula'}</h3>
                <form onSubmit={handleSubmit}>
                    {/* Dropdown preenchido com os alunos carregados do ms-aluno */}
                    <div>
                        <select name="alunoId" value={form.alunoId} onChange={handleChange} required>
                            <option value="">Selecione o Aluno</option>
                            {alunos.map((a) => (
                                <option key={a.id} value={a.id}>
                                    {a.nome} — {a.email}
                                </option>
                            ))}
                        </select>
                    </div>

                    {/* Dropdown preenchido com os cursos carregados do ms-curso */}
                    <div>
                        <select name="cursoId" value={form.cursoId} onChange={handleChange} required>
                            <option value="">Selecione o Curso</option>
                            {cursos.map((c) => (
                                <option key={c.id} value={c.id}>
                                    {c.nome} ({c.cargaHoraria}h)
                                </option>
                            ))}
                        </select>
                    </div>

                    <div>
                        <input
                            name="dataMatricula"
                            value={form.dataMatricula}
                            onChange={handleChange}
                            placeholder="Data da Matrícula (AAAA-MM-DD)"
                            required
                        />
                    </div>

                    {/* Dropdown de status da matrícula */}
                    <div>
                        <select name="status" value={form.status} onChange={handleChange}>
                            <option value="ATIVA">Ativa</option>
                            <option value="CANCELADA">Cancelada</option>
                            <option value="CONCLUIDA">Concluída</option>
                        </select>
                    </div>

                    <div>
                        <button type="submit" className="btn-primary">
                            {editandoId ? 'Salvar Alterações' : 'Matricular'}
                        </button>
                        {editandoId && (
                            <button type="button" className="btn-secondary" onClick={handleCancelar}>
                                Cancelar
                            </button>
                        )}
                    </div>
                </form>
            </div>

            {/* Tabela com a listagem de todas as matrículas
                Os dados de Aluno e Curso são enriquecidos pelo ms-matricula via REST */}
            <div className="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Aluno</th>
                            <th>Curso</th>
                            <th>Data da Matrícula</th>
                            <th>Status</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {matriculas.length === 0 ? (
                            <tr>
                                <td colSpan="6" style={{ textAlign: 'center', color: '#888' }}>
                                    Nenhuma matrícula registrada.
                                </td>
                            </tr>
                        ) : (
                            matriculas.map((m) => (
                                <tr key={m.id}>
                                    <td>{m.id}</td>
                                    {/* Nome do aluno obtido via comunicação REST entre ms-matricula e ms-aluno */}
                                    <td>{m.aluno ? m.aluno.nome : <em style={{ color: '#888' }}>indisponível</em>}</td>
                                    {/* Nome do curso obtido via comunicação REST entre ms-matricula e ms-curso */}
                                    <td>{m.curso ? m.curso.nome : <em style={{ color: '#888' }}>indisponível</em>}</td>
                                    <td>{m.dataMatricula}</td>
                                    <td>
                                        {/* Badge colorido conforme o status da matrícula */}
                                        <span className={`status-badge status-${m.status}`}>
                                            {m.status}
                                        </span>
                                    </td>
                                    <td>
                                        <button className="btn-edit" onClick={() => handleEditar(m)}>
                                            Editar
                                        </button>
                                        <button className="btn-delete" onClick={() => handleDeletar(m.id)}>
                                            Excluir
                                        </button>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default Matriculas;

import { useState, useEffect } from 'react';
import { cursoApi } from '../services/api';

// Estado inicial do formulário de curso
const FORM_VAZIO = { nome: '', descricao: '', cargaHoraria: '' };

// Página de gerenciamento de Cursos
// Exibe uma tabela com todos os cursos e um formulário para cadastro e edição
function Cursos() {
    // Lista de cursos carregada do ms-curso
    const [cursos, setCursos] = useState([]);

    // Dados do formulário (novo curso ou curso em edição)
    const [form, setForm] = useState(FORM_VAZIO);

    // ID do curso em modo de edição (null = modo de cadastro)
    const [editandoId, setEditandoId] = useState(null);

    // Carrega a lista de cursos ao montar o componente
    useEffect(() => {
        carregarCursos();
    }, []);

    // Busca todos os cursos no ms-curso e atualiza o estado
    const carregarCursos = async () => {
        try {
            const resposta = await cursoApi.listar();
            setCursos(resposta.data);
        } catch {
            alert('Erro ao conectar com o serviço de cursos. Verifique se o ms-curso está em execução na porta 8082.');
        }
    };

    // Atualiza o estado do formulário conforme o usuário digita
    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    // Envia o formulário para criar ou atualizar um curso
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // Converte cargaHoraria para número inteiro antes de enviar
            const payload = { ...form, cargaHoraria: Number(form.cargaHoraria) };

            if (editandoId) {
                // Modo edição: chama o endpoint PUT
                await cursoApi.atualizar(editandoId, payload);
            } else {
                // Modo cadastro: chama o endpoint POST
                await cursoApi.criar(payload);
            }
            // Reseta formulário e atualiza a tabela após salvar
            setForm(FORM_VAZIO);
            setEditandoId(null);
            carregarCursos();
        } catch {
            alert('Erro ao salvar curso. Verifique os dados e tente novamente.');
        }
    };

    // Preenche o formulário com os dados do curso selecionado para edição
    const handleEditar = (curso) => {
        setEditandoId(curso.id);
        setForm({
            nome: curso.nome,
            descricao: curso.descricao,
            cargaHoraria: curso.cargaHoraria,
        });
    };

    // Exclui um curso após confirmação do usuário
    const handleDeletar = async (id) => {
        if (window.confirm('Tem certeza que deseja excluir este curso?')) {
            try {
                await cursoApi.deletar(id);
                carregarCursos();
            } catch {
                alert('Erro ao excluir curso.');
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
            <h2>Gerenciamento de Cursos</h2>

            {/* Formulário de cadastro ou edição de curso */}
            <div className="form-card">
                <h3>{editandoId ? 'Editar Curso' : 'Novo Curso'}</h3>
                <form onSubmit={handleSubmit}>
                    <div>
                        <input
                            name="nome"
                            value={form.nome}
                            onChange={handleChange}
                            placeholder="Nome do curso"
                            required
                        />
                    </div>
                    <div>
                        <input
                            name="descricao"
                            value={form.descricao}
                            onChange={handleChange}
                            placeholder="Descrição do curso"
                            required
                        />
                    </div>
                    <div>
                        <input
                            name="cargaHoraria"
                            type="number"
                            min="1"
                            value={form.cargaHoraria}
                            onChange={handleChange}
                            placeholder="Carga horária (horas)"
                            required
                        />
                    </div>
                    <div>
                        <button type="submit" className="btn-primary">
                            {editandoId ? 'Salvar Alterações' : 'Cadastrar Curso'}
                        </button>
                        {editandoId && (
                            <button type="button" className="btn-secondary" onClick={handleCancelar}>
                                Cancelar
                            </button>
                        )}
                    </div>
                </form>
            </div>

            {/* Tabela com a listagem de todos os cursos cadastrados */}
            <div className="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>Descrição</th>
                            <th>Carga Horária</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {cursos.length === 0 ? (
                            <tr>
                                <td colSpan="5" style={{ textAlign: 'center', color: '#888' }}>
                                    Nenhum curso cadastrado.
                                </td>
                            </tr>
                        ) : (
                            cursos.map((curso) => (
                                <tr key={curso.id}>
                                    <td>{curso.id}</td>
                                    <td>{curso.nome}</td>
                                    <td>{curso.descricao}</td>
                                    <td>{curso.cargaHoraria}h</td>
                                    <td>
                                        <button className="btn-edit" onClick={() => handleEditar(curso)}>
                                            Editar
                                        </button>
                                        <button className="btn-delete" onClick={() => handleDeletar(curso.id)}>
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

export default Cursos;

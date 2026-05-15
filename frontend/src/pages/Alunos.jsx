import { useState, useEffect } from 'react';
import { alunoApi } from '../services/api';

// Estado inicial do formulário de aluno
const FORM_VAZIO = { nome: '', email: '', cpf: '', dataNascimento: '' };

// Página de gerenciamento de Alunos
// Exibe uma tabela com todos os alunos e um formulário para cadastro e edição
function Alunos() {
    // Lista de alunos carregada do ms-aluno
    const [alunos, setAlunos] = useState([]);

    // Dados do formulário (novo aluno ou aluno em edição)
    const [form, setForm] = useState(FORM_VAZIO);

    // ID do aluno em modo de edição (null = modo de cadastro)
    const [editandoId, setEditandoId] = useState(null);

    // Carrega a lista de alunos ao montar o componente
    useEffect(() => {
        carregarAlunos();
    }, []);

    // Busca todos os alunos no ms-aluno e atualiza o estado
    const carregarAlunos = async () => {
        try {
            const resposta = await alunoApi.listar();
            setAlunos(resposta.data);
        } catch {
            alert('Erro ao conectar com o serviço de alunos. Verifique se o ms-aluno está em execução na porta 8081.');
        }
    };

    // Atualiza o estado do formulário conforme o usuário digita
    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    // Envia o formulário para criar ou atualizar um aluno
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (editandoId) {
                // Modo edição: chama o endpoint PUT
                await alunoApi.atualizar(editandoId, form);
            } else {
                // Modo cadastro: chama o endpoint POST
                await alunoApi.criar(form);
            }
            // Reseta formulário e atualiza a tabela após salvar
            setForm(FORM_VAZIO);
            setEditandoId(null);
            carregarAlunos();
        } catch {
            alert('Erro ao salvar aluno. Verifique os dados e tente novamente.');
        }
    };

    // Preenche o formulário com os dados do aluno selecionado para edição
    const handleEditar = (aluno) => {
        setEditandoId(aluno.id);
        setForm({
            nome: aluno.nome,
            email: aluno.email,
            cpf: aluno.cpf,
            dataNascimento: aluno.dataNascimento,
        });
    };

    // Exclui um aluno após confirmação do usuário
    const handleDeletar = async (id) => {
        if (window.confirm('Tem certeza que deseja excluir este aluno?')) {
            try {
                await alunoApi.deletar(id);
                carregarAlunos();
            } catch {
                alert('Erro ao excluir aluno.');
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
            <h2>Gerenciamento de Alunos</h2>

            {/* Formulário de cadastro ou edição de aluno */}
            <div className="form-card">
                <h3>{editandoId ? 'Editar Aluno' : 'Novo Aluno'}</h3>
                <form onSubmit={handleSubmit}>
                    <div>
                        <input
                            name="nome"
                            value={form.nome}
                            onChange={handleChange}
                            placeholder="Nome completo"
                            required
                        />
                    </div>
                    <div>
                        <input
                            name="email"
                            type="email"
                            value={form.email}
                            onChange={handleChange}
                            placeholder="E-mail"
                            required
                        />
                    </div>
                    <div>
                        <input
                            name="cpf"
                            value={form.cpf}
                            onChange={handleChange}
                            placeholder="CPF (ex: 123.456.789-00)"
                            required
                        />
                    </div>
                    <div>
                        <input
                            name="dataNascimento"
                            value={form.dataNascimento}
                            onChange={handleChange}
                            placeholder="Data de Nascimento (AAAA-MM-DD)"
                            required
                        />
                    </div>
                    <div>
                        <button type="submit" className="btn-primary">
                            {editandoId ? 'Salvar Alterações' : 'Cadastrar Aluno'}
                        </button>
                        {editandoId && (
                            <button type="button" className="btn-secondary" onClick={handleCancelar}>
                                Cancelar
                            </button>
                        )}
                    </div>
                </form>
            </div>

            {/* Tabela com a listagem de todos os alunos cadastrados */}
            <div className="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>E-mail</th>
                            <th>CPF</th>
                            <th>Data de Nascimento</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {alunos.length === 0 ? (
                            <tr>
                                <td colSpan="6" style={{ textAlign: 'center', color: '#888' }}>
                                    Nenhum aluno cadastrado.
                                </td>
                            </tr>
                        ) : (
                            alunos.map((aluno) => (
                                <tr key={aluno.id}>
                                    <td>{aluno.id}</td>
                                    <td>{aluno.nome}</td>
                                    <td>{aluno.email}</td>
                                    <td>{aluno.cpf}</td>
                                    <td>{aluno.dataNascimento}</td>
                                    <td>
                                        <button className="btn-edit" onClick={() => handleEditar(aluno)}>
                                            Editar
                                        </button>
                                        <button className="btn-delete" onClick={() => handleDeletar(aluno.id)}>
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

export default Alunos;

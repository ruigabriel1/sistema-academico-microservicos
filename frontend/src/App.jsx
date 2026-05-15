import { BrowserRouter as Router, Routes, Route, NavLink } from 'react-router-dom';
import Alunos from './pages/Alunos';
import Cursos from './pages/Cursos';
import Matriculas from './pages/Matriculas';
import './App.css';

// Componente raiz da aplicação
// Define o roteamento entre as páginas e o layout principal (cabeçalho e navegação)
function App() {
    return (
        <Router>
            <div className="app">
                {/* Cabeçalho principal com o título e a navegação entre módulos */}
                <header className="header">
                    <h1>Sistema Acadêmico</h1>
                    <nav>
                        {/* NavLink aplica a classe 'active' automaticamente na rota atual */}
                        <NavLink to="/alunos" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>
                            Alunos
                        </NavLink>
                        <NavLink to="/cursos" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>
                            Cursos
                        </NavLink>
                        <NavLink to="/matriculas" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>
                            Matrículas
                        </NavLink>
                    </nav>
                </header>

                {/* Área de conteúdo principal — renderiza a página conforme a rota acessada */}
                <main className="main-content">
                    <Routes>
                        {/* Página inicial com uma mensagem de boas-vindas */}
                        <Route path="/" element={
                            <div className="welcome">
                                <h2>Bem-vindo ao Sistema Acadêmico</h2>
                                <p>Utilize o menu acima para gerenciar Alunos, Cursos e Matrículas.</p>
                            </div>
                        } />
                        <Route path="/alunos" element={<Alunos />} />
                        <Route path="/cursos" element={<Cursos />} />
                        <Route path="/matriculas" element={<Matriculas />} />
                    </Routes>
                </main>
            </div>
        </Router>
    );
}

export default App;

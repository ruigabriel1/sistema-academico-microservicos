import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.jsx';
import './App.css';

// Ponto de entrada do React
// Monta o componente raiz App dentro do elemento <div id="root"> do index.html
// React.StrictMode ativa verificações adicionais em desenvolvimento para detectar problemas
ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <App />
    </React.StrictMode>
);

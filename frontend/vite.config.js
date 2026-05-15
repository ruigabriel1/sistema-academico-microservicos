import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// Configuração do Vite para o projeto React
// O plugin @vitejs/plugin-react habilita JSX e Fast Refresh durante o desenvolvimento
export default defineConfig({
    plugins: [react()],
    server: {
        // Porta do servidor de desenvolvimento do front-end
        port: 5173,
    },
});

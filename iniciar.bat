@echo off
chcp 65001 >nul
title Sistema Academico - Inicializador

echo.
echo =====================================================
echo   SISTEMA ACADEMICO - Iniciando todos os servicos
echo =====================================================
echo.

:: Verifica MySQL
echo [1/5] Verificando MySQL...
sc query MySQL84 | find "RUNNING" >nul 2>&1
if %errorlevel% neq 0 (
    echo       MySQL parado. Iniciando servico...
    net start MySQL84 >nul 2>&1
    timeout /t 3 /nobreak >nul
    echo       MySQL iniciado!
) else (
    echo       MySQL ja esta rodando.
)

:: ms-aluno (porta 8081)
echo [2/5] Iniciando ms-aluno   (porta 8081)...
start "ms-aluno  :8081" cmd /k "cd /d %~dp0ms-aluno & mvn spring-boot:run"
timeout /t 2 /nobreak >nul

:: ms-curso (porta 8082)
echo [3/5] Iniciando ms-curso   (porta 8082)...
start "ms-curso  :8082" cmd /k "cd /d %~dp0ms-curso & mvn spring-boot:run"
timeout /t 2 /nobreak >nul

:: ms-matricula (porta 8083)
echo [4/5] Iniciando ms-matricula (porta 8083)...
start "ms-matricula :8083" cmd /k "cd /d %~dp0ms-matricula & mvn spring-boot:run"

:: ============================================================
:: Aguarda os microserviços inicializarem antes do frontend
:: ============================================================
echo.
echo       Aguardando os microservicos subirem (30s)...
timeout /t 30 /nobreak >nul

:: Frontend (porta 5173)
echo [5/5] Iniciando Frontend    (porta 5173)...
if not exist "%~dp0frontend\node_modules" (
    echo       Instalando dependencias do frontend pela primeira vez...
    start "Frontend :5173" cmd /k "set PATH=C:\Program Files\nodejs;%PATH% & cd /d %~dp0frontend & npm install & npm run dev"
) else (
    start "Frontend :5173" cmd /k "set PATH=C:\Program Files\nodejs;%PATH% & cd /d %~dp0frontend & npm run dev"
)

:: ============================================================
:: Abre o navegador após mais alguns segundos
:: ============================================================
echo.
echo       Aguardando frontend iniciar (10s)...
timeout /t 10 /nobreak >nul
echo.
echo [OK] Abrindo http://localhost:5173 no navegador...
start http://localhost:5173

echo.
echo =====================================================
echo   Todos os servicos foram iniciados!
echo.
echo   ms-aluno    → http://localhost:8081/alunos
echo   ms-curso    → http://localhost:8082/cursos
echo   ms-matricula→ http://localhost:8083/matriculas
echo   Frontend    → http://localhost:5173
echo.
echo   Para encerrar, feche as 4 janelas abertas.
echo =====================================================
echo.
pause

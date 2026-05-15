@echo off
chcp 65001 >nul
title Sistema Academico - Parando servicos

echo.
echo =====================================================
echo   SISTEMA ACADEMICO - Parando todos os servicos
echo =====================================================
echo.

:: Para o frontend (Node.js / Vite)
echo [1/3] Parando Frontend (node.exe)...
taskkill /F /IM node.exe >nul 2>&1
if %errorlevel% equ 0 (
    echo       Frontend parado.
) else (
    echo       Frontend nao estava rodando.
)

:: Para todos os microservicos Java (spring-boot:run usa java.exe)
echo [2/3] Parando microservicos Java...
taskkill /F /IM java.exe >nul 2>&1
if %errorlevel% equ 0 (
    echo       Microservicos parados (ms-aluno, ms-curso, ms-matricula).
) else (
    echo       Nenhum microservico estava rodando.
)

:: Para o MySQL (opcional - comente a linha abaixo se quiser manter o MySQL ativo)
echo [3/3] Parando MySQL...
net stop MySQL84 >nul 2>&1
if %errorlevel% equ 0 (
    echo       MySQL parado.
) else (
    echo       MySQL nao estava rodando.
)

echo.
echo =====================================================
echo   Todos os servicos foram encerrados.
echo =====================================================
echo.
pause

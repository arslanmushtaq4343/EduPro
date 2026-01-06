@echo off
echo ========================================
echo   EDUAI PRO - STARTING SERVERS
echo ========================================
echo.

cd /d "%~dp0"

echo Starting BACKEND in new window...
start "EduAI Pro - Backend" cmd /k "cd /d %~dp0backend-node && npm run dev"

timeout /t 3 /nobreak >nul

echo Starting FRONTEND in new window...
start "EduAI Pro - Frontend" cmd /k "cd /d %~dp0frontend && npm run dev -- --host --port 3001"

echo.
echo ========================================
echo   SERVERS STARTING...
echo ========================================
echo.
echo Backend:  http://localhost:8081/api/health
echo Frontend: http://localhost:3001
echo.
echo Two windows opened. Wait 10-15 seconds, then:
echo   1. Check backend window for "SERVER RUNNING" message
echo   2. Open browser: http://localhost:3001
echo.
echo Press any key to exit this window (servers will keep running)...
pause >nul


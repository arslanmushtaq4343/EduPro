@echo off
echo ========================================
echo   RESTARTING ALL SERVERS
echo ========================================
echo.

cd /d "%~dp0"

echo Stopping any existing Node processes...
taskkill /F /IM node.exe >nul 2>&1
timeout /t 2 /nobreak >nul

echo.
echo Starting BACKEND in new window...
start "EduAI Pro - Backend (PORT 8081)" cmd /k "cd /d %~dp0backend-node && set PORT=8081 && npm run dev"

timeout /t 3 /nobreak >nul

echo Starting FRONTEND in new window...
start "EduAI Pro - Frontend (PORT 3001)" cmd /k "cd /d %~dp0frontend && npm run dev -- --host --port 3001"

echo.
echo ========================================
echo   SERVERS STARTING...
echo ========================================
echo.
echo Backend:  http://localhost:8081/api/health
echo Frontend: http://localhost:3001
echo.
echo Check the windows that opened:
echo   1. Backend should show: "Port: 8081"
echo   2. Backend should show: "SERVER RUNNING ON PORT 8081"
echo   3. Frontend should show: "Local: http://localhost:3001"
echo.
echo Wait 10-15 seconds, then try: http://localhost:3001
echo.
pause


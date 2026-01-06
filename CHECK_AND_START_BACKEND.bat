@echo off
echo ========================================
echo   CHECKING BACKEND STATUS
echo ========================================
echo.

REM Check if backend is running on port 8081
curl -s http://localhost:8081/api/health >nul 2>&1
if %errorlevel% equ 0 (
    echo Backend is already running on port 8081
    echo.
    echo Testing AI service...
    curl -s http://localhost:8081/api/health
    echo.
    echo ========================================
    echo   BACKEND IS HEALTHY
    echo ========================================
    pause
    exit /b 0
)

echo Backend is NOT running. Starting it now...
echo.
echo ========================================
echo   STARTING BACKEND ON PORT 8081
echo ========================================
echo.

cd /d "%~dp0backend-node"

REM Check if .env file exists
if not exist .env (
    echo WARNING: .env file not found!
    echo Creating .env from env.example...
    copy env.example .env
    echo.
    echo IMPORTANT: Please verify your GROQ_API_KEY in backend-node\.env
    echo.
    pause
)

REM Check if node_modules exists
if not exist node_modules (
    echo Installing dependencies...
    call npm install
    echo.
)

echo.
echo Starting backend server...
echo This window will stay open. DO NOT CLOSE IT.
echo Backend will run on: http://localhost:8081
echo.

set PORT=8081
npm run dev


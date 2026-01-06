# EduAI Pro - Start All Services (Node.js Stack)

Write-Host "EduAI Pro - Starting All Services (Node.js Stack)" -ForegroundColor Cyan
Write-Host "=================================================" -ForegroundColor Cyan
Write-Host ""

# Check prerequisites
Write-Host "Checking prerequisites..." -ForegroundColor Yellow

# Check Node.js
$nodePath = Get-Command node -ErrorAction SilentlyContinue
if (-not $nodePath) {
    Write-Host "  Node.js: MISSING" -ForegroundColor Red
    Write-Host "Please install Node.js 18+ from: https://nodejs.org/" -ForegroundColor Yellow
    exit 1
} else {
    $nodeVersion = node --version
    Write-Host "  Node.js: OK ($nodeVersion)" -ForegroundColor Green
}

# Check npm
$npmPath = Get-Command npm -ErrorAction SilentlyContinue
if (-not $npmPath) {
    Write-Host "  npm: MISSING" -ForegroundColor Red
    exit 1
} else {
    $npmVersion = npm --version
    Write-Host "  npm: OK ($npmVersion)" -ForegroundColor Green
}

Write-Host ""
Write-Host "All prerequisites met!" -ForegroundColor Green
Write-Host ""

# Install backend dependencies if needed
if (-not (Test-Path "backend-node\node_modules")) {
    Write-Host "Installing backend dependencies..." -ForegroundColor Yellow
    Set-Location backend-node
    npm install
    Set-Location ..
}

# Install frontend dependencies if needed
if (-not (Test-Path "frontend\node_modules")) {
    Write-Host "Installing frontend dependencies..." -ForegroundColor Yellow
    Set-Location frontend
    npm install
    Set-Location ..
}

Write-Host ""
Write-Host "IMPORTANT:" -ForegroundColor Yellow
Write-Host "1. Make sure MSSQL Server is running" -ForegroundColor White
Write-Host "2. Create database: CREATE DATABASE eduai_pro;" -ForegroundColor White
Write-Host "3. Update backend-node/.env with:" -ForegroundColor White
Write-Host "   - Database credentials (DB_SERVER, DB_USER, DB_PASSWORD)" -ForegroundColor White
Write-Host "   - Groq API key is already configured" -ForegroundColor White
Write-Host ""
Write-Host "Starting backend in a new window..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$PWD\backend-node'; npm run dev"

Start-Sleep -Seconds 5

Write-Host "Starting frontend..." -ForegroundColor Yellow
Set-Location frontend
npm run dev


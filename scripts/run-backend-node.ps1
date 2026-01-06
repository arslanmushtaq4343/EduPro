# EduAI Pro - Node.js Backend Startup Script

Write-Host "Starting EduAI Pro Backend (Node.js)..." -ForegroundColor Cyan
Write-Host ""

# Check if Node.js is available
$nodePath = Get-Command node -ErrorAction SilentlyContinue
if (-not $nodePath) {
    Write-Host "ERROR: Node.js is not installed or not in PATH" -ForegroundColor Red
    Write-Host "Please install Node.js 18+ from: https://nodejs.org/" -ForegroundColor Yellow
    exit 1
}

Set-Location backend-node

# Check if node_modules exists, if not install dependencies
if (-not (Test-Path "node_modules")) {
    Write-Host "Installing backend dependencies..." -ForegroundColor Yellow
    npm install
}

# Check if .env exists
if (-not (Test-Path ".env")) {
    Write-Host "WARNING: .env file not found. Creating from template..." -ForegroundColor Yellow
    Copy-Item ".env.example" ".env" -ErrorAction SilentlyContinue
    Write-Host "Please update .env with your database credentials and API keys" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Note: Make sure MSSQL Server is running and database is configured" -ForegroundColor Yellow
Write-Host "Note: Update .env file with your database credentials" -ForegroundColor Yellow
Write-Host ""

Write-Host "Starting Express server..." -ForegroundColor Green
Write-Host "Backend will be available at: http://localhost:8080/api" -ForegroundColor Green
Write-Host ""

npm run dev

Set-Location ..


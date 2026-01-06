# EduAI Pro - Setup Validation (PowerShell)

Write-Host "EduAI Pro - Setup Validation" -ForegroundColor Cyan
Write-Host "============================" -ForegroundColor Cyan
Write-Host ""

# Check Java
Write-Host -NoNewline "Checking Java... "
try {
    $javaVersion = java -version 2>&1 | Select-Object -First 1
    Write-Host "OK - $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "FAILED - Java not found" -ForegroundColor Red
    exit 1
}

# Check Maven
Write-Host -NoNewline "Checking Maven... "
try {
    $mvnVersion = mvn -version | Select-Object -First 1
    Write-Host "OK - $mvnVersion" -ForegroundColor Green
} catch {
    Write-Host "FAILED - Maven not found" -ForegroundColor Red
    exit 1
}

# Check Node.js
Write-Host -NoNewline "Checking Node.js... "
try {
    $nodeVersion = node --version
    Write-Host "OK - $nodeVersion" -ForegroundColor Green
} catch {
    Write-Host "FAILED - Node.js not found" -ForegroundColor Red
    exit 1
}

# Check npm
Write-Host -NoNewline "Checking npm... "
try {
    $npmVersion = npm --version
    Write-Host "OK - $npmVersion" -ForegroundColor Green
} catch {
    Write-Host "FAILED - npm not found" -ForegroundColor Red
    exit 1
}

# Check PostgreSQL
Write-Host -NoNewline "Checking PostgreSQL... "
try {
    $psqlVersion = psql --version 2>&1 | Select-Object -First 1
    Write-Host "OK - $psqlVersion" -ForegroundColor Green
} catch {
    Write-Host "WARNING - PostgreSQL not found (may be installed differently)" -ForegroundColor Yellow
}

# Check backend directory
Write-Host -NoNewline "Checking backend directory... "
if (Test-Path "backend") {
    Write-Host "OK" -ForegroundColor Green
} else {
    Write-Host "FAILED - backend directory not found" -ForegroundColor Red
    exit 1
}

# Check frontend directory
Write-Host -NoNewline "Checking frontend directory... "
if (Test-Path "frontend") {
    Write-Host "OK" -ForegroundColor Green
} else {
    Write-Host "FAILED - frontend directory not found" -ForegroundColor Red
    exit 1
}

# Check application.yml
Write-Host -NoNewline "Checking application.yml... "
if (Test-Path "backend/src/main/resources/application.yml") {
    Write-Host "OK" -ForegroundColor Green
} else {
    Write-Host "FAILED - application.yml not found" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Setup validation complete!" -ForegroundColor Green


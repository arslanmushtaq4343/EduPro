# EduAI Pro - Start All Services

Write-Host "EduAI Pro - Starting All Services" -ForegroundColor Cyan
Write-Host "=================================" -ForegroundColor Cyan
Write-Host ""

# Check prerequisites
$errors = @()

Write-Host "Checking prerequisites..." -ForegroundColor Yellow

# Check Node.js
$nodePath = Get-Command node -ErrorAction SilentlyContinue
if (-not $nodePath) {
    $errors += "Node.js is not installed or not in PATH"
} else {
    Write-Host "  Node.js: OK" -ForegroundColor Green
}

# Check Java
$javaPath = Get-Command java -ErrorAction SilentlyContinue
if (-not $javaPath) {
    $errors += "Java is not installed or not in PATH"
} else {
    Write-Host "  Java: OK" -ForegroundColor Green
}

# Check Maven
$mvnPath = Get-Command mvn -ErrorAction SilentlyContinue
if (-not $mvnPath) {
    $errors += "Maven is not installed or not in PATH"
} else {
    Write-Host "  Maven: OK" -ForegroundColor Green
}

if ($errors.Count -gt 0) {
    Write-Host ""
    Write-Host "Missing prerequisites:" -ForegroundColor Red
    foreach ($error in $errors) {
        Write-Host "  - $error" -ForegroundColor Red
    }
    Write-Host ""
    Write-Host "Please install missing tools and try again." -ForegroundColor Yellow
    Write-Host "Run: .\scripts\install-java-maven.ps1 for installation help" -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "All prerequisites met!" -ForegroundColor Green
Write-Host ""

# Install frontend dependencies if needed
if (-not (Test-Path "frontend\node_modules")) {
    Write-Host "Installing frontend dependencies..." -ForegroundColor Yellow
    Set-Location frontend
    npm install
    Set-Location ..
}

Write-Host ""
Write-Host "Starting services..." -ForegroundColor Cyan
Write-Host ""
Write-Host "IMPORTANT:" -ForegroundColor Yellow
Write-Host "1. Make sure PostgreSQL is running" -ForegroundColor White
Write-Host "2. Update backend/src/main/resources/application.yml with:" -ForegroundColor White
Write-Host "   - Database credentials" -ForegroundColor White
Write-Host "   - Groq API key (GROQ_API_KEY)" -ForegroundColor White
Write-Host ""
Write-Host "Starting backend in a new window..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$PWD\backend'; mvn spring-boot:run"

Start-Sleep -Seconds 5

Write-Host "Starting frontend..." -ForegroundColor Yellow
Set-Location frontend
npm run dev


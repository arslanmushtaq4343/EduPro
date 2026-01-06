# EduAI Pro - Backend Startup Script

Write-Host "Starting EduAI Pro Backend..." -ForegroundColor Cyan
Write-Host ""

# Check if Java is available
$javaPath = Get-Command java -ErrorAction SilentlyContinue
if (-not $javaPath) {
    Write-Host "ERROR: Java is not installed or not in PATH" -ForegroundColor Red
    Write-Host "Please install Java 17+ first" -ForegroundColor Yellow
    Write-Host "Run: .\scripts\install-java-maven.ps1" -ForegroundColor Yellow
    exit 1
}

# Check if Maven is available
$mvnPath = Get-Command mvn -ErrorAction SilentlyContinue
if (-not $mvnPath) {
    Write-Host "ERROR: Maven is not installed or not in PATH" -ForegroundColor Red
    Write-Host "Please install Maven 3.8+ first" -ForegroundColor Yellow
    Write-Host "Run: .\scripts\install-java-maven.ps1" -ForegroundColor Yellow
    exit 1
}

# Check if PostgreSQL is configured
Write-Host "Note: Make sure PostgreSQL is running and database 'eduai_pro' exists" -ForegroundColor Yellow
Write-Host "Note: Update application.yml with your database credentials and Groq API key" -ForegroundColor Yellow
Write-Host ""

# Change to backend directory
Set-Location backend

Write-Host "Building backend with Maven..." -ForegroundColor Yellow
mvn clean install -DskipTests

if ($LASTEXITCODE -ne 0) {
    Write-Host "Build failed! Please check errors above." -ForegroundColor Red
    Set-Location ..
    exit 1
}

Write-Host ""
Write-Host "Starting Spring Boot application..." -ForegroundColor Green
Write-Host "Backend will be available at: http://localhost:8080/api" -ForegroundColor Green
Write-Host ""

mvn spring-boot:run

Set-Location ..


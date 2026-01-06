# EduAI Pro - Java and Maven Installation Helper

Write-Host "EduAI Pro - Java and Maven Setup Helper" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if Java is installed
Write-Host "Checking for Java..." -ForegroundColor Yellow
$javaPath = Get-Command java -ErrorAction SilentlyContinue
if ($javaPath) {
    $javaVersion = java -version 2>&1 | Select-Object -First 1
    Write-Host "Java found: $javaVersion" -ForegroundColor Green
} else {
    Write-Host "Java NOT found in PATH" -ForegroundColor Red
    Write-Host ""
    Write-Host "To install Java:" -ForegroundColor Yellow
    Write-Host "1. Download Java 17+ from: https://adoptium.net/" -ForegroundColor White
    Write-Host "2. Or use Chocolatey: choco install openjdk17" -ForegroundColor White
    Write-Host "3. Or use Winget: winget install Microsoft.OpenJDK.17" -ForegroundColor White
    Write-Host ""
}

# Check if Maven is installed
Write-Host "Checking for Maven..." -ForegroundColor Yellow
$mvnPath = Get-Command mvn -ErrorAction SilentlyContinue
if ($mvnPath) {
    $mvnVersion = mvn -version | Select-Object -First 1
    Write-Host "Maven found: $mvnVersion" -ForegroundColor Green
} else {
    Write-Host "Maven NOT found in PATH" -ForegroundColor Red
    Write-Host ""
    Write-Host "To install Maven:" -ForegroundColor Yellow
    Write-Host "1. Download from: https://maven.apache.org/download.cgi" -ForegroundColor White
    Write-Host "2. Or use Chocolatey: choco install maven" -ForegroundColor White
    Write-Host "3. Or use Winget: winget install Apache.Maven" -ForegroundColor White
    Write-Host ""
    Write-Host "After installation, add Maven to PATH:" -ForegroundColor Yellow
    Write-Host "  [Environment]::SetEnvironmentVariable('Path', [Environment]::GetEnvironmentVariable('Path', 'User') + ';C:\Program Files\Apache\maven\bin', 'User')" -ForegroundColor White
    Write-Host ""
}

Write-Host "Setup Instructions:" -ForegroundColor Cyan
Write-Host "1. Install Java 17+ (required)" -ForegroundColor White
Write-Host "2. Install Maven 3.8+ (required)" -ForegroundColor White
Write-Host "3. Restart your terminal after installation" -ForegroundColor White
Write-Host "4. Run this script again to verify installation" -ForegroundColor White
Write-Host ""


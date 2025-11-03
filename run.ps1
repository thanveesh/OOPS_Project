# Carbon Emission Tracker - PowerShell Runner Script

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Carbon Emission Tracker" -ForegroundColor Green
Write-Host "  Starting Application..." -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if Maven is installed
$mvnExists = Get-Command mvn -ErrorAction SilentlyContinue
if (-not $mvnExists) {
    Write-Host "ERROR: Maven is not installed or not in PATH!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Please see QUICKSTART.md for installation instructions." -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Quick options:" -ForegroundColor White
    Write-Host "  1. Install Maven: https://maven.apache.org/download.cgi" -ForegroundColor White
    Write-Host "  2. Use IntelliJ IDEA: https://www.jetbrains.com/idea/download/" -ForegroundColor White
    Write-Host ""
    Read-Host "Press Enter to exit"
    exit 1
}

# Check if Java is installed
$javaExists = Get-Command java -ErrorAction SilentlyContinue
if (-not $javaExists) {
    Write-Host "ERROR: Java is not installed or not in PATH!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Please download and install Java 17 or higher from:" -ForegroundColor Yellow
    Write-Host "  https://adoptium.net/" -ForegroundColor White
    Write-Host ""
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host "Checking Java version..." -ForegroundColor Yellow
java -version

Write-Host ""
Write-Host "Starting Carbon Emission Tracker..." -ForegroundColor Green
Write-Host ""

# Run the application
try {
    mvn clean javafx:run
    if ($LASTEXITCODE -ne 0) {
        throw "Maven build failed"
    }
} catch {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Red
    Write-Host "  Error running application!" -ForegroundColor Red
    Write-Host "========================================" -ForegroundColor Red
    Write-Host ""
    Write-Host $_.Exception.Message -ForegroundColor Yellow
    Write-Host ""
    Read-Host "Press Enter to exit"
    exit 1
}

Read-Host "Press Enter to exit"

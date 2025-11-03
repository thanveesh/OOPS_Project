@echo off
REM Carbon Emission Tracker - Windows Batch Script Runner
REM This script helps run the application if Maven is installed

echo.
echo ========================================
echo   Carbon Emission Tracker
echo   Starting Application...
echo ========================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven is not installed or not in PATH!
    echo.
    echo Please see QUICKSTART.md for installation instructions.
    echo.
    echo Quick options:
    echo   1. Install Maven: https://maven.apache.org/download.cgi
    echo   2. Use IntelliJ IDEA: https://www.jetbrains.com/idea/download/
    echo.
    pause
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java is not installed or not in PATH!
    echo.
    echo Please download and install Java 17 or higher from:
    echo   https://adoptium.net/
    echo.
    pause
    exit /b 1
)

echo Checking Java version...
java -version

echo.
echo Starting Carbon Emission Tracker...
echo.

REM Run the application
mvn clean javafx:run

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ========================================
    echo   Error running application!
    echo ========================================
    echo.
    pause
    exit /b 1
)

pause

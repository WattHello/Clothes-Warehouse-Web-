@echo off
echo ========================================================
echo      Running Both Applications in DEV Profile Mode
echo ========================================================
echo.

echo Terminating any running Java processes...
taskkill /F /FI "IMAGENAME eq java.exe" >nul 2>&1
echo Java processes terminated.
echo.

echo Step 1: Starting Distribution Center Manager in DEV mode...
echo.
echo Starting the Distribution Center Manager with DEV profile on port 8081
cd distribution-center-manager
start cmd /k "mvn spring-boot:run -Dspring-boot.run.profiles=dev"

echo.
echo Step 2: Waiting for Distribution Center Manager to start up...
timeout /t 20

echo.
echo Step 3: Starting Clothes Warehouse in DEV mode...
echo.
echo Starting the Clothes Warehouse with DEV profile on port 8080
cd ../clothes-warehouse/clothes-warehouse
start cmd /k "mvn spring-boot:run -Dspring-boot.run.profiles=dev"

echo.
echo ========================================================
echo Both applications should now be starting in DEV mode:
echo - Distribution Center Manager: http://localhost:8081
echo - Clothes Warehouse: http://localhost:8080
echo ========================================================
echo. 
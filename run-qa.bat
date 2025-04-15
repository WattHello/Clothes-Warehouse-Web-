@echo off
echo ========================================================
echo      Running Both Applications in QA Profile Mode
echo ========================================================
echo.

echo Terminating any running Java processes...
taskkill /F /FI "IMAGENAME eq java.exe" >nul 2>&1
echo Java processes terminated.
echo.

echo Step 1: Starting Distribution Center Manager in QA mode...
echo.
echo Starting the Distribution Center Manager with QA profile on port 8082
cd distribution-center-manager
start cmd /k "mvn spring-boot:run -Dspring-boot.run.profiles=qa"

echo.
echo Step 2: Waiting for Distribution Center Manager to start up...
timeout /t 20

echo.
echo Step 3: Starting Clothes Warehouse in QA mode...
echo.
echo Starting the Clothes Warehouse with QA profile on port 8083
cd ../clothes-warehouse/clothes-warehouse
start cmd /k "mvn spring-boot:run -Dspring-boot.run.profiles=qa"

echo.
echo ========================================================
echo Both applications should now be starting in QA mode:
echo - Distribution Center Manager: http://localhost:8082
echo - Clothes Warehouse: http://localhost:8083
echo ========================================================
echo. 
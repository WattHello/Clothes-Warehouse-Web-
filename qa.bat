@echo off
echo Starting Distribution Center Manager in QA mode...
cd distribution-center-manager
start cmd /k "mvn spring-boot:run -Dspring-boot.run.profiles=qa"
timeout /t 15
echo Starting Clothes Warehouse in QA mode...
cd ../clothes-warehouse/clothes-warehouse
start cmd /k "mvn spring-boot:run -Dspring-boot.run.profiles=qa"
echo.
echo Both applications started in QA mode:
echo - Distribution Center Manager: http://localhost:8082
echo - Clothes Warehouse: http://localhost:8083 
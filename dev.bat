@echo off
echo Starting Distribution Center Manager in DEV mode...
cd distribution-center-manager
start cmd /k "mvn spring-boot:run -Dspring-boot.run.profiles=dev"
timeout /t 15
echo Starting Clothes Warehouse in DEV mode...
cd ../clothes-warehouse/clothes-warehouse
start cmd /k "mvn spring-boot:run -Dspring-boot.run.profiles=dev"
echo.
echo Both applications started in DEV mode:
echo - Distribution Center Manager: http://localhost:8081
echo - Clothes Warehouse: http://localhost:8080 
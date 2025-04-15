@echo off
echo ========================================================
echo      STARTING BOTH APPLICATIONS IN QA MODE
echo      Using Direct JVM System Properties
echo ========================================================
echo.

echo Terminating any running Java processes...
taskkill /F /FI "IMAGENAME eq java.exe" >nul 2>&1
echo Java processes terminated.
echo.

echo Step 1: Starting Distribution Center Manager in QA mode...
echo.
cd distribution-center-manager
start cmd /k "mvn clean spring-boot:run -Drun.jvmArguments='-Dspring.profiles.active=qa'"

echo.
echo Step 2: Waiting for Distribution Center Manager to start up...
timeout /t 20

echo.
echo Step 3: Starting Clothes Warehouse in QA mode...
echo.
cd ../clothes-warehouse/clothes-warehouse
start cmd /k "mvn clean spring-boot:run -Drun.jvmArguments='-Dspring.profiles.active=qa'"

echo.
echo ========================================================
echo Both applications should now be starting in QA mode:
echo - Distribution Center Manager: http://localhost:8082
echo - Clothes Warehouse: http://localhost:8083
echo ========================================================
echo.
echo NOTE: If either application fails to start, check the command
echo window for error messages. You may need to manually run:
echo.
echo cd distribution-center-manager
echo java -jar -Dspring.profiles.active=qa target/distribution-center-manager-0.0.1-SNAPSHOT.jar
echo.
echo and
echo.
echo cd clothes-warehouse/clothes-warehouse
echo java -jar -Dspring.profiles.active=qa target/clothes-warehouse-0.0.1-SNAPSHOT.jar
echo ======================================================== 
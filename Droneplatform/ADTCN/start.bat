@echo off
REM Script pour démarrer l'infrastructure complète CityMesh

echo ========================================
echo  CityMesh - Démarrage Infrastructure
echo ========================================
echo.

cd /d %~dp0

echo [1/4] Build de l'application Spring Boot...
call mvn clean package -DskipTests -q
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: Build echoue!
    pause
    exit /b 1
)
echo ✓ Build OK

echo.
echo [2/4] Demarrage des containers Docker...
docker compose down 2>nul
docker compose up -d
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: Docker Compose echoue!
    pause
    exit /b 1
)
echo ✓ Containers demarres

echo.
echo [3/4] Attente du demarrage de MySQL (20s)...
timeout /t 20 /nobreak >nul

echo.
echo [4/4] Import de la base de donnees...
docker exec -i mysql-db mysql -ucitymeshuser -pcitymeshpwd citymesh < src\main\resources\db.sql
if %ERRORLEVEL% NEQ 0 (
    echo ATTENTION: Import DB peut avoir echoue - verifiez manuellement
) else (
    echo ✓ Base de donnees importee
)

echo.
echo ========================================
echo  Infrastructure prete !
echo ========================================
echo.
echo  - Backend API:      http://localhost:8081/api/drones
echo  - phpMyAdmin:       http://localhost:8082
echo  - MySQL:            localhost:3306
echo.
echo Pour voir les logs: docker compose logs -f app
echo Pour arreter:       docker compose down
echo.
pause


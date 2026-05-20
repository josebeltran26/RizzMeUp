@echo off
echo ====================================
echo  Inicializando BD rizzmeup en Mongo
echo ====================================

:: Buscar mongosh en rutas comunes de instalacion
set MONGOSH=mongosh
where mongosh >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    :: MongoDB 6+ en ruta de instalacion por defecto
    if exist "C:\Program Files\MongoDB\Server\7.0\bin\mongosh.exe" (
        set MONGOSH="C:\Program Files\MongoDB\Server\7.0\bin\mongosh.exe"
    ) else if exist "C:\Program Files\MongoDB\Server\6.0\bin\mongosh.exe" (
        set MONGOSH="C:\Program Files\MongoDB\Server\6.0\bin\mongosh.exe"
    ) else if exist "C:\Program Files\MongoDB\Server\8.0\bin\mongosh.exe" (
        set MONGOSH="C:\Program Files\MongoDB\Server\8.0\bin\mongosh.exe"
    ) else (
        echo ERROR: No se encontro mongosh.
        echo Instala MongoDB Community desde https://www.mongodb.com/try/download/community
        pause
        exit /b 1
    )
)

echo Ejecutando script de inicializacion...
%MONGOSH% --file "%~dp0init_rizzmeup.js"

if %ERRORLEVEL% EQU 0 (
    echo.
    echo [OK] Base de datos rizzmeup creada correctamente.
) else (
    echo.
    echo [ERROR] Verifica que el servicio de MongoDB este corriendo.
    echo Abre "Servicios" de Windows y busca "MongoDB"
)

pause

@echo off

call mvn clean package

:: Set JAR path
set "jarFile=target\taskapp.jar"

setlocal enabledelayedexpansion

:: Path to the .env file
set "envFile=%~dp0.env"

:: Read and set environment variables from .env
for /F "tokens=*" %%a in (%envFile%) do (
    for /F "tokens=1* delims==" %%b in ("%%a") do (
        set %%b=%%c
    )
)

:: Execute the JAR file
java -jar %jarFile%

endlocal

pause
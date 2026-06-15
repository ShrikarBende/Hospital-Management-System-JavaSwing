@echo off
cd /d "%~dp0.."
echo Saving Hospital Management System project...

:: Set Java paths
set JAVA_HOME=C:\Program Files\Java\jdk-22
set PATH=%JAVA_HOME%\bin;%PATH%

:: Create backup directory with timestamp
for /f "tokens=2 delims==" %%I in ('wmic os get localdatetime /value') do set datetime=%%I
set BACKUP_DIR=backup_%datetime:~0,8%_%datetime:~8,6%
if not exist "%BACKUP_DIR%" mkdir "%BACKUP_DIR%"

:: Copy source files to backup
echo Creating backup of source files...
xcopy /E /I /Y "src" "%BACKUP_DIR%\src"
xcopy /E /I /Y "scripts" "%BACKUP_DIR%\scripts"
xcopy /Y "*.jar" "%BACKUP_DIR%\"

:: Create bin directory if it doesn't exist
if not exist "bin" mkdir bin

:: Clean bin directory
del /Q /S bin\*.*

:: Clean any .class files in source directories
del /Q /S src\main\java\com\hospital\*.class
del /Q /S src\main\java\com\hospital\model\*.class
del /Q /S src\main\java\com\hospital\dao\*.class
del /Q /S src\main\java\com\hospital\util\*.class
del /Q /S src\main\java\com\hospital\test\*.class

:: Compile all Java files except IconConverter.java
echo Compiling source files...
"%JAVA_HOME%\bin\javac" -d bin -cp ".;mysql-connector-j-9.3.0.jar" ^
    src/main/java/com/hospital/Main.java ^
    src/main/java/com/hospital/HospitalManagementGUI.java ^
    src/main/java/com/hospital/HospitalManagementSystem.java ^
    src/main/java/com/hospital/DatabaseTest.java ^
    src/main/java/com/hospital/model/*.java ^
    src/main/java/com/hospital/dao/*.java ^
    src/main/java/com/hospital/util/*.java ^
    src/main/java/com/hospital/test/*.java

if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)

:: Copy compiled files to backup
echo Copying compiled files to backup...
xcopy /E /I /Y "bin" "%BACKUP_DIR%\bin"

echo Project saved successfully!
echo Backup created in directory: %BACKUP_DIR%
echo.
echo To run the application, use: scripts\compile_and_run_without_icon.bat
echo.
pause

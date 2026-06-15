@echo off
cd /d "%~dp0.."
echo Running Hospital Management System Database Test...
echo.

REM Set MySQL path (update this path to match your MySQL installation)
set MYSQL_PATH=C:\Program Files\MySQL\MySQL Server 8.0\bin

REM Set the path to the MySQL Connector/J JAR file
set MYSQL_CONNECTOR=%CD%\mysql-connector-j-9.3.0.jar

REM Check if MySQL is installed
"%MYSQL_PATH%\mysql" --version > nul 2>&1
if %errorlevel% neq 0 (
    echo MySQL is not found at %MYSQL_PATH%
    echo Please update the MYSQL_PATH variable in this batch file.
    exit /b 1
)

REM Check if the MySQL Connector/J JAR file exists
if not exist "%MYSQL_CONNECTOR%" (
    echo MySQL Connector/J JAR file not found: %MYSQL_CONNECTOR%
    echo Please copy the mysql-connector-j-9.3.0.jar file to the current directory.
    exit /b 1
)

REM Create necessary directories if they don't exist
if not exist "src\main\java\com\hospital\model" mkdir "src\main\java\com\hospital\model"
if not exist "src\main\java\com\hospital\dao" mkdir "src\main\java\com\hospital\dao"
if not exist "src\main\java\com\hospital\util" mkdir "src\main\java\com\hospital\util"
if not exist "target\classes" mkdir "target\classes"

REM Compile and run the test
echo Compiling and running database test...
javac -d target/classes -cp ".;%MYSQL_CONNECTOR%;src/main/java" src/main/java/com/hospital/DatabaseTest.java
if %errorlevel% neq 0 (
    echo Compilation failed.
    exit /b 1
)

REM Copy resources to target directory
xcopy /s /y src\main\resources\* target\classes\

REM Run the test
java -cp "target/classes;%MYSQL_CONNECTOR%" com.hospital.DatabaseTest
if %errorlevel% neq 0 (
    echo Test failed.
    exit /b 1
)

echo.
echo Database test completed successfully!

@echo off
cd /d "%~dp0.."
echo Compiling and running Hospital Management System...

:: Set Java paths
set JAVA_HOME=C:\Program Files\Java\jdk-22
set PATH=%JAVA_HOME%\bin;%PATH%

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
    src/main/java/com/hospital/gui/HospitalManagementGUI.java ^
    src/main/java/com/hospital/service/HospitalManagementSystem.java ^
    src/main/java/com/hospital/model/*.java ^
    src/main/java/com/hospital/dao/*.java ^
    src/main/java/com/hospital/util/DatabaseInitializer.java ^
    src/main/java/com/hospital/util/DatabaseUtil.java ^
    src/main/java/com/hospital/util/ImageSaver.java ^
    src/test/java/com/hospital/*.java

if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo Running Hospital Management System...

:: Run the application
"%JAVA_HOME%\bin\java" -cp "bin;mysql-connector-j-9.3.0.jar" com.hospital.Main

pause

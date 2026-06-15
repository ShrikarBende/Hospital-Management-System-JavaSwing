@echo off
cd /d "%~dp0.."
echo Compiling all classes...
javac -cp ".;%CD%\mysql-connector-j-9.3.0.jar" src/main/java/com/hospital/model/*.java
javac -cp ".;%CD%\mysql-connector-j-9.3.0.jar" src/main/java/com/hospital/dao/*.java
javac -cp ".;%CD%\mysql-connector-j-9.3.0.jar" src/main/java/com/hospital/util/*.java
javac -cp ".;%CD%\mysql-connector-j-9.3.0.jar" src/main/java/com/hospital/service/*.java
javac -cp ".;%CD%\mysql-connector-j-9.3.0.jar" src/main/java/com/hospital/*.java
javac -cp ".;%CD%\mysql-connector-j-9.3.0.jar" src/test/java/com/hospital/*.java

echo Running PatientTest...
java -cp ".;%CD%\mysql-connector-j-9.3.0.jar;src/main/java;src/test/java" com.hospital.PatientTest

pause

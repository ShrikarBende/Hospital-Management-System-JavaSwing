@echo off
cd /d "%~dp0.."
echo Compiling all classes...
javac -cp ".;%CD%\mysql-connector-j-9.3.0.jar" -d target/classes src/main/java/com/hospital/model/*.java src/main/java/com/hospital/dao/*.java src/main/java/com/hospital/util/*.java src/main/java/com/hospital/service/*.java src/main/java/com/hospital/*.java src/test/java/com/hospital/*.java

echo Running RoomTest...
java -cp "target/classes;%CD%\mysql-connector-j-9.3.0.jar" com.hospital.RoomTest

pause

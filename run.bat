@echo off
cd /d "%~dp0"
powershell -Command "$files = Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName }; javac -Xprefer:source -cp "." -d "." $files"
java -cp "." Launcher
